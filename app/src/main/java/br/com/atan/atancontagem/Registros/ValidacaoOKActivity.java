package br.com.atan.atancontagem.Registros;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import br.com.atan.atancontagem.MainActivity;
import br.com.atan.atancontagem.R;

public class ValidacaoOKActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_ok);

        Button validarApp = (Button) findViewById(R.id.btn_continuarApp);
        validarApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ValidacaoOKActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
}
