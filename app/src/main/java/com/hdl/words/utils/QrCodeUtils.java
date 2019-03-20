package com.hdl.words.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.util.Hashtable;


public class QrCodeUtils {
    /*
     * 定义二维码的宽高
     */

    private static int WIDTH = 300;
    private static int HEIGHT = 300;
    private static String FORMAT = "png";//二维码格式

    //生成二维码
    public static Bitmap createQrCode(String data, int w, int h) {
        Bitmap resultBitmap = null;
        BitMatrix bitMatrix;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * w + x] = 0xff000000;
                    } else {
                        pixels[y * w + x] = 0xffffffff;
                    }
                }
            }
            resultBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            resultBitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return resultBitmap;
    }

    //识别二维码的函数
    public static String recogQRcode(ImageView imageView) {
        String result = null;
        Bitmap QRbmp = ((BitmapDrawable) (imageView).getDrawable()).getBitmap();   //将图片bitmap化
        int width = QRbmp.getWidth();
        int height = QRbmp.getHeight();
        int[] data = new int[width * height];
        QRbmp.getPixels(data, 0, width, 0, 0, width, height);    //得到像素
        RGBLuminanceSource source = new RGBLuminanceSource(QRbmp);   //RGBLuminanceSource对象
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result re;
        try {
            //得到结果
            re = reader.decode(bitmap);
            if (re.getText() != null) {
                result = re.getText();
            }
        } catch (NotFoundException | ChecksumException | FormatException e) {
            e.printStackTrace();
        }
        return result;
        /*//Toast出内容
        //利用正则表达式判断内容是否是URL，是的话则打开网页
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式

        Pattern pat = Pattern.compile(regex.trim());//比对
        Matcher mat = pat.matcher(re.getText().trim());
        if (mat.matches()) {
            Uri uri = Uri.parse(re.getText());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);//打开浏览器
            startActivity(intent);
        }*/
    }

    //识别图片所需要的RGBLuminanceSource类
    public static class RGBLuminanceSource extends LuminanceSource {
        private byte bitmapPixels[];

        protected RGBLuminanceSource(Bitmap bitmap) {
            super(bitmap.getWidth(), bitmap.getHeight());

            // 首先，要取得该图片的像素数组内容
            int[] data = new int[bitmap.getWidth() * bitmap.getHeight()];
            this.bitmapPixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
            bitmap.getPixels(data, 0, getWidth(), 0, 0, getWidth(), getHeight());

            // 将int数组转换为byte数组，也就是取像素值中蓝色值部分作为辨析内容
            for (int i = 0; i < data.length; i++) {
                this.bitmapPixels[i] = (byte) data[i];
            }
        }

        @Override
        public byte[] getMatrix() {
            // 返回我们生成好的像素数据
            return bitmapPixels;
        }

        @Override
        public byte[] getRow(int y, byte[] row) {
            // 这里要得到指定行的像素数据
            System.arraycopy(bitmapPixels, y * getWidth(), row, 0, getWidth());
            return row;
        }
    }

}


