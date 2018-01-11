package com.TJApp.handchina.Activity;

import com.TJApp.handchina.R;
import com.TJApp.handchina.R.id;
import com.TJApp.handchina.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebActivity extends Activity{
	private WebView webView;
	private ProgressBar progressBar;
	private TextView textView;
	private ImageButton imageBack,imageRefresh;
	private String url;
	private String imageName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		webView = (WebView) findViewById(R.id.webView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		textView  = (TextView) findViewById(R.id.title);
		imageBack = (ImageButton) findViewById(R.id.back);
		imageRefresh = (ImageButton) findViewById(R.id.refresh);
		Intent intent = getIntent();
		imageName = intent.getStringExtra("titleName");
		url = intent.getStringExtra("textUrl");
		textView.setText(imageName);
		imageBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		imageRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				webView.reload();
			}
		});
		webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
           @Override
           public boolean shouldOverrideUrlLoading(WebView view, String url) {
               view.loadUrl(url);
               return true;
           }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
            }
        });
	}
}
