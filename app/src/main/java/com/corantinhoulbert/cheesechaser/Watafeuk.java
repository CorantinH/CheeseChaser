package com.corantinhoulbert.cheesechaser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Watafeuk extends Activity{
ImageView imgClick;
    ImageView carView;
    Activity lecontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watafeuk);

        lecontext = this;

        imgClick = (ImageView) findViewById(R.id.imageView3);

        imgClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent defineIntent = new Intent(lecontext, CheeseChaser.class);
                lecontext.startActivity(defineIntent);
            }
        });
    }
}
