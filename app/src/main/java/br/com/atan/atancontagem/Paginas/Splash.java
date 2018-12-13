package br.com.atan.atancontagem.Paginas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import br.com.atan.atancontagem.Controles.ControleSessaoLog;
import br.com.atan.atancontagem.MainActivity;
import br.com.atan.atancontagem.R;
import br.com.atan.atancontagem.Registros.RegistraAppActivity;

//import static br.com.atan.a1pedidos.Activity.MainActivity.SESSION_LOGADO;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){


                ControleSessaoLog SessaoLog = new ControleSessaoLog(getBaseContext());
                int linha = SessaoLog.VerificaSessao();

                if(linha > 0){
                    Intent i = new Intent(Splash.this, MainActivity.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(Splash.this, RegistraAppActivity.class);
                    startActivity(i);
                }

                //Intent i = new Intent(Splash.this, LoginActivity.class);
                //startActivity(i);

                      //Intent i = new Intent(Splash.this, MainActivity.class);
                      //startActivity(i);

                finish();

            }
        },SPLASH_TIME_OUT);
    }
}
