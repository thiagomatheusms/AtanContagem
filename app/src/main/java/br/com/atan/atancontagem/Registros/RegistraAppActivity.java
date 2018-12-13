package br.com.atan.atancontagem.Registros;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.atan.atancontagem.Classes.Sincronizar;
import br.com.atan.atancontagem.Controles.ControleSessaoLog;
import br.com.atan.atancontagem.R;

//import com.google.firebase.iid.FirebaseInstanceId;

public class RegistraAppActivity extends AppCompatActivity {

    private View mProgressView;
    EditText t_cod_chave;
    EditText t_nome_terminal;
    EditText t_email_ativa;

    String chave = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_app);

        t_cod_chave = (EditText) findViewById(R.id.t_cod_chave);
        Button validarApp = (Button) findViewById(R.id.btn_validarApp);

        validarApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = conn.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()){

                    String url = "http://www.ataneletro.com.br/AtanPed/AppContagem/ativaApp.php";
                    chave = t_cod_chave.getText().toString().trim();
                    new RegistraAppActivity.RegistraApp().execute(url);

                }
                else {
                    Toast.makeText(getApplicationContext(), "OFF LINE", Toast.LENGTH_LONG).show();
                }


//                ControleSessaoLog SessaoLog = new ControleSessaoLog(getBaseContext());
//                SessaoLog.InserirSessao(t_nome_terminal.toString(),"S");
//
//                Intent i = new Intent(RegistraAppActivity.this, ValidacaoOKActivity.class);
//                startActivity(i);
            }
        });

        mProgressView = findViewById(R.id.appAtivacao_progress);
    }


    private class RegistraApp extends AsyncTask<String,JSONObject,String> {

        private ProgressDialog load;

        @Override
        protected void onPreExecute(){
            //MOSTRA A MENSAGEM PARA O USUARIO
            load = ProgressDialog.show(RegistraAppActivity.this, "Por favor Aguarde!",
                    "Carregando...");
        }

        @Override
        protected String doInBackground(String... urls){

            //INSTANCIA A CLASSE
            JSONArray jsonArray = null;
            String retorno = null;
            Sincronizar conexao = new Sincronizar();

            String parametros = "DBA_ATIV_CHAVE="+ chave;
            //ARMAZENA NA LISTA O RETORNO

            String ret = conexao.postSinc(getBaseContext(),urls[0], parametros).trim();

            if(ret.contains("result_sql") && (!ret.contains("RESULT_ERRO_JSON"))){
                try {

                    JSONObject obj = new JSONObject(ret);
                    jsonArray = obj.getJSONArray("result_sql");

                    //CONTA OS REGISTROS DA LISTA
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        //PASSA COMO PARAMETRO A LINHA ATUAL
                        publishProgress(jsonObject);
                    }

                    retorno = "OK";

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                retorno = "ERRO";
            }

            return  retorno;
        }


        @Override
        protected void onProgressUpdate(JSONObject... objeto) {

            //RECEBE A LISTA
            JSONObject c = objeto[0];

            try {

                String varNOME = c.getString("dba_estq_nome");
//                String codigo = chave.substring(6,9);
                ControleSessaoLog SessaoLog = new ControleSessaoLog(getBaseContext());
//                Log.i("Msg: ", codigo);
                SessaoLog.InserirSessao(chave, chave, varNOME);

            }catch (Exception e){

            }


        }

        @Override
        protected void onPostExecute(String result) {
            load.dismiss();

            if(result.equals("OK")){

                Intent i = new Intent(RegistraAppActivity.this, ValidacaoOKActivity.class);
                startActivity(i);
                finish();
            }
            else {

                Intent i = new Intent(RegistraAppActivity.this, ValidacaoErroActivity.class);
                startActivity(i);
                finish();
            }

        }
    }

}
