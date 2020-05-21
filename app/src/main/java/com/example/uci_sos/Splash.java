package com.example.uci_sos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ActionMode;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView logosos = findViewById(R.id.logoLogin);
        //ImageView logouem = findViewById(R.id.logouem);
        ImageView logo = findViewById(R.id.europea);
        //TextView colaboracion = findViewById(R.id.colabo);
        Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.animacionlogo);
        Animation pulso = AnimationUtils.loadAnimation(this, R.anim.latidos);
        Animation aparece = AnimationUtils.loadAnimation(this, R.anim.animacionlogouem);
        Animation facein = AnimationUtils.loadAnimation(this, R.anim.animaciontexto);
        logosos.startAnimation(zoomin);
        logosos.startAnimation(pulso);
        //logouem.startAnimation(aparece);
        logo.startAnimation(facein);
        //colaboracion.startAnimation(facein);
        start();
    }
    private void start(){
        Handler han = new Handler();
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, Login.class);
                startActivityForResult(intent, 0);
                finish();
            }
        }, 4000);

    }
}