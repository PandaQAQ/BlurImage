package com.pandaq.blurimage.view.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.pandaq.blurimage.R;
import com.pandaq.blurimage.utils.FastBlur;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.center_split)
    View mCenterSplit;
    @BindView(R.id.image)
    CircleImageView mImage;
    @BindView(R.id.image_background)
    FrameLayout mImageBackground;
    float scaleFactor = 1;
    float blur = 1;
    @BindView(R.id.blur)
    SeekBar mBlur;
    @BindView(R.id.sampling)
    SeekBar mSampling;
    private int bitmap_width;
    private int bitmap_hight;
    private Bitmap image_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBlur.setOnSeekBarChangeListener(this);
        mSampling.setOnSeekBarChangeListener(this);
//        image_bitmap = ((BitmapDrawable) mImage.getDrawable()).getBitmap();
        initBackGround(mImage);
    }

    private void initBackGround(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        //创建一个长宽等比缩小的Bitmap
        bitmap_width = bitmap.getWidth();
        bitmap_hight = bitmap.getHeight();
        Bitmap overlay = Bitmap.createBitmap((int) (bitmap_width / scaleFactor), (int) (bitmap_hight / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        //将canvas按照bitmap等量缩放，模糊处理的图片才能显示正常
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        //对采样后的bitmap进行模糊处理，缩放采样后的图片处理比原图处理要省很多时间和内存开销
        overlay = FastBlur.doBlur(overlay, (int) blur, false);
        //模糊处理后的图片设置为头部布局背景图
        mImageBackground.setBackground(new BitmapDrawable(getResources(), overlay));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        //纠正seekbar的最小值为1即不采样不模糊
        if (i == 0) {
            i = 1;
        }
        switch (seekBar.getId()) {
            case R.id.sampling:
                scaleFactor = i;
                initBackGround(mImage);
                break;
            case R.id.blur:
                blur = i;
                initBackGround(mImage);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
