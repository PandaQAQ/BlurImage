# BlurImage
==imageView图片模糊处理作为父控件背景小demo==
**说明**
本例是通过获取ImageView中的Bitmap位图后进行模糊处理再设置为其他控件的背景图。
先上效果图：
![效果图][1]
可以看到图上有采样间隔和模糊度两个seekbar。

**采样间隔**：做模糊处理这类的涉及到计算的操作图片大小越小速度越快是可以肯定的。而处理效果是模糊效果，因此对原图进行采样降低待处理图的清晰度来达到提高处理速度的方式是可取的。
**模糊度**：及对图片进行模糊计算处理而不是像采样那样仅仅是通过按长宽比来缩放图片大小。图中变化不是太明显的原因是采样设为1的时候的模糊效果，seekbar的范围我设置的0-5.将采样间隔加大的话效果会很明显，我也不推荐使用原图来模糊处理

  [1]: ./images/11.gif "11.gif"