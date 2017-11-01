package com.letv.handleassistant.widget;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 鏈夊脊鎬х殑ScrollView
 * 瀹炵幇涓嬫媺寮瑰洖鍜屼笂鎷夊脊鍥� * @author zhangjg
 * @date Feb 13, 2014 6:11:33 PM
 */
public class ReboundScrollView extends ScrollView {
	
	private static final String TAG = "ElasticScrollView";
	
	//绉诲姩鍥犲瓙, 鏄竴涓櫨鍒嗘瘮, 姣斿鎵嬫寚绉诲姩浜�00px, 閭ｄ箞View灏卞彧绉诲姩50px
	//鐩殑鏄揪鍒颁竴涓欢杩熺殑鏁堟灉
	private static final float MOVE_FACTOR = 0.5f;
	
	//鏉惧紑鎵嬫寚鍚� 鐣岄潰鍥炲埌姝ｅ父浣嶇疆闇�鐨勫姩鐢绘椂闂�	
	private static final int ANIM_TIME = 300;
	
	//ScrollView鐨勫瓙View锛�涔熸槸ScrollView鐨勫敮涓�竴涓瓙View
	private View contentView;
	
	//鎵嬫寚鎸変笅鏃剁殑Y鍊� 鐢ㄤ簬鍦ㄧЩ鍔ㄦ椂璁＄畻绉诲姩璺濈
	//濡傛灉鎸変笅鏃朵笉鑳戒笂鎷夊拰涓嬫媺锛�浼氬湪鎵嬫寚绉诲姩鏃舵洿鏂颁负褰撳墠鎵嬫寚鐨刌鍊�	
	private float startY;
	
	//鐢ㄤ簬璁板綍姝ｅ父鐨勫竷灞�綅缃�	
	private Rect originalRect = new Rect();
	
	//鎵嬫寚鎸変笅鏃惰褰曟槸鍚﹀彲浠ョ户缁笅鎷�	
	private boolean canPullDown = false;
	
	//鎵嬫寚鎸変笅鏃惰褰曟槸鍚﹀彲浠ョ户缁笂鎷�	
	private boolean canPullUp = false;
	
	//鍦ㄦ墜鎸囨粦鍔ㄧ殑杩囩▼涓褰曟槸鍚︾Щ鍔ㄤ簡甯冨眬
	private boolean isMoved = false;

	public ReboundScrollView(Context context) {
		super(context);
	}
	
	public ReboundScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public interface OnTouchEventMoveListenre {

		public void onSlideUp(int mOriginalHeaderHeight, int mHeaderHeight);

		public void onSlideDwon(int mOriginalHeaderHeight, int mHeaderHeight);

		public void onSlide(int alpha);
	}
	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			contentView = getChildAt(0);
		}
		super.onFinishInflate();
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		
		if(contentView == null) return;

		//ScrollView涓殑鍞竴瀛愭帶浠剁殑浣嶇疆淇℃伅, 杩欎釜浣嶇疆淇℃伅鍦ㄦ暣涓帶浠剁殑鐢熷懡鍛ㄦ湡涓繚鎸佷笉鍙�		
		originalRect.set(contentView.getLeft(), contentView.getTop(), contentView
				.getRight(), contentView.getBottom());
	}
	
	/**
	 * 鍦ㄨ鏂规硶涓幏鍙朣crollView涓殑鍞竴瀛愭帶浠剁殑浣嶇疆淇℃伅
	 * 杩欎釜浣嶇疆淇℃伅鍦ㄦ暣涓帶浠剁殑鐢熷懡鍛ㄦ湡涓繚鎸佷笉鍙�	 */
	
	/**
	 * 鍦ㄨЕ鎽镐簨浠朵腑, 澶勭悊涓婃媺鍜屼笅鎷夌殑閫昏緫
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		
		if (contentView == null) {
			return super.dispatchTouchEvent(ev);
		}

		int action = ev.getAction();
		
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			
			//鍒ゆ柇鏄惁鍙互涓婃媺鍜屼笅鎷�			
			canPullDown = isCanPullDown();
			canPullUp = isCanPullUp();
			
			//璁板綍鎸変笅鏃剁殑Y鍊�			
			startY = ev.getY();
			break;
			
		case MotionEvent.ACTION_UP:
			
			if(!isMoved) break;  //濡傛灉娌℃湁绉诲姩甯冨眬锛�鍒欒烦杩囨墽琛�			
			// 寮�惎鍔ㄧ敾
			TranslateAnimation anim = new TranslateAnimation(0, 0, contentView.getTop(),
					originalRect.top);
			anim.setDuration(ANIM_TIME);
			
			contentView.startAnimation(anim);
			
			// 璁剧疆鍥炲埌姝ｅ父鐨勫竷灞�綅缃�			
			contentView.layout(originalRect.left, originalRect.top, 
					originalRect.right, originalRect.bottom);
			
			//灏嗘爣蹇椾綅璁惧洖false
			canPullDown = false;
			canPullUp = false;
			isMoved = false;
			
			break;
		case MotionEvent.ACTION_MOVE:
			
			//鍦ㄧЩ鍔ㄧ殑杩囩▼涓紝 鏃㈡病鏈夋粴鍔ㄥ埌鍙互涓婃媺鐨勭▼搴︼紝 涔熸病鏈夋粴鍔ㄥ埌鍙互涓嬫媺鐨勭▼搴�			
			if(!canPullDown && !canPullUp) {
				startY = ev.getY();
				canPullDown = isCanPullDown();
				canPullUp = isCanPullUp();
				
				break;
			}
			
			//璁＄畻鎵嬫寚绉诲姩鐨勮窛绂�			
			float nowY = ev.getY();
			int deltaY = (int) (nowY - startY);
			
			//鏄惁搴旇绉诲姩甯冨眬
			boolean shouldMove = 
					(canPullDown && deltaY > 0)    //鍙互涓嬫媺锛�骞朵笖鎵嬫寚鍚戜笅绉诲姩
					|| (canPullUp && deltaY< 0)    //鍙互涓婃媺锛�骞朵笖鎵嬫寚鍚戜笂绉诲姩
					|| (canPullUp && canPullDown); //鏃㈠彲浠ヤ笂鎷変篃鍙互涓嬫媺锛堣繖绉嶆儏鍐靛嚭鐜板湪ScrollView鍖呰９鐨勬帶浠舵瘮ScrollView杩樺皬锛�			
			if(shouldMove){
				
				//璁＄畻鍋忕Щ閲�				
				int offset = (int)(deltaY * MOVE_FACTOR);
				
				//闅忕潃鎵嬫寚鐨勭Щ鍔ㄨ�绉诲姩甯冨眬
				contentView.layout(originalRect.left, originalRect.top + offset,
						originalRect.right, originalRect.bottom + offset);
				
				isMoved = true;  //璁板綍绉诲姩浜嗗竷灞�			
				}
			
			break;
		default:
			break;
		}

		return super.dispatchTouchEvent(ev);
	}
	

	/**
	 * 鍒ゆ柇鏄惁婊氬姩鍒伴《閮�	 */
	private boolean isCanPullDown() {
		return getScrollY() == 0 || 
				contentView.getHeight() < getHeight() + getScrollY();
	}
	
	/**
	 * 鍒ゆ柇鏄惁婊氬姩鍒板簳閮�	 */
	private boolean isCanPullUp() {
		return  contentView.getHeight() <= getHeight() + getScrollY();
	}
	
}
