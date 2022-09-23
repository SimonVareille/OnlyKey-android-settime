package com.github.simonvareille.onlykey_android_settime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_MANUAL_SET_TIME = "com.github.simonvareille.onlykey_android_settime.ACTION_MANUAL_SET_TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.setTimeButton);
        button.setOnClickListener(v -> setTime());
    }

    private void setTime() {
        // Create a new intent
        Intent setTimeIntent = new Intent(getApplicationContext(), SetTime.class);
        setTimeIntent.setAction(ACTION_MANUAL_SET_TIME);

        startService(setTimeIntent);
    }

}