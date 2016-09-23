# BlurImage
imageView图片模糊处理作为父控件背景小demo
**说明**
本例是通过获取ImageView中的Bitmap位图后进行模糊处理再设置为其他控件的背景图。
先上效果图：
![效果图][1]
可以看到图上有采样间隔和模糊度两个seekbar。

**采样间隔**：做模糊处理这类的涉及到计算的操作图片大小越小速度越快是可以肯定的。而处理效果是模糊效果，因此对原图进行采样降低待处理图的清晰度来达到提高处理速度的方式是可取的。
**模糊度**：及对图片进行模糊计算处理而不是像采样那样仅仅是通过按长宽比来缩放图片大小。图中变化不是太明显的原因是采样设为1的时候的模糊效果，seekbar的范围我设置的0-5.将采样间隔加大的话效果会很明显，我也不推荐使用原图来模糊处理

**代码**
实现代码也很简单
1.获取ImageView显示的Bitmap bitmap
2.创建一个新的Bitmap overlay，这里的大小按照自己觉得理想的采样频率来设置。以达到缩小图片大小的目的
3.使用canvas将原图bitmap绘制到较小的overlay上
4.对overlay进行模糊处理
5.处理后返回的bitmap设置为目标控件的背景图
``` java
    private void initBackGround(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        //创建一个长宽等比缩小的Bitmap
        int bitmap_width = bitmap.getWidth();
        int bitmap_hight = bitmap.getHeight();
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
```
[demo地址][2]


  [1]: http://oddbiem8l.bkt.clouddn.com/11.gif "11.gif"
  [2]: https://github.com/PandaQAQ/BlurImage.git