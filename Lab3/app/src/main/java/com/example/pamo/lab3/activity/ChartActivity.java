package com.example.pamo.lab3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.pamo.lab3.R;

@SuppressLint("SetJavaScriptEnabled")
public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        WebView chartView = findViewById(R.id.webview_chart);
        ProgressBar loader = findViewById(R.id.webview_chart_loading);
        loader.setVisibility(View.VISIBLE);
        chartView.setVisibility(View.GONE);
        chartView.getSettings().setJavaScriptEnabled(true);
        chartView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                chartView.loadUrl("javascript:$('body > :not(#graph-cases-daily)').hide();javascript:$('#graph-cases-daily').appendTo('body');");
                loader.setVisibility(View.GONE);
                chartView.setVisibility(View.VISIBLE);
            }
        });
        chartView.loadUrl("https://www.worldometers.info/coronavirus/country/poland/");
    }
}