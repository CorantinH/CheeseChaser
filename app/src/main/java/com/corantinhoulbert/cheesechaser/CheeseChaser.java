package com.corantinhoulbert.cheesechaser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CheeseChaser extends Activity {
    Activity lecontext;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheese_chaser);

         lecontext = this;
        Button btaction = (Button) findViewById(R.id.buttonPlay);
        //action sur le bouton click appelle de la nouvelle activité
        btaction.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent defineIntent = new Intent(lecontext, Watafeuk.class);
                lecontext.startActivity(defineIntent);
            }
        });
    }
}