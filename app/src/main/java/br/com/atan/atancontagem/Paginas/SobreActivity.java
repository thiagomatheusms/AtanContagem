package br.com.atan.atancontagem.Paginas;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import br.com.atan.atancontagem.R;

import static br.com.atan.atancontagem.MainActivity.VERSAO_APP;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        TextView textView = (TextView) findViewById(R.id.t_versao);
        String v = "Versão: " + VERSAO_APP;
        textView.setText(v);

        NotificationManager nm =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(R.drawable.ico);
    }
}
