package com.mobileappsco.training.webviewhw;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    private EditText myEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuration of the WebView
        myWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Catching the URLs redirections while clicking something inside the WebView, and loading it inside the WebView
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                myWebView.loadUrl(url);
                myEditText.setText(url);
                return true;
            }
        });
        String javascript = "window.alert(\"This is an alert from javascript\");";
        myWebView.loadUrl(javascript);

        // Catching the event where the user clicks on the enter button on the keyboard, then hiding the keyboard and sending a click event to the Go button.
        myEditText = (EditText) findViewById(R.id.textURL);
        myEditText.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            loadURLintoWebView(findViewById(R.id.buttonGo));
                            myEditText.clearFocus();
                            hideKeyboard(v);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

    }

    // method to hide the keyboard, whether it's visible or not
    public void hideKeyboard(View v) {
        InputMethodManager imm =  (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    // method called when the Go button is pressed
    public void loadURLintoWebView(View view) {
        String url = myEditText.getText().toString();
        if (!url.contains("http://") && !url.contains("https://"))
            url = "http://"+url;
        myWebView.loadUrl(url);
        Log.d("MYLOG", "Loading: " + url);
        hideKeyboard(view);

    }

    // method called when the X button is pressed
    public void clearURL(View view) {
        myEditText.setText("");
    }

    // method called when the JS button is pressed
    public void sendJavascript(View view) {
        String javascript = "javascript:alert(\"Javascript - Title is '"+myWebView.getTitle()+"'\");";
        myWebView.loadUrl(javascript);
    }
}
