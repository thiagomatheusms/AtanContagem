package br.com.atan.atancontagem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.atan.atancontagem.Adapter.AdapterInventario;
import br.com.atan.atancontagem.Adapter.AdapterObservacao;
import br.com.atan.atancontagem.Classes.ConexaoJson;
import br.com.atan.atancontagem.Controles.ControleInventario;
import br.com.atan.atancontagem.Controles.ControleObservacao;
import br.com.atan.atancontagem.Inventarios.InventarioActivity;
import br.com.atan.atancontagem.Inventarios.ListaInventario;

public class ObservacaoActivity extends AppCompatActivity {

    ListView listaObs;
    String parametros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observacao);


        ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {

            String url2 = "http://www.ataneletro.com.br/AtanPed/AppContagem/baixar_obs.php";
            parametros = "NUM_INVENTARIO=" + InventarioActivity.NUMERO_INV_NUM;
            new ObservacaoActivity.BaixarObservacao().execute(url2);
        } else {
            Toast.makeText(getApplicationContext(), "Nenhuma Conexão estabelecida...", Toast.LENGTH_LONG).show();
            finish();
        }

//        registerForContextMenu(listaObs);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private class BaixarObservacao extends AsyncTask<String, JSONObject, JSONArray> {

        private ProgressDialog load;

        @Override
        protected void onPreExecute() {
            //MOSTRA A MENSAGEM PARA O USUARIO
            load = ProgressDialog.show(ObservacaoActivity.this, "Por favor Aguarde!",
                    "Listando observações, isso pode demorar um pouco...");
        }

        @Override
        protected JSONArray doInBackground(String... urls) {

            JSONArray jsonArray = null;

            //ARMAZENA NA LISTA O RETORNO
            String listaObservacaoJson = ConexaoJson.get(urls[0], parametros);

            try {

                JSONObject obj = new JSONObject(listaObservacaoJson);
                jsonArray = obj.getJSONArray("result_sql");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonArray;
        }


        @Override
        protected void onPostExecute(JSONArray array) {
            //RETIRA A MENSAGEM DA TELA
            load.dismiss();

            if (array != null) {

                ListView listaS = (ListView) findViewById(R.id.list_obs);
//                TextView sem_dados = (TextView) findViewById(R.id.t_status_sem_dados);

                int n = array.length();
                if (!array.toString().contains("erro_sem_dados")) {
                    AdapterObservacao customListAdapter = new AdapterObservacao(ObservacaoActivity.this, array);
                    listaS.setAdapter(customListAdapter);
                } else {
//                    sem_dados.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Sem dados para exibir!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
