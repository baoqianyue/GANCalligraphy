package com.barackbao.aicalligraphy.classifier;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BarackBao on 2019/4/7;
 */
public class KaiClassifier {


    private static final String TAG = "KaiClassifier.class";
    //模型中输入变量名称
    private static final String inputName = "inputs";
    private static final boolean[] isTrain = {false};
    //模型输出变量名称
    private static final String probName = "probability";
    private static final String outputName = "predict";
    //图片维度

    private static final int IMAGE_SIZE = 64;

    TensorFlowInferenceInterface inferenceInterface;

    static {
        System.loadLibrary("tensorflow_inference");
        Log.e(TAG, "tensorflow_inference.so 加载成功");
    }

    public KaiClassifier(AssetManager assetManager, String modelPath) {
        inferenceInterface = new TensorFlowInferenceInterface(assetManager, modelPath);
        Log.e(TAG, "model 加载成功");
    }


    private float[] getPixels(Bitmap bitmap) {
        int[] intValues = new int[IMAGE_SIZE * IMAGE_SIZE];
        float[] floatValues = new float[IMAGE_SIZE * IMAGE_SIZE];
        if (bitmap.getWidth() != IMAGE_SIZE || bitmap.getHeight() != IMAGE_SIZE) {
//            bitmap = ThumbnailUtils.extractThumbnail(bitmap, IMAGE_SIZE, IMAGE_SIZE);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scaleWidth = ((float) IMAGE_SIZE) / width;
            float scaleHeight = ((float) IMAGE_SIZE) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        }
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        Log.e(TAG, "value length:" + intValues.length);

        for (int i = 0; i < intValues.length; i++) {
            final int val = intValues[i];
            floatValues[i] = Color.red(val) / 127.5f - 1.0f;
        }
        return floatValues;
    }


    public List predict(Bitmap bitmap) {
        List<Object> resList = new ArrayList<>();
        float[] inputData = getPixels(bitmap);
//        Log.e(TAG, "inputdata[43]: " + inputData[388]);

        inferenceInterface.feed(inputName, inputData, 1, IMAGE_SIZE, IMAGE_SIZE, 1);
        inferenceInterface.feed("istrain", isTrain);

        //运行模型
        String[] outputNames = new String[]{outputName, probName};
        inferenceInterface.run(outputNames, true);

        //获取结果
        int[] labels = new int[1];
        inferenceInterface.fetch(outputName, labels);
        int label = labels[0];
        // label: 0：柳，1：欧，2：颜，3：赵
        resList.add(label);
        Log.e(TAG, "predict: label" + label);
        float[] prob = new float[4];
        inferenceInterface.fetch(probName, prob);

        DecimalFormat format = new DecimalFormat("0.000000");
        float label_prob = prob[label];
        Log.e(TAG, "label_prob: " + label_prob);
        resList.add(label_prob);

        return resList;
    }

}
