package com.example.refresh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 下拉刷新框架，是一个ViewGroup，里面最上面是下拉刷新View（可以使用默认的，可以自定义）
 * 里面的下边就根据项目添加控件
 */
public class GodRefreshLayout extends LinearLayout {
    private BaseRefreshManager mRefreshManager;
    private Context mContext;
    private View mHeaderView;
    private int mHeaderViewHeight;
    private int minHeadViewHeight; //头部布局最小高度，也就是隐藏了
    private int maxHeadViewHeight; //头部布局最大的一个高度,就是“下拉刷新”离上边界的距离最大到“下拉刷新”控件高度的0.3倍
    private RefreshingListener mRefreshListener;  //正在刷新回调接口
    private RecyclerView mRecyclerView;
    private ScrollView mScrollView;
    private int downY;
    private int interceptDownY;
    private int interceptDownX;

    public GodRefreshLayout(Context context) {
        super(context);
        initView(context);
    }

    public GodRefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GodRefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
    }

    //添加下拉刷新View
    //开启下拉刷新 下拉刷新布局的效果 是默认的
    public void setRefreshManager(){
        mRefreshManager = new DefaultRefreshManager(mContext);
        initHeaderView();
    }
    //开启下拉刷新  使用用户自定义的下拉刷新效果
    public void setRefreshManager(BaseRefreshManager manager){
        mRefreshManager = manager;
        initHeaderView();
    }
    //刷新完成后的操作,供GodRefreshLayout实例调用
    public void refreshOver(){
        hideHeadView(getHeadViewLayoutParams());
    }

    public interface RefreshingListener{
        void onRefreshing();
    }
    //自定义回调接口
    public void setRefreshListener(RefreshingListener refreshListener){
        this.mRefreshListener = refreshListener;
    }

    private void initHeaderView() {
        setOrientation(VERTICAL);
        mHeaderView = mRefreshManager.getHeaderView();
        mHeaderView.measure(0, 0);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        minHeadViewHeight = -mHeaderViewHeight;
        maxHeadViewHeight = (int) (mHeaderViewHeight * 0.3f);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeaderViewHeight);
        //隐藏“下拉刷新”
        params.topMargin = minHeadViewHeight;
        //将ulti_header_layout.xml转成的view（或用户自定义的）添加到GodRefreshLayout中
        //第二个参数将view放在最上面
        // 第三个参数定义了view的宽高属性，之所以传第三个参数是因为它有params.topMargin可以实现“下拉1刷新”的隐藏
        addView(mHeaderView, 0, params);
    }

    //这个方法回调时可以获取当前ViewGroupo的子View
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //先初始化布局，然后再动态添加HeadView，所以这个也传0
        View childAt = getChildAt(0);
        //获取RecyclerView
        if(childAt instanceof RecyclerView){
            mRecyclerView = (RecyclerView) childAt;
        }
        if(childAt instanceof ScrollView){
            mScrollView = (ScrollView) childAt;
        }
        // TODO: 20-4-29
        //获取其他view
    }

    //事件分发
    //这个方法能获取用户的点击事件
    //当我们手指触摸到屏幕时，系统会给我们生成一个MotionEvent
    //返回true表示对这个事件处理，false表示不处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            //手指按压,可以获取当前手指位置
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();
                return true;
            //手指移动，动态改变“下拉刷新”位置
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) event.getY();
                //ACTION_DOWN没有拦截，所以上面case执行不到，倒置HeadView直接划出，没有动画效果
                if(downY == 0){
                    downY = interceptDownY;
                }
                int dy = moveY - downY;
                if(dy > 0){
                    LayoutParams layoutParams = getHeadViewLayoutParams();
                    //阻尼效果:  /1.8f
                    int topMargin = (int) Math.min(dy/1.8f + minHeadViewHeight, maxHeadViewHeight);
                    //这个事件的处理是为了 不断回调这个比例 用于一些视觉效果，比如美团下拉
                    if(topMargin <= 0){
                        //这个值是从0到1进行变化
                        float percent = ((-minHeadViewHeight) - (-topMargin)) * 1.0f / (-minHeadViewHeight);
                        mRefreshManager.downRefreshPercent(percent);
                    }
                    // &&mCurrentRefreshState != RefreshState.DOWNREFRESSH 保证handleRefreshState()方法不重复执行
                    if(topMargin < 0 && mCurrentRefreshState != RefreshState.DOWNREFRESSH){
                        mCurrentRefreshState = RefreshState.DOWNREFRESSH;
                        //提示下拉刷新状态
                        handleRefreshState(mCurrentRefreshState);
                    }else if(topMargin >= 0 && mCurrentRefreshState != RefreshState.RELEASEREFRESH){
                        mCurrentRefreshState = RefreshState.RELEASEREFRESH;
                        //提示释放更新状态
                        handleRefreshState(mCurrentRefreshState);
                    }
                    layoutParams.topMargin = topMargin;
                    mHeaderView.setLayoutParams(layoutParams);
                }
                return true;
            //手指抬起，回弹效果
            case MotionEvent.ACTION_UP:
                //手指抬起，并且需要处理事件的时候才返回true
                if(handleEventUp(event)){
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    //用RecyclerView啥的需要拦截一下
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                interceptDownY = (int) ev.getY();
                interceptDownX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //1.确定滑动方向，只有上下滑动才会出发拦截
                int dy = (int) (ev.getY() - interceptDownY);
                int dx = (int) (ev.getX() - interceptDownX);
                if(Math.abs(dy) > Math.abs(dx) && dy > 0 && handleChildViewIsTop()){
                    //向下滑动，并且recyclerview在顶端时拦截
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    //判断子View是否是滑动到顶端的
    private boolean handleChildViewIsTop() {
        if(mRecyclerView != null){
            return RefreshScrollingUtil.isRecyclerViewToTop(mRecyclerView);
        }
        if(mScrollView != null){
            return RefreshScrollingUtil.isScrollViewOrWebViewToTop(mScrollView);
        }
        // TODO: 20-4-29
        //判断其他view是否到顶端
        return false;
    }

    private boolean handleEventUp(MotionEvent event) {
        final LayoutParams layoutParams = getHeadViewLayoutParams();
        //如果是下拉刷新状态 就回弹回去
        if(mCurrentRefreshState == RefreshState.DOWNREFRESSH){
            hideHeadView(layoutParams);
        }else if(mCurrentRefreshState == RefreshState.RELEASEREFRESH){
            //保持刷新状态
            layoutParams.topMargin = 0;
            mHeaderView.setLayoutParams(layoutParams);
            mCurrentRefreshState = RefreshState.REFRESHING;
            handleRefreshState(mCurrentRefreshState);
            if(mRefreshListener != null){
                mRefreshListener.onRefreshing();
            }
        }
        //”下拉刷新“确实往下滑动了，返回true，也就是需要处理事件完成回弹
        return layoutParams.topMargin > minHeadViewHeight;
    }

    private void hideHeadView(final LayoutParams layoutParams){
        //用属性动画实现回弹
        //用ValueAnimator.ofInt，如果用ValueAnimator.ofFloat会报错
        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.topMargin, minHeadViewHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                //根据属性动画移动的位置动态改变layoutParams.topMargin
                layoutParams.topMargin = animatedValue;
                mHeaderView.setLayoutParams(layoutParams);
            }
        });
        //动画执行完，也就是“下拉刷新”完全退回去之后，设置为IDDLE状态
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentRefreshState = RefreshState.IDDLE;
                handleRefreshState(mCurrentRefreshState);
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    private LayoutParams getHeadViewLayoutParams(){
        return (LayoutParams) mHeaderView.getLayoutParams();
    }

    private void handleRefreshState(RefreshState mCurrentRefreshState) {
        switch (mCurrentRefreshState){
            case IDDLE:
                mRefreshManager.iddRefresh();
                break;
            case DOWNREFRESSH:
                mRefreshManager.downRefresh();
                break;
            case RELEASEREFRESH:
                mRefreshManager.releaseRefresh();
                break;
            case REFRESHING:
                mRefreshManager.refreshing();
                break;
            default:
                break;
        }
    }

    private RefreshState mCurrentRefreshState = RefreshState.IDDLE;
    //定义下拉刷新的状态，依次为 静止、下拉刷新、释放刷新、正在刷新、刷新完成
    private enum RefreshState{
        IDDLE, DOWNREFRESSH, RELEASEREFRESH, REFRESHING
    }
}
