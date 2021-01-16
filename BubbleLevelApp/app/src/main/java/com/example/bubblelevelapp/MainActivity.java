package com.example.bubblelevelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bubblelevelapp.Sensor.MySensorEventListener;

public class MainActivity extends AppCompatActivity {
ImageView oneDBubble,twoDBubble,cross1D,cross2D;
TextView values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oneDBubble=findViewById(R.id.bubbleImg);
        twoDBubble=findViewById(R.id.twoDBubbleImg);
        cross2D=findViewById(R.id.crossImg);
        cross1D=findViewById(R.id.crossOneDImg);
        values=findViewById(R.id.valuesTxt);
        new MySensorEventListener(this,oneDBubble,twoDBubble,cross1D,cross2D,values);
    }
}