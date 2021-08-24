package com.oditly.gcipsssopoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean isAuthnticated = false;
    private boolean isError = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.loginokta);
        final TextView text = findViewById(R.id.idToken);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                String url = "https://8417-124-123-161-143.ngrok.io";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                // set toolbar color and/or setting custom actions before invoking build()
                // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                builder.setToolbarColor(Color.parseColor("#3e91f0"));

                CustomTabsIntent customTabsIntent = builder.build();
                // and launch the desired Url with CustomTabsIntent.launchUrl()
                customTabsIntent.launchUrl(v.getContext(), Uri.parse(url));
            }
        });

        Intent intent = getIntent();
        String action = intent.getAction();
        try{
            Uri data = intent.getData();
            //ID token
            String authKey = data.getQueryParameter("key");
            String status = data.getQueryParameter("status");
            // REFRESH TOKEN
            String refreshToken = data.getQueryParameter("refreshToken");

            // NOTE
            // We have to exchange the refreshToken to get a new ID token
            // Google provides a public endpoint to this
            // https://securetoken.googleapis.com/v1/token?key=[WEB_KEY]
            // We can find the WEB_KEY from a configured android firebase SDK as bellow
            // FirebaseApp.getInstance().getOptions().getApiKey()
            // The above code would extract WEB_KEY from google.services.json file
            // If you dont have the SDK configured in the android project , you can get this from Firebase console


            if(status.equals("ERROR")){
                this.isError=true;
            }else if(status.equals("SUCCESS")){
                this.isAuthnticated = true;
                Toast.makeText(getApplicationContext(),"Login success" , Toast.LENGTH_LONG).show();
                Log.d("ID_TOKEN", authKey);
                Log.d("REFRESH_TOKEN", refreshToken);

                text.setText(authKey);
            }

        }catch (Exception e){
            Log.e("INTENT_FILTER",e.getMessage());
        }



        //myWebView.loadUrl("https://okta.com");




    }
}