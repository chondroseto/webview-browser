package com.xela.browser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    EditText editText;
    ProgressBar progressBar;
    ImageButton back, forward, refresh, homeButton,goButton,hist_btn,full_screen,mini_screen,stop_btn;
    LinearLayout homeui,bottom_navigation;
    Button open_book1,open_book2,open_book3,open_book4,open_book5;
    RelativeLayout top_navigation;


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        webView.goBack();
        return;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeui =  findViewById(R.id.homepage);
        open_book1 =  findViewById(R.id.bookmark_btn_1);
        open_book2 = findViewById(R.id.bookmark_btn_2);
        open_book3 =  findViewById(R.id.bookmark_btn_3);
        open_book4 =  findViewById(R.id.bookmark_btn_4);
        open_book5 =  findViewById(R.id.bookmark_btn_5);


        top_navigation =  findViewById(R.id.top_nav);
        bottom_navigation =  findViewById(R.id.bottom_nav);
        mini_screen =  findViewById(R.id.miniscreen);
        full_screen =  findViewById(R.id.fullscreen);

        editText =  findViewById(R.id.address);
        hist_btn =  findViewById(R.id.history);
        back =  findViewById(R.id.back_arrow);
        forward =  findViewById(R.id.forward_arrow);
        goButton = findViewById(R.id.go_btn);
        refresh =  findViewById(R.id.refresh);
        stop_btn =  findViewById(R.id.stop);
        homeButton =  findViewById(R.id.home);

        progressBar =  findViewById(R.id.progress_bar);
        progressBar.setMax(100);

        webView =  findViewById(R.id.web_view);

        homepage();
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setSupportMultipleWindows(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setBackgroundColor(Color.WHITE);


            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    progressBar.setProgress(newProgress);
                    if ((newProgress > 0) && (newProgress < 100)) {
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                        editText.setText(webView.getUrl());
                        refresh.setVisibility(View.GONE);
                        stop_btn.setVisibility(View.VISIBLE);

                    }
                    if (newProgress == 100) {
                        progressBar.setVisibility(ProgressBar.GONE);
                        refresh.setVisibility(View.VISIBLE);
                        stop_btn.setVisibility(View.GONE);
                    }
                }
            });
        }
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == keyEvent.KEYCODE_ENTER){
                    webView.setVisibility(View.VISIBLE);
                    homeui.setVisibility(View.GONE);
                    try {
                        if(!NetworkState.connectionAvailable(MainActivity.this)){
                            Toast.makeText(MainActivity.this, "connect", Toast.LENGTH_SHORT).show();
                        }else {
                            if (String.valueOf(editText.getText()).contains(".")){
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                webView.loadUrl("https://" + editText.getText().toString());

                            }if (String.valueOf(editText.getText()).contains("https://")){
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                webView.loadUrl(editText.getText().toString());

                            }if (!String.valueOf(editText.getText()).contains(".")){
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                webView.loadUrl("https://www.bing.com/search?q=" + editText.getText().toString());

                            }else{
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                webView.loadUrl("https://" +editText.getText().toString());
                            }
                        }

                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.stopLoading();
            }
        });

        webView.setWebViewClient(new MyWebViewClient());

        mini_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top_navigation.setVisibility(View.VISIBLE);
                bottom_navigation.setVisibility(View.VISIBLE);
                mini_screen.setVisibility(View.GONE);
            }
        });
        full_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               top_navigation.setVisibility(View.GONE);
               bottom_navigation.setVisibility(View.GONE);
               mini_screen.setVisibility(View.VISIBLE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.GONE);
                homeui.setVisibility(View.VISIBLE);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoForward()) {
                    webView.goForward();
                }
            }
        });

        hist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, History.class);
                startActivity(intent);
            }
        });

    }

    public void homepage(){
        open_book1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                homeui.setVisibility(View.GONE);
                try {
                    if(!NetworkState.connectionAvailable(MainActivity.this)){
                        Toast.makeText(MainActivity.this, "connect", Toast.LENGTH_SHORT).show();
                    }else {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        webView.loadUrl("https://" + open_book1.getText().toString());
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        open_book2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                homeui.setVisibility(View.GONE);
                try {
                    if(!NetworkState.connectionAvailable(MainActivity.this)){
                        Toast.makeText(MainActivity.this, "connect", Toast.LENGTH_SHORT).show();
                    }else {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        webView.loadUrl("https://" + open_book2.getText().toString());
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        open_book3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                homeui.setVisibility(View.GONE);
                try {
                    if(!NetworkState.connectionAvailable(MainActivity.this)){
                        Toast.makeText(MainActivity.this, "connect", Toast.LENGTH_SHORT).show();
                    }else {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        webView.loadUrl("https://" + open_book3.getText().toString());
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        open_book4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                homeui.setVisibility(View.GONE);
                try {
                    if(!NetworkState.connectionAvailable(MainActivity.this)){
                        Toast.makeText(MainActivity.this, "connect", Toast.LENGTH_SHORT).show();
                    }else {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        webView.loadUrl("https://" + open_book4.getText().toString());
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        open_book5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                homeui.setVisibility(View.GONE);
                try {
                    if(!NetworkState.connectionAvailable(MainActivity.this)){
                        Toast.makeText(MainActivity.this, "connect", Toast.LENGTH_SHORT).show();
                    }else {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        webView.loadUrl("https://" + open_book5.getText().toString());
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}