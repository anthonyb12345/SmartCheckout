package com.horizon.procart.procart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.Toast;
import org.tensorflow.lite.Interpreter;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.MatOfFloat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class VegetableDetection {

    private Interpreter tfliteModel;

    public VegetableDetection(Context context) {
        // Initialize the model
        try {
            tfliteModel = new Interpreter(loadModelFile(context));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error loading model", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to load the YOLOv5 model from assets
    private MappedByteBuffer loadModelFile(Context context) throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd("best.tflite");
        FileInputStream inputStream = fileDescriptor.createInputStream();
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // Method to handle detection logic and return the detected vegetable
    public String detectVegetable(Mat frame) {
        // Convert OpenCV Mat to Bitmap
        Bitmap bitmap = matToBitmap(frame);

        // Preprocess the Bitmap (resize and normalize)
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, false);
        resizedBitmap = normalizeBitmap(resizedBitmap);

        // Run the model on the preprocessed bitmap
        float[][] output = new float[1][3]; // Assuming 3 classes (tomato, cucumber, potato)
        tfliteModel.run(resizedBitmap, output);

        // Parse the output to get the detected vegetable
        return parseDetectionOutput(output);
    }

    // Convert OpenCV Mat to Bitmap
    private Bitmap matToBitmap(Mat mat) {
        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, bitmap);
        return bitmap;
    }

    // Normalize the Bitmap (convert pixel values to range [0, 1])
    private Bitmap normalizeBitmap(Bitmap bitmap) {
        Bitmap normalizedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(normalizedBitmap);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return normalizedBitmap;
    }

    // Parse the YOLO output to determine the vegetable class
    private String parseDetectionOutput(float[][] output) {
        // Example logic to parse model output and map to vegetables
        // Assuming output contains class probabilities
        float tomatoScore = output[0][0]; // Tomato
        float cucumberScore = output[0][1]; // Cucumber
        float potatoScore = output[0][2]; // Potato

        // Identify the highest scoring vegetable
        if (tomatoScore > cucumberScore && tomatoScore > potatoScore) {
            return "tomato";
        } else if (cucumberScore > potatoScore) {
            return "cucumber";
        } else {
            return "potato";
        }
    }
}
