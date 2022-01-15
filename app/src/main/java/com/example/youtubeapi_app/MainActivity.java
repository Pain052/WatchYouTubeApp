package com.example.youtubeapi_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        },2000);
    }
}