package com.puzzle.jigsaw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
 
/**
 * 自定义容器
 * Created by LGL on 2016/5/2.
 */
public class GameView extends RelativeLayout implements View.OnClickListener {
 
 
    //默认3*3
    private int mColumn = 3;
    //容器的内边距
    private int mPadding;
    //小图的距离 dp
    private int mMagin = 3;
    //存储图片的，宽高 都是固定的，所以使用数组
    private ImageView[] mGameOintuItems;
    //宽度
    private int mItemWidth;
    //图片
    private Bitmap mBitmap;
    //切图后存储
    private List<ImagePiece> mItemBitmaps;
    //标记
    private boolean once;
 
    //容器的一个宽度
    private int mWidth;
 
 
    public GameView(Context context) {
 
        this(context, null);
    }
 
    public GameView(Context context, AttributeSet attrs) {
 
        this(context, attrs, 0);
    }
 
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
 
        init();
    }
 
    /**
     * 初始化
     */
    private void init() {
        //单位转换——dp-px
        mMagin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
 
        mPadding = min(getPaddingLeft(), getPaddingRight(), getPaddingTop(), getPaddingBottom());
    }
 
    /**
     * 确定当前布局的大小，我们要设置成正方形
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
 
        //拿到容器的高宽最小值
        mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
 
        if (!once) {
            //进行切图和排序
            initBitmap();
 
            //设置imageview(item)的宽高等属性
            initItem();
 
            once = true;
 
        }
        setMeasuredDimension(mWidth, mWidth);
    }
 
    /**
     * 进行切图和排序
     */
    private void initBitmap() {
        //判断是否存在这张图片
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        }
        //进行裁剪
        mItemBitmaps = ImageSplitterUtil.splitImage(mBitmap, mColumn);
 
        //裁剪玩后需要进行顺序打乱sort
        Collections.sort(mItemBitmaps, new Comparator<ImagePiece>() {
            @Override
            public int compare(ImagePiece lhs, ImagePiece rhs) {
 
                //生成随机数，如果》0.5返回1否则返回-1
                return Math.random() > 0.5 ? 1 : -1;
            }
        });
 
    }
 
    /**
     * 设置imageview(item)的宽高等属性
     */
    private void initItem() {
        //（ 容器的宽度 - 内边距 * 2  - 间距  ） /  裁剪的数量
        mItemWidth = (mWidth - mPadding * 2 - mMagin * (mColumn - 1)) / mColumn;
        //几 * 几
        mGameOintuItems = new ImageView[mColumn * mColumn];
 
        //开始排放
        for (int i = 0; i < mGameOintuItems.length; i++) {
            ImageView item = new ImageView(getContext());
            item.setOnClickListener(this);
            //设置图片
            item.setImageBitmap(mItemBitmaps.get(i).getBitmap());
            //保存
            mGameOintuItems[i] = item;
            //设置ID
            item.setId(i + 1);
            //设置Tag
            item.setTag(i + "_" + mItemBitmaps.get(i).getIndex());
 
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mItemWidth, mItemWidth);
 
            //判断不是最后一列
            if (i + 1 % mColumn != 0) {
                lp.rightMargin = mMagin;
            }
 
            //判断不是第一列
            if (i % mColumn != 0) {
                lp.addRule(RelativeLayout.RIGHT_OF, mGameOintuItems[i - 1].getId());
            }
 
            //判断如果不是第一行
            if ((i + 1) > mColumn) {
                lp.topMargin = mMagin;
                lp.addRule(RelativeLayout.BELOW, mGameOintuItems[i - mColumn].getId());
            }
            addView(item, lp);
        }
    }
 
    /**
     * 获取多个参数的最小值
     */
    private int min(int... params) {
        int min = params[0];
        //遍历
        for (int param : params) {
            if (param < min) {
                min = param;
            }
        }
        return min;
    }
 
    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
 
    }
}