package com.example.bubblelevelapp.Sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bubblelevelapp.MainActivity;
import com.example.bubblelevelapp.R;

import static java.lang.Math.abs;

public class MySensorEventListener implements SensorEventListener {
    private static final String TAG = "MySensorEventListener";
    private static double x = 0;
    private static double y = 0;
    private static float x_delta = 0;
    private static float x_delta_2_d = 0;
    private static float y_delta = 0;
    private static float init_x_delta = 0;
    private static float init_y_delta = 0;
    private TranslateAnimation oneDAnimation, twoDAnimation;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ImageView oneDBubble, twoDBubble, cross2D, cross1D;
    private TextView values;
    private Context context;

    public MySensorEventListener(Context context, ImageView oneDBubble, ImageView twoDBubble, ImageView cross1D, ImageView cross2D, TextView values) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "Sensor listener registered successfully");
        this.context = context;
        this.oneDBubble = oneDBubble;
        this.twoDBubble = twoDBubble;
        this.cross1D = cross1D;
        this.cross2D = cross2D;
        this.values=values;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        //RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) bubble.getLayoutParams();
        if (init_x_delta == 0 && init_y_delta == 0) {
            init_x_delta = event.values[0];
            init_y_delta = event.values[1];
            x_delta = init_x_delta;
            x_delta_2_d = init_x_delta;
            y_delta = init_y_delta;
            Log.d(TAG, "x delta initialized as : " + init_x_delta);
            Log.d(TAG, "y delta initialized as : " + init_y_delta);
        }
        if (abs(x - event.values[0]) > 0.1 || abs(y - event.values[1]) > 0.1) {
            Log.d(TAG, "onSensorChanged X : " + event.values[0] + " Y : " + event.values[1]);
            if (cross2D.getVisibility() == View.VISIBLE) cross2D.setVisibility(View.INVISIBLE);
            if (cross1D.getVisibility() == View.VISIBLE) cross1D.setVisibility(View.INVISIBLE);
            if (-10 < event.values[0] && event.values[0] < 10)
                x = event.values[0];
            oneDAnimation = new TranslateAnimation(x_delta, x_delta = (float) (x * 30.5),
                    0.0f, 0.0f);
            if (-10 < event.values[1] && event.values[1] < 10)
                y = event.values[1];
            twoDAnimation = new TranslateAnimation(x_delta_2_d, x_delta_2_d = (float) (x * 27.5f),
                    y_delta, y_delta = (float) (y * -27.5f));

            oneDAnimation.setFillEnabled(true);
            oneDAnimation.setFillAfter(true);
            twoDAnimation.setFillAfter(true);
            twoDAnimation.setFillEnabled(true);
            oneDAnimation.setDuration(100);
            twoDAnimation.setDuration(100);
            twoDBubble.startAnimation(twoDAnimation);
            oneDBubble.startAnimation(oneDAnimation);
            values.setText(String.format("X : %.2f  Y : %.2f", x_delta,y_delta));
        } else {
            if (cross2D.getVisibility() == View.VISIBLE)
                cross2D.setVisibility(View.INVISIBLE);
            if (cross1D.getVisibility() == View.VISIBLE)
                cross1D.setVisibility(View.INVISIBLE);

            if ((int) event.values[0] != 0 && (int) event.values[1] != 0) {
                oneDAnimation = new TranslateAnimation(x_delta, x_delta, 0.0f, 0.0f);
                twoDAnimation = new TranslateAnimation(x_delta_2_d, x_delta_2_d, y_delta, y_delta);
            }
            if ((int) event.values[0] == 0) {
                oneDAnimation = new TranslateAnimation(x_delta, init_x_delta, 0.0f, 0.0f);
                twoDAnimation = new TranslateAnimation(x_delta_2_d, init_x_delta, y_delta, y_delta);
                x_delta = init_x_delta;
                x_delta_2_d = init_x_delta;
                if (cross1D.getVisibility() == View.INVISIBLE)
                    cross1D.setVisibility(View.VISIBLE);
            }
            if ((int) event.values[1] == 0) {
                oneDAnimation = new TranslateAnimation(x_delta, x_delta, 0.0f, 0.0f);
                twoDAnimation = new TranslateAnimation(x_delta_2_d, x_delta_2_d, y_delta, init_y_delta);
                y_delta = init_y_delta;
            }
            if ((int) event.values[0] == 0 && (int) event.values[1] == 0) {
                oneDAnimation = new TranslateAnimation(x_delta, init_x_delta, 0.0f, 0.0f);
                twoDAnimation = new TranslateAnimation(x_delta_2_d, init_x_delta, y_delta, init_y_delta);
                x_delta = init_x_delta;
                x_delta_2_d = init_x_delta;
                y_delta = init_y_delta;
                if (cross2D.getVisibility() == View.INVISIBLE)
                    cross2D.setVisibility(View.VISIBLE);
                Log.d(TAG, "Phone on flat surface!");
            }
            oneDAnimation.setDuration(100);
            twoDAnimation.setDuration(100);
            oneDAnimation.setFillEnabled(true);
            oneDAnimation.setFillAfter(true);
            twoDAnimation.setFillAfter(true);
            twoDAnimation.setFillEnabled(true);
            twoDAnimation.setDuration(100);
            values.setText(String.format("X : %.2f  Y : %.2f", x_delta,y_delta));
            twoDBubble.startAnimation(twoDAnimation);
            oneDBubble.startAnimation(oneDAnimation);


        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
