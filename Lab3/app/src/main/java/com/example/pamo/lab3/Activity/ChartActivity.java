package com.example.pamo.lab3.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.pamo.lab3.R;

@SuppressLint("SetJavaScriptEnabled")
public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        WebView chartView = findViewById(R.id.webview_chart);
        chartView.getSettings().setJavaScriptEnabled(true);
        chartView.loadUrl("https://www.worldometers.info/coronavirus/country/poland/");
    }
}