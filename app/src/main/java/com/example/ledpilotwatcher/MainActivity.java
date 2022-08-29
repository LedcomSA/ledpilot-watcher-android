package com.example.ledpilotwatcher;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button b_play, b_stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startService(new Intent(MainActivity.this,WatchService.class));
        //}
        b_play =  (Button) findViewById(R.id.b_play);
        b_stop =  (Button) findViewById(R.id.b_stop);

        b_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startService(new Intent(MainActivity.this,WatchService.class));
                //}
            }
        });
        b_stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this,WatchService.class));
            }
        });

    }
}