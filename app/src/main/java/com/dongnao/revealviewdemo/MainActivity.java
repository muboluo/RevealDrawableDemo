package com.dongnao.revealviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RevealDrawable2 rd = new RevealDrawable2(
                getResources().getDrawable(R.drawable.avft),
                getResources().getDrawable(R.drawable.avft_active)
        );

        ImageView iv = findViewById(R.id.iv);

        iv.setBackground(rd);





    }
}
