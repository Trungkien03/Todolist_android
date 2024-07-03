package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper; // Import Looper

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Sử dụng Looper.getMainLooper() để đảm bảo chạy trên main thread
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish(); // Đóng SplashActivity sau khi chuyển sang MainActivity
            }
        }, 2000); // Giảm thời gian chờ xuống 2 giây (hoặc tùy chỉnh theo ý bạn)
    }
}