package xyz.izen1231.web;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private WebView mWebView;
private WebSettings mWebSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PoliceActivity.class);
                startActivity(intent);
            }
        });

        Button button2= (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EleActivity.class);
                startActivity(intent);
            }
        });

        Button button3= (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GasActivity.class);
                startActivity(intent);
            }
        });


        mWebView = (WebView) findViewById(R.id.webview); //레이어와 연결
   mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
      mWebSettings = mWebView.getSettings(); //세부 세팅 등록
    mWebSettings.setJavaScriptEnabled(true); // 자바스크립트 사용 허용
        mWebView.loadUrl("https://m.naver.com"); //원하는 URL  입력

    }

    }

