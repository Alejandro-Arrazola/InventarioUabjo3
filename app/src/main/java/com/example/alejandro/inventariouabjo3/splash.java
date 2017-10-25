package com.example.alejandro.inventariouabjo3;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by Alejandro on 26/09/2017.
 */

public class splash  extends Activity {

/*
    private WebView web;
    ImageView gifView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        //gifView=(ImageView)findViewById(R.id.raspweb);
        gifView.setImageResource(R.drawable.splash);
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    finish();

                }
            }
        };
        timerThread.start();
    }*/

    public static final int segundos=5;
    public static final int misgundos=segundos*1000;
    public ProgressBar progreso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash);
        progreso=(ProgressBar)findViewById(R.id.progressBarsplash);
        progreso.setMax(segundos-2);
        empezaranimacion();
    }
    public void empezaranimacion()
    {
        new CountDownTimer(misgundos,1000)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                progreso.setProgress(progress(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                finish();

            }
        }.start();
    }
    public int progress (long milisegundos)
    {
        return (int)(misgundos-milisegundos)/1000;
    }



}
