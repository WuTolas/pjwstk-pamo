package com.example.pamo.lab4.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.pamo.lab4.R

@SuppressLint("SetJavaScriptEnabled")
class ChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        val chartView = findViewById<WebView>(R.id.webview_chart)
        val loader = findViewById<ProgressBar>(R.id.webview_chart_loading)
        loader.visibility = View.VISIBLE
        chartView.visibility = View.GONE
        chartView.settings.javaScriptEnabled = true
        chartView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                chartView.loadUrl("javascript:$('body > :not(#graph-cases-daily)').hide();javascript:$('#graph-cases-daily').appendTo('body');")
                loader.visibility = View.GONE
                chartView.visibility = View.VISIBLE
            }

        }
        chartView.loadUrl("https://www.worldometers.info/coronavirus/country/poland/")
    }
}