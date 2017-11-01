package com.letv.handleassistant.framework.uploadimage;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.WindowManager;

import com.letv.handleassistant.framework.bean.BaseResponse;
import com.letv.handleassistant.framework.bean.DataHull;
import com.letv.handleassistant.framework.network.Request;
import com.letv.handleassistant.framework.parser.BaseParser;

public class UpLoadImageHelper {
	private OnUploadImageCompleteListener onUploadImageCompleteListener;
	private OnUploadImageArrayCompleteListener onUploadImageArrayCompleteListener;
	private static UpLoadImageHelper sharedPrefHelper = null;
	private ProgressDialog progressDialog;
	private static Context context;
	public static synchronized UpLoadImageHelper getInstance(Context context2) {
		context = context2;
		if (null == sharedPrefHelper) {
			sharedPrefHelper = new UpLoadImageHelper();
		}
		return sharedPrefHelper;
	}
	public UpLoadImageHelper() {
		super();
	}
	
	public <T extends BaseResponse>void upLoadingImageArray(BaseParser<T> parser , Request request, FormFile formFile, OnUploadImageArrayCompleteListener onUploadImageArrayCompleteListener){
		this.onUploadImageArrayCompleteListener = onUploadImageArrayCompleteListener;
		new UploadingImageArrayTask<T>(formFile,request,parser).execute();
	}
	
	private class UploadingImageArrayTask<T extends BaseResponse>  extends AsyncTask<String, Integer, String> {
		private FormFile formFile;
		private Request request;
		private BaseParser<T> parser;
		public UploadingImageArrayTask(FormFile formFile,Request request,BaseParser<T> parser) {
			super();
			this.formFile = formFile;
			this.request = request;
			this.parser = parser ; 
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String postResult = HttpRequester.postArray(request,formFile);
			return postResult;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(onUploadImageArrayCompleteListener != null){
				if(!TextUtils.isEmpty(result)){
					DataHull<T> response = parser.getParseResult(result);
					if(response != null && response.dataEntry != null ){
						onUploadImageArrayCompleteListener.onUploadImageArraySuccess(response.dataEntry);
					}else{
						onUploadImageArrayCompleteListener.onUploadImageArrayFailed();
					}
				}else{
					onUploadImageArrayCompleteListener.onUploadImageArrayFailed();
				}
			}
		}
		
	}
	
	
	public <T extends BaseResponse> void upLoadingImage(Request request,FormFile formFile,BaseParser<T> parser ,OnUploadImageCompleteListener onUploadImageCompleteListener){
		this.onUploadImageCompleteListener = onUploadImageCompleteListener;
		new UploadingImageTask<T>(formFile,request,parser).execute();
	}
	
	private class UploadingImageTask<T extends BaseResponse> extends AsyncTask<String, Integer, String>{
		private FormFile formFile;
		private Request request;
		private BaseParser<T> parser;
		public UploadingImageTask(FormFile formFile,Request request,BaseParser<T> parser) {
			super();
			this.formFile = formFile;
			this.request = request;
			this.parser = parser ; 
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String postResult = HttpRequester.post(request,formFile);
			return postResult;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(onUploadImageCompleteListener != null){
				if(!TextUtils.isEmpty(result)){
					DataHull<T> response = parser.getParseResult(result);
					if(response != null && response.dataEntry!=null ){
						onUploadImageCompleteListener.onUploadImageSuccess(response.dataEntry,result);
					}else{
						onUploadImageCompleteListener.onUploadImageFailed(response.dataEntry);
					}
				}else{
					onUploadImageCompleteListener.onUploadImageFailed(null );
				}
			}
		}
		
	}
	
	/**
	 * 显示进度条对话框
	 * @param msg
	 */
	public void showProgressDialog(String msg){
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(msg);
		try {
			progressDialog.show();
		}catch (WindowManager.BadTokenException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * 隐藏正在加载的进度条
	 * 
	 */
	public void dismissProgressDialog(){
		if(null != progressDialog && progressDialog.isShowing() == true) {
			progressDialog.dismiss();
		}
	}
	
	public interface OnUploadImageCompleteListener<T extends BaseResponse>{
		public abstract void onUploadImageSuccess(T response, String resultString);
		public abstract void onUploadImageFailed(T response);
	}
	
	
	public interface OnUploadImageArrayCompleteListener{
		public abstract void onUploadImageArraySuccess(BaseResponse response);
		public abstract void onUploadImageArrayFailed();
	}
	
	

}
