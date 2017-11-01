package com.letv.handleassistant.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextPaint;
import android.util.Log;

import com.letv.handleassistant.application.SoftApplication;
import com.letv.handleassistant.constant.Constant;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



/**
 * 图片工具类，对图片进行一些处理
 * 
 */
public class ImageUtil {

	/*
	 * 对图片进行压缩
	 *
	 * @param srcPath
	 */
	public static InputStream getIsByPath(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		LogUtil.log("w ::" + w + "h::" + h);
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
//		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
//			be = (int) (newOpts.outWidth / ww);
//		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
//			be = (int) (newOpts.outHeight / hh);
//		}
//		if (be <= 0)
//			be = 1;
		LogUtil.log("be ::" + be);
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	public static InputStream compressImage(Bitmap image) {

		if (image == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于300kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		LogUtil.log("图片大小为---------------->" + baos.toByteArray().length / 1024);
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		// Bitmap bitmap = BitmapFactory.decodeStream(isBm, null,
		// null);//把ByteArrayInputStream数据生成图片
		return isBm;
	}


	public static Bitmap createTextBitmap(Bitmap src, String title) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		// 需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
		Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		// Paint paint = new Paint();
		// 加入图片
		// if (watermark != null) {
		// int ww = watermark.getWidth();
		// int wh = watermark.getHeight();
		// paint.setAlpha(50);
		// cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);//
		// 在src的右下角画入水印
		// }
		// 加入文字
		if (title != null) {
			String familyName = "宋体";
			Typeface font = Typeface.create(familyName, Typeface.BOLD);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(60);
			// 这里是自动换行的
			// StaticLayout layout = new StaticLayout(title, textPaint, w,
			// Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
			// layout.draw(cv);
			// 文字就加左上角算了
			cv.drawText(title, 40, h-40, textPaint);
		}
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储
		return newb;
	}


	public static File saveBitmap2file(Bitmap bmp) {
		CompressFormat format = CompressFormat.JPEG;
		int quality = 80;
		OutputStream stream = null;
		String path = Constant.FILE_PATH_BASE+ "/"
				+ System.currentTimeMillis()+".jpg";
		try {
			stream = new FileOutputStream(path);
			if(stream!=null)
				bmp.compress(format, quality, stream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new File(path);
	}

	public static Bitmap getSmallBitmap(String filePath) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		Bitmap bm = BitmapFactory.decodeFile(filePath, options);
		if (bm == null) {
			return null;
		}
		int degree = readPictureDegree(filePath);
		bm = rotateBitmap(bm, degree);
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			bm.compress(CompressFormat.JPEG, 20, baos);

		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bm;

	}


	private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
		if (bitmap == null)
			return null;

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		// Setting post rotate to 90
		Matrix mtx = new Matrix();
		mtx.postRotate(rotate);
		return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
	}


	/**
	 * 图片旋转
	 * 
	 * @param bmp
	 * @param degree
	 * @return
	 */
	public static Bitmap postRotateBitamp(Bitmap bmp, float degree) {
		// 获得Bitmap的高和宽
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		// 产生resize后的Bitmap对象
		Matrix matrix = new Matrix();
		matrix.setRotate(degree);
		Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
		return resizeBmp;
	}

	/**
	 * 图片放大缩小
	 * 
	 * @param bmp
	 * @return
	 */
	public static Bitmap postScaleBitamp(Bitmap bmp, float sx, float sy) {
		// 获得Bitmap的高和宽
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		// System.out.println("--------------before+w+h:::::::::::"+bmpWidth+","+bmpHeight);
		// 产生resize后的Bitmap对象
		Matrix matrix = new Matrix();
		matrix.setScale(sx, sy);
		Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
		// System.out.println("--------------after+w+h:::::::::::"+resizeBmp.getWidth()+","+resizeBmp.getHeight());
		return resizeBmp;
	}

	/**
	 * 图片 亮度调整
	 * 
	 *            huevalue亮度调整黑白
	 * @return
	 */
	public static Bitmap postColorRotateBitamp(int hueValue, int lumValue, Bitmap bm) {
		// 获得Bitmap的高和宽
		// System.out.println(bm.getWidth()+","+bm.getHeight()+"------before");
		Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
		// 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
		Canvas canvas = new Canvas(bmp); // 得到画笔对象
		Paint paint = new Paint(); // 新建paint
		paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理

		// 产生resize后的Bitmap对象
		ColorMatrix mAllMatrix = new ColorMatrix();
		ColorMatrix mLightnessMatrix = new ColorMatrix();
		ColorMatrix mSaturationMatrix = new ColorMatrix();
		ColorMatrix mHueMatrix = new ColorMatrix();

		float mHueValue = hueValue * 1.0F / 127; // 亮度
		mHueMatrix.reset();
		mHueMatrix.setScale(mHueValue, mHueValue, mHueValue, 1); // 红、绿、蓝三分量按相同的比例,最后一个参数1表示透明度不做变化，此函数详细说明参考

		float mSaturationValue = 127 * 1.0F / 127;// 饱和度
		mSaturationMatrix.reset();
		mSaturationMatrix.setSaturation(mSaturationValue);

		float mLumValue = (lumValue - 127) * 1.0F / 127 * 180; // 色相
		mLightnessMatrix.reset(); // 设为默认值
		mLightnessMatrix.setRotate(0, mLumValue); // 控制让红色区在色轮上旋转的角度
		mLightnessMatrix.setRotate(1, mLumValue); // 控制让绿红色区在色轮上旋转的角度
		mLightnessMatrix.setRotate(2, mLumValue); // 控制让蓝色区在色轮上旋转的角度

		mAllMatrix.reset();
		mAllMatrix.postConcat(mHueMatrix);
		mAllMatrix.postConcat(mSaturationMatrix); // 效果叠加
		mAllMatrix.postConcat(mLightnessMatrix); // 效果叠加

		paint.setColorFilter(new ColorMatrixColorFilter(mAllMatrix));// 设置颜色变换效果
		canvas.drawBitmap(bm, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
		// System.out.println(bmp.getWidth()+","+bmp.getHeight()+"------after");
		return bmp;
	}

	/**
	 * 读取资源图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 对图片进行处理 1，首先判断 图片的宽和高 2，如果宽和高都小于700，就放大到手机的宽度（要判断是否大于700）
	 * 3，如果有一项大于700，就进行缩放，都小于700为止
	 */
	public static Bitmap parseBitmap(Bitmap mBitmap, String path) {
		// 1
		int imgWidth = mBitmap.getWidth();
		int imgHeight = mBitmap.getHeight();
		// 2
		if (imgWidth > 700 || imgHeight > 700) {
			float sx = imgWidth > imgHeight ? ((float) 700) / (float) imgWidth : ((float) 700) / (float) imgHeight;
			mBitmap = postScaleBitamp(mBitmap, sx, sx);
		} else {
			/*
			 * if(screenWidth<700){ float sx = imgWidth > imgHeight ?
			 * ((float)screenWidth)/(float)imgWidth
			 * :((float)screenWidth)/(float)imgHeight; mBitmap =
			 * postScaleBitamp(mBitmap, sx, sx); }else{ float sx = imgWidth >
			 * imgHeight ? ((float)700)/(float)imgWidth
			 * :((float)700)/(float)imgHeight; mBitmap =
			 * postScaleBitamp(mBitmap, sx, sx); }
			 */
			int value = imgWidth > imgHeight ? imgWidth : imgHeight;
			if (value < 100) {
				mBitmap = getBitmapByPath(path);
			} else {
				return mBitmap;
			}
		}
		return mBitmap;
	}

	/**
	 * 图片缩放到手机屏幕
	 * 
	 * @param mBitmap
	 * @return
	 */
	public static Bitmap parseBitmapToScreenWH(Bitmap mBitmap) {
		// 1
		int imgWidth = mBitmap.getWidth();
		int imgHeight = mBitmap.getHeight();
		int screenW = DensityUtil.getWidth(SoftApplication.softApplication);
		int screenH = DensityUtil.getHeight(SoftApplication.softApplication);
		// 2
		if (imgWidth > screenW || imgHeight > screenH) {
			float sx = imgWidth > imgHeight ? ((float) screenW) / (float) imgWidth
					: ((float) screenH) / (float) imgHeight;
			mBitmap = postScaleBitamp(mBitmap, sx, sx);
		} else {
			/*
			 * if(screenWidth<700){ float sx = imgWidth > imgHeight ?
			 * ((float)screenWidth)/(float)imgWidth
			 * :((float)screenWidth)/(float)imgHeight; mBitmap =
			 * postScaleBitamp(mBitmap, sx, sx); }else{ float sx = imgWidth >
			 * imgHeight ? ((float)700)/(float)imgWidth
			 * :((float)700)/(float)imgHeight; mBitmap =
			 * postScaleBitamp(mBitmap, sx, sx); }
			 */
			// int value = imgWidth > imgHeight ? imgWidth : imgHeight;
			// if (value < sun) {
			// mBitmap = getBitmapByPath(path);
			// } else {
			// return mBitmap;
			// }
		}
		return mBitmap;
	}

	public static Bitmap parseBitmap(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(path, options);

		int mWidth = 640;

		int max = options.outWidth > options.outHeight ? options.outWidth : options.outHeight;
		if (max > mWidth) {
			options.inSampleSize = max / mWidth;
			int height = options.outHeight * mWidth / max;
			int width = options.outWidth * mWidth / max;
			options.outWidth = width;
			options.outHeight = height;

		} else {
			options.inSampleSize = 1;
			options.outWidth = options.outWidth;
			options.outHeight = options.outHeight;
		}
		/* 这样才能真正的返回一个Bitmap给你 */
		options.inJustDecodeBounds = false;

		return getBitmapByPath(path, options);
	}

	public static Bitmap parseBitmap(Bitmap mBitmap, String path, int width) {
		if (mBitmap == null) {
			return null;
		}
		int imgWidth = mBitmap.getWidth();
		int imgHeight = mBitmap.getHeight();
		if (imgWidth > width || imgHeight > width) {
			float sx = imgWidth > imgHeight ? ((float) width) / (float) imgWidth : ((float) width) / (float) imgHeight;
			mBitmap = postScaleBitamp(mBitmap, sx, sx);
		} else {
			int value = imgWidth > imgHeight ? imgWidth : imgHeight;
			if (value < 100) {
				mBitmap = getBitmapByPath(path);
			} else {
				return mBitmap;
			}
		}
		return mBitmap;
	}

	public static Bitmap parseBitmapToLittle(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(path, options);

		int mWidth = 320;

		int max = options.outWidth > options.outHeight ? options.outWidth : options.outHeight;
		if (max > mWidth) {
			options.inSampleSize = max / mWidth;
			// int height = options.outHeight * mWidth / max;
			// int width = options.outWidth * mWidth / max;
			options.outWidth = 320;
			options.outHeight = 320;

		} else {
			options.inSampleSize = max / mWidth;
			options.outWidth = 320;
			options.outHeight = 320;
		}
		/* 这样才能真正的返回一个Bitmap给你 */
		options.inJustDecodeBounds = false;
		return getBitmapByPath(path, options);
	}

	public static Bitmap parseHeadBitmapToLittle(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(path, options);

		int mWidth = 120;

		int max = options.outWidth > options.outHeight ? options.outWidth : options.outHeight;
		if (max > mWidth) {
			options.inSampleSize = max / mWidth;
			options.outWidth = 120;
			options.outHeight = 120;

		} else {
			options.inSampleSize = max / mWidth;
			options.outWidth = 120;
			options.outHeight = 120;
		}
		/* 这样才能真正的返回一个Bitmap给你 */
		options.inJustDecodeBounds = false;
		return getBitmapByPath(path, options);
	}

	/**
	 * 获取bitmap
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getBitmapByPath(String filePath) {
		return getBitmapByPath(filePath, null);
	}

	/**
	 * @description 从SD卡上加载图片
	 * 
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromFile(byte data[], int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		Bitmap src = BitmapFactory.decodeByteArray(data, 0, data.length, options);
		// if (options.inSampleSize <= 1) {
		return src;
		// } else {
		// return createScaleBitmap(src, reqWidth, reqHeight,
		// options.inSampleSize);
		// }
	}

	/**
	 * @description 从SD卡上加载图片
	 * 
	 * @param pathName
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		Bitmap src = BitmapFactory.decodeFile(pathName, options);
		if (options.inSampleSize <= 1) {
			return src;
		} else {
			return createScaleBitmap(src, reqWidth, reqHeight, options.inSampleSize);
		}
	}

	/**
	 * @description 计算图片的压缩比率
	 * 
	 * @param options
	 *            参数
	 * @param reqWidth
	 *            目标的宽度
	 * @param reqHeight
	 *            目标的高度
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}

	/**
	 * @description 通过传入的bitmap，进行压缩，得到符合标准的bitmap
	 * 
	 * @param src
	 * @param dstWidth
	 * @param dstHeight
	 * @return
	 */
	private static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight, int inSampleSize) {
		// 如果inSampleSize是2的倍数，也就说这个src已经是我们想要的缩略图了，直接返回即可。
		if (inSampleSize % 2 == 0) {
			return src;
		}
		// 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响，我们这里是缩小图片，所以直接设置为false
		Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
		if (src != dst) { // 如果没有缩放，那么不回收
			src.recycle(); // 释放Bitmap的native像素数组
		}
		return dst;
	}

	public static Bitmap getBitmapByPath(String filePath, BitmapFactory.Options opts) {
		FileInputStream fis = null;
		Bitmap bitmap = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(fis, null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return bitmap;
	}

	/**
	 * 根据Uri获取文件的路径
	 * 
	 * @Title: getUriString
	 * @param uri
	 * @return
	 * @return String
	 * @date 2012-11-28 下午1:19:31
	 */
	public static String getUriString(Uri uri, ContentResolver cr) {
		String imgPath = null;
		if (uri != null) {
			String uriString = uri.toString();
			// 小米手机的适配问题，小米手机的uri以file开头，其他的手机都以content开头
			// 以content开头的uri表明图片插入数据库中了，而以file开头表示没有插入数据库
			// 所以就不能通过query来查询，否则获取的cursor会为null。
			if (uriString.startsWith("file")) {
				// uri的格式为file:///mnt....,将前七个过滤掉获取路径
				imgPath = uriString.substring(7, uriString.length());
				return imgPath;
			}
			Cursor cursor = cr.query(uri, null, null, null, null);
			cursor.moveToFirst();
			imgPath = cursor.getString(1); // 图片文件路径

		}
		return imgPath;
	}

	/**
	 * 写图片文件到SD卡
	 * 
	 * @throws IOException
	 */
	public static void saveImageToSD(String filePath, Bitmap bitmap) {
		try {
			if (bitmap != null) {
				FileOutputStream fos = new FileOutputStream(filePath);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.PNG, 100, stream);
				byte[] bytes = stream.toByteArray();
				fos.write(bytes);
				fos.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Bitmap DrawableToBitmap(Drawable d) {
		Bitmap bm = null;
		if (d != null) {

			BitmapDrawable bd = (BitmapDrawable) d;
			bm = bd.getBitmap();
		}
		return bm;
	}

	/**
	 * 压缩图片并保存到sd卡
	 * 
	 * @param path
	 *            传过来的图片路径
	 * @return 压缩后图片的路径
	 */
	public static String parseBitmapToSD(String path, int mWidth) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(path, options);
		int max = options.outWidth > options.outHeight ? options.outWidth : options.outHeight;
		if (max > mWidth) {
			float f = ((float) max / (float) mWidth);
			// LogUtil.log("----------- ((float) max / (float)
			// mWidth)-------->"+f);
			options.inSampleSize = Math.round(f);
			// LogUtil.log("------------options.inSampleSize)------->"+options.inSampleSize);
			// int height = options.outHeight * mWidth / max;
			// int width = options.outWidth * mWidth / max;
			// options.outWidth = width;
			// options.outHeight = height;
		} else {
			options.inSampleSize = 1;
		}
		/* 这样才能真正的返回一个Bitmap给你 */
		options.inJustDecodeBounds = false;
		Bitmap parseBitmap = getBitmapByPath(path, options);
		File file = new File(path);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (parseBitmap.compress(CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return path;
		} catch (IOException e) {
			e.printStackTrace();
			return path;
		}
		return path;
	}

	/**
	 * 对图片进行压缩旋转处理
	 *
	 * @return
	 */
	public static File getScaledImage(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return file;
		} else {
			Bitmap bitmap = decodeScaleImage(path, 640, 960);
			try {
				File newFile = new File(path);
				if (newFile.exists()) {
					newFile.delete();
				}
				FileOutputStream os = new FileOutputStream(newFile);
				bitmap.compress(CompressFormat.JPEG, 100, os);
				os.close();
				return newFile;
			} catch (Exception var8) {
				var8.printStackTrace();
				return file;
			}
		}
	}

	public static Bitmap decodeScaleImage(String var0, int var1, int var2) {
		BitmapFactory.Options var3 = getBitmapOptions(var0);
		int var4 = calculateInSampleSize(var3, var1, var2);
		var3.inSampleSize = var4;
		var3.inJustDecodeBounds = false;
		Bitmap var5 = BitmapFactory.decodeFile(var0, var3);
		int var6 = readPictureDegree(var0);
		Bitmap var7 = null;
		if (var5 != null && var6 != 0) {
			var7 = rotaingImageView(var6, var5);
			var5.recycle();
			var5 = null;
			return var7;
		} else {
			return var5;
		}
	}

	public static BitmapFactory.Options getBitmapOptions(String var0) {
		BitmapFactory.Options var1 = new BitmapFactory.Options();
		var1.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(var0, var1);
		return var1;
	}

	/**
	 * 读取图片的旋转的角度
	 *
	 * @param path
	 *            图片绝对路径
	 * @return 图片的旋转角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			// 从指定路径下读取图片，并获取其EXIF信息
			ExifInterface exifInterface = new ExifInterface(path);
			// 获取图片的旋转信息
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 将图片按照某个角度进行旋转
	 *
	 * @param bm
	 *            需要旋转的图片
	 * @param degree
	 *            旋转角度
	 * @return 旋转后的图片
	 */
	public static Bitmap rotaingImageView(int degree, Bitmap bm) {
		Bitmap returnBm = null;

		// 根据旋转角度，生成旋转矩阵
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		try {
			// 将原始图片按照旋转矩阵进行旋转，并得到新的图片
			returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
		} catch (OutOfMemoryError e) {
		}
		if (returnBm == null) {
			returnBm = bm;
		}
		if (bm != returnBm) {
			bm.recycle();
		}
		return returnBm;
	}

	public static Bitmap getBitmapFromUri(Context context, Uri uri) {
		try {
			// 读取uri所在的图片
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
			return bitmap;
		} catch (Exception e) {
			Log.e("[Android]", e.getMessage());
			Log.e("[Android]", "目录为：" + uri);
			e.printStackTrace();
			return null;
		}
	}


	/**
	 *  根据路径来获取图片的二进制流,没有通过bitmap来压缩图片
	 * @param path
	 * @return
	 */
	public static String getImgEncodeStrByPath(String path){
		String imgEncodeStr = "";
		byte[] bitmapBytes = readImgPath(path);
//		imgEncodeStr = StringUtil.encodeStr(bitmapBytes);
		imgEncodeStr = ImgErToFileUtil.byte2hex(bitmapBytes);
		ImgErToFileUtil.bytes2Str(bitmapBytes);
		return imgEncodeStr;
	}

	public static byte[] readImgPath(String path) {
		FileInputStream fileInputStream;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			fileInputStream = new FileInputStream(new File(path));
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.close();
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}
}
