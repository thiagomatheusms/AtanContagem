package br.com.atan.atancontagem.Inventarios;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.atan.atancontagem.Adapter.AdapterProdutos;
import br.com.atan.atancontagem.Classes.ConexaoJson;
import br.com.atan.atancontagem.Controles.ControleProdutos;
import br.com.atan.atancontagem.R;

public class ProdutosActivity extends AppCompatActivity {

    //ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    //NetworkInfo networkInfo = conn.getActiveNetworkInfo();

    String parametros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListaProdutos();

    }

    @Override
    public void onResume(){
        super.onResume();
        ListaProdutos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_produtos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_baixar_produtos) {

                String url1 = "http://www.ataneletro.com.br/AtanPed/AppContagem/baixar_produtos.php";
                parametros = "?";
                new ProdutosActivity.BaixarProdutos().execute(url1);
        }

        return super.onOptionsItemSelected(item);
    }

    private void ListaProdutos() {

        ListView listaS = (ListView) findViewById(R.id.ListViewProdutos);
        TextView status = (TextView) findViewById(R.id.t_status_sem_dados_pedidos_pendentes);

        ControleProdutos produto = new ControleProdutos(getBaseContext());
        Cursor dados = produto.BuscaProdutos();

        if(dados.getCount() > 0){

            AdapterProdutos customListAdapter = new AdapterProdutos(getBaseContext(), dados);
            listaS.setAdapter(customListAdapter);
            status.setVisibility(View.INVISIBLE);
        }
        else {
            status.setVisibility(View.VISIBLE);
        }

        listaS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                TextView id = (TextView) view.findViewById(R.id.t_view_ped_lista_tab_01_enviados_NUM_PEDIDO);
//                String nPed = id.getText().toString();
//                TextView status = (TextView) view.findViewById(R.id.t_view_ped_lista_tab_01_enviados_STATUS_PEDIDO);
//                String varStatus = status.getText().toString();
//
//                Intent it = new Intent(ProdutosActivity.this, InventarioActivity.class);
//                Bundle param = new Bundle();
//                param.putLong("paramNumPed", Long.parseLong(nPed));
//                param.putString("paramStatusPed", varStatus);
//                it.putExtras(param);
//                startActivity(it);

            }
        });


        listaS.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return true;
            }
        });
    }

    private class BaixarProdutos extends AsyncTask<String,JSONObject,JSONArray> {

        private ProgressDialog load;

        @Override
        protected void onPreExecute(){
            //MOSTRA A MENSAGEM PARA O USUARIO
            load = ProgressDialog.show(ProdutosActivity.this, "Por favor Aguarde!",
                    "Baixando produtos, isso pode demorar um pouco...");
        }

        @Override
        protected JSONArray doInBackground(String... urls) {

            JSONArray jsonArray = null;

            //ARMAZENA NA LISTA O RETORNO
            String listaProdutosJson = ConexaoJson.get(urls[0], parametros);
            ControleProdutos produto = new ControleProdutos(getBaseContext());
            produto.ExcluirProdutos();

            try {

                JSONObject obj = new JSONObject(listaProdutosJson);
                jsonArray = obj.getJSONArray("result_sql");

                //CONTA OS REGISTROS DA LISTA
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);

                    //PASSA COMO PARAMETRO A LINHA ATUAL
                    publishProgress(jsonObject);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return jsonArray;
        }

        @Override
        protected void onProgressUpdate(JSONObject... objeto) {
            //RECEBE A LISTA
            JSONObject c = objeto[0];

            try {

                Integer varDBA_PROD_CODIGO = c.getInt("DBA_PROD_CODIGO");
                String varDBA_PROD_DESCRICAO = c.getString("DBA_PROD_DESCRICAO");
                String varDBA_PROD_REFERENCIA = c.getString("DBA_PROD_REF");
                Long varDBA_PROD_EAN13 = c.getLong("DBA_PROD_EAN13");
                String varDBA_PROD_MARCA = c.getString("DBA_PROD_MARCA");
                Integer varDBA_PROD_SECAO = c.getInt("DBA_PROD_SECAO");
                Integer varDBA_PROD_GRUPO = c.getInt("DBA_PROD_GRUPO");
                Integer varDBA_PROD_SUBGRUPO = c.getInt("DBA_PROD_SUBGRUPO");

                //INSTANCIA O CONTROLE PRODUTOS
                ControleProdutos produto = new ControleProdutos(getBaseContext());
                //CONTA AS LINHAS DE RETORNO
                // int  qtd_registro = produto.VerificaProdutosImportado(varDBA_PROD_CODIGO).getCount();

                produto.InserirProduto(
                        varDBA_PROD_CODIGO,
                        varDBA_PROD_DESCRICAO,
                        varDBA_PROD_REFERENCIA,
                        varDBA_PROD_EAN13,
                        varDBA_PROD_MARCA,
                        varDBA_PROD_SECAO,
                        varDBA_PROD_GRUPO,
                        varDBA_PROD_SUBGRUPO
                );



//                if(qtd_registro == 0){
//
//                    //GRAVA OS PRODUTOS NO SQLITE LOCAL
//                    produto.InserirProduto(
//                            varDBA_PROD_CODIGO,
//                            varDBA_PROD_DESCRICAO,
//                            varDBA_PROD_REFERENCIA,
//                            varDBA_PROD_EAN13,
//                            varDBA_PROD_MARCA,
//                            varDBA_PROD_SECAO,
//                            varDBA_PROD_GRUPO,
//                            varDBA_PROD_SUBGRUPO
//                    );
//                }
//                else{
//                    //ALTERAR OS PRODUTOS NO SQLITE LOCAL
//                    produto.AlterarProdutos(
//                            varDBA_PROD_CODIGO,
//                            varDBA_PROD_DESCRICAO,
//                            varDBA_PROD_REFERENCIA,
//                            varDBA_PROD_EAN13,
//                            varDBA_PROD_MARCA,
//                            varDBA_PROD_SECAO,
//                            varDBA_PROD_GRUPO,
//                            varDBA_PROD_SUBGRUPO
//                    );
//                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }


        }

        @Override
        protected void onPostExecute(JSONArray lista) {
            //RETIRA A MENSAGEM DA TELA
            load.dismiss();

            //SE O RETORNO FOR NULLO
            if (lista == null) {
                Toast.makeText(getApplicationContext(), "Nenhum produto para baixar...", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Os produtos foram atualizados.", Toast.LENGTH_LONG).show();
            }


            //ATUALIZA O LIST VIEW
            ListaProdutos();
        }
    }

}
