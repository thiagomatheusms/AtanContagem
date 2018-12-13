package br.com.atan.atancontagem.Registros;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import br.com.atan.atancontagem.R;

public class ValidacaoErroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_erro);

        Button validarApp = (Button) findViewById(R.id.btn_voltarApp);
        validarApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ValidacaoErroActivity.this, RegistraAppActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
}
