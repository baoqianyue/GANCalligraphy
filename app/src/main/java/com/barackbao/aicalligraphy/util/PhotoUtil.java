package com.barackbao.aicalligraphy.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BarackBao on 2019/4/6;
 */
public class PhotoUtil {
    // start camera
    public static String start_camera(Activity activity, int requestCode) {
        Uri imageUri;
        // save image in cache path
        File outputImage = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/lite_mobile/", System.currentTimeMillis() + ".jpg");
        Log.d("outputImage", outputImage.getAbsolutePath());
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            File out_path = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/lite_mobile/");
            if (!out_path.exists()) {
                out_path.mkdirs();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            // compatible with Android 7.0 or over
            imageUri = FileProvider.getUriForFile(activity,
                    "com.barackbao.aicalligraphy.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        // set system camera Action
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // set save photo path
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        // set photo quality, min is 0, max is 1
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        activity.startActivityForResult(intent, requestCode);
        // return image absolute path
        return outputImage.getAbsolutePath();
    }

    // get picture in photo
    public static void use_photo(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    // get photo from Uri
    public static String get_path_from_URI(Context context, Uri uri) {
        String result;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    // TensorFlow model，get predict data
    public static ByteBuffer getScaledMatrix(Bitmap bitmap, int[] ddims) {
        ByteBuffer imgData = ByteBuffer.allocateDirect(ddims[0] * ddims[1] * ddims[2] * ddims[3] * 4);
        imgData.order(ByteOrder.nativeOrder());
        // get image pixel
        int[] pixels = new int[ddims[2] * ddims[3]];
        Bitmap bm = Bitmap.createScaledBitmap(bitmap, ddims[2], ddims[3], false);
        bm.getPixels(pixels, 0, bm.getWidth(), 0, 0, ddims[2], ddims[3]);
        int pixel = 0;
        for (int i = 0; i < ddims[2]; ++i) {
            for (int j = 0; j < ddims[3]; ++j) {
                final int val = pixels[pixel++];
                imgData.putFloat(((((val >> 16) & 0xFF) - 128f) / 128f));
                imgData.putFloat(((((val >> 8) & 0xFF) - 128f) / 128f));
                imgData.putFloat((((val & 0xFF) - 128f) / 128f));
            }
        }

        if (bm.isRecycled()) {
            bm.recycle();
        }
        return imgData;
    }

    // compress picture
    public static Bitmap getScaleBitmap(String filePath) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opt);

        int bmpWidth = opt.outWidth;
        int bmpHeight = opt.outHeight;
        int maxSize = 500;
        // compress picture with inSampleSize
        opt.inSampleSize = 1;
        while (true) {
            if (bmpWidth / opt.inSampleSize < maxSize || bmpHeight / opt.inSampleSize < maxSize) {
                break;
            }
            opt.inSampleSize *= 2;
        }
        opt.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, opt);
    }

    public static List<ImagePiece> split(Bitmap bitmap, int xPiece, int yPiece) {
        List<ImagePiece> pieces = new ArrayList<>(xPiece * yPiece);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int pieceWidth = width / 3;
        int pieceHeight = height / 3;

        for (int i = 0; i < yPiece; i++) {
            for (int j = 0; j < xPiece; j++) {
                ImagePiece piece = new ImagePiece();
                piece.index = j + i * xPiece;
                int xValue = j * pieceWidth;
                int yValue = i * pieceHeight;

                Bitmap temp = Bitmap.createBitmap(bitmap, xValue, yValue, pieceWidth, pieceHeight);
                temp = ThumbnailUtils.extractThumbnail(temp, 8, 8);
                piece.bitmap = convertGreyImg(temp);
                pieces.add(piece);
            }
        }
        return pieces;
    }


    /*public static String contrastBitmap(Bitmap a, Bitmap b) {

        //保存图像所有像素个数的数组
        int[] pixelsOne = new int[a.getWidth() * a.getHeight()];
        int[] pixelsTwo = new int[b.getWidth() * b.getHeight()];

        a.getPixels(pixelsOne, 0, a.getWidth(), 0, 0, a.getWidth(), a.getHeight());
        b.getPixels(pixelsTwo, 0, b.getWidth(), 0, 0, b.getWidth(), b.getHeight());
        int t = 0;
        int f = 0;
        //如果图片一个像素大于图片2的像素，就用像素少的作为循环条件。避免报错
        if (pixelsOne.length >= pixelsTwo.length) {
            //对每一个像素的RGB值进行比较
            for (int i = 0; i < pixelsTwo.length; i++) {
                int clr_one = pixelsOne[i];
                int clr_two = pixelsTwo[i];
                //RGB值一样就加一（以便算百分比）
                if (clr_one == clr_two) {
                    t++;
                } else {
                    f++;
                }
            }
        } else {
            for (int i = 0; i < pixelsOne.length; i++) {
                int clr_one = pixelsOne[i];
                int clr_two = pixelsTwo[i];
                if (clr_one == clr_two) {
                    t++;
                } else {
                    f++;
                }
            }

        }

        return "相似度为：" + myPercent(t, t + f);
    }


    public static String myPercent(int y, int z) {
        String baifenbi = ""; //接受百分比的值
        double baiy = y * 1.0;
        double baiz = z * 1.0;
        double fen = baiy / baiz;
        DecimalFormat df1 = new DecimalFormat("00.00%"); //##.00%   百分比格式，后面不足2位的用0补齐
        baifenbi = df1.format(fen);
        return baifenbi;
    }*/


    public static int contrastBitmap(Bitmap a, Bitmap b) {
        a = convertGreyImg(a);
        b = convertGreyImg(b);
        int aAvg = getAvg(a);
        int bAvg = getAvg(b);
        String aBinary = getBinary(a, aAvg);
        String bBinary = getBinary(b, bAvg);

        String aHex = binaryString2hexString(aBinary);
        String bHex = binaryString2hexString(bBinary);

        int diffNum = diff(aHex, bHex);
        return diffNum;
    }

    /**
     * 将彩色图转换为灰度图
     *
     * @param img 位图
     * @return 返回转换好的位图
     */
    public static Bitmap convertGreyImg(Bitmap img) {
        int width = img.getWidth();         //获取位图的宽
        int height = img.getHeight();       //获取位图的高

        int[] pixels = new int[width * height]; //通过位图的大小创建像素点数组

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int original = pixels[width * i + j];
//                System.out.println("pixels[" + (width * i + j) + "]原色=" + original);

//                if (original == 0) { // 透明转为白色，不需要，因为求平均值后再转换会变黑
//                    pixels[width * i + j] = -1;
//                } else {
                int red = ((original & 0x00FF0000) >> 16);
                int green = ((original & 0x0000FF00) >> 8);
                int blue = (original & 0x000000FF);

                int grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
//                    System.out.println("pixels[" + (width * i + j) + "]grey=" + grey);
//                }

            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }

    public static int getAvg(Bitmap img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[] pixels = new int[width * height];
        img.getPixels(pixels, 0, width, 0, 0, width, height);

        int avgPixel = 0;
        for (int pixel : pixels) {
            avgPixel += pixel;
        }
        return avgPixel / pixels.length;
    }

    public static String getBinary(Bitmap img, int average) {
        StringBuilder sb = new StringBuilder();

        int width = img.getWidth();
        int height = img.getHeight();
        int[] pixels = new int[width * height];

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int original = pixels[width * i + j];
                if (original >= average) {
                    pixels[width * i + j] = 1;
                } else {
                    pixels[width * i + j] = 0;
                }
                sb.append(pixels[width * i + j]);
            }
        }
        return sb.toString();
    }

    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuilder sb = new StringBuilder();
        int iTmp;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            sb.append(Integer.toHexString(iTmp));
        }
        return sb.toString();
    }

    public static int diff(String s1, String s2) {
        char[] s1s = s1.toCharArray();
        char[] s2s = s2.toCharArray();
        int diffNum = 0;
        for (int i = 0; i < s1s.length; i++) {
            if (s1s[i] != s2s[i]) {
                diffNum++;
            }
        }
        return diffNum;
    }
}

