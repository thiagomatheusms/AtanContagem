package br.com.atan.atancontagem.Inventarios;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import br.com.atan.atancontagem.Adapter.AdapterContagem;
import br.com.atan.atancontagem.Adapter.AdapterInventario;
import br.com.atan.atancontagem.Classes.ConexaoJson;
import br.com.atan.atancontagem.Classes.Produtos;
import br.com.atan.atancontagem.Controles.BancoDados;
import br.com.atan.atancontagem.Controles.ControleContagem;
import br.com.atan.atancontagem.Controles.ControleInventario;
import br.com.atan.atancontagem.Controles.ControleObservacao;
import br.com.atan.atancontagem.Controles.ControlePermissoes;
import br.com.atan.atancontagem.Controles.ControleProdutos;
import br.com.atan.atancontagem.MainActivity;
import br.com.atan.atancontagem.Modelo.Inventario;
import br.com.atan.atancontagem.ObservacaoActivity;
import br.com.atan.atancontagem.R;


public class ListaInventario extends AppCompatActivity {

    String parametros, parametros2;
    ListView listaS;
    String statusLong;


    Integer _DBA_INV_NUM;
    Integer _DBA_INV_DATA;
    Integer _DBA_INV_LOJA;
    String _DBA_INV_OBS;
    String _DBA_INV_STATUS;
    String _DBA_INV_ENVIADO;


    String varStatusEnviado;

    List<Inventario> listaInventariosLocal = new ArrayList<>();
    List<Inventario> listaInventariosRemoto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_inventario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListaInventarios();
        registerForContextMenu(listaS);

        ControleInventario inv = new ControleInventario(getBaseContext());
        Inventario inventario = new Inventario();

        listaInventariosLocal = inv.Buscar();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        menu.add("Primeira Opção do Menu");
//        menu.add("Segunda Opção do Menu");
//        menu.add("Deletar Item");
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_acao_inventario, menu);

//        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_enviar_inventario:
                if (!statusLong.equalsIgnoreCase("FINALIZADO")) {
                    if (varStatusEnviado.equalsIgnoreCase("NAO")) {
                        //String url0 = "http://www.ataneletro.com.br/AtanPed/AppContagem/enviar_contagem.php";
                        String url0 = "http://www.ataneletro.com.br/AtanPed/AppContagem/enviar_contagem_json.php";
                        new ListaInventario.EnviarContagem().execute(url0);
                    } else {
                        Toast.makeText(ListaInventario.this, "Inválido, inventário já enviado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ListaInventario.this, "Inválido, o inventário está " + statusLong, Toast.LENGTH_SHORT).show();
                }
                break;


//            case R.id.action_observacoes:
//                Intent vaiPraTelaRestaurantes = new Intent(ListaInventario.this, ObservacaoActivity.class);
//                startActivity(vaiPraTelaRestaurantes);
//                break;

            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }


    private class EnviarContagem extends AsyncTask<String, Long, String> {
        private ProgressDialog load;

        @Override
        protected void onPreExecute() {
            //MOSTRA A MENSAGEM PARA O USUARIO
            load = ProgressDialog.show(ListaInventario.this, "Por favor Aguarde!",
                    "Enviado contagem...");
        }


        @Override
        protected String doInBackground(String... urls) {
            String ret = null;

            //ControlePedidosCapa sincCapa = new ControlePedidosCapa(getBaseContext());
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("HH:mm:ss");
            dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
            String horaEnvio = dateFormatGmt.format(new Date()) + "";


            ControleContagem contagem = new ControleContagem(getBaseContext());


            Cursor lista = contagem.BuscaProdutosContados(InventarioActivity.NUMERO_INV_NUM);
            ConexaoJson conexao = new ConexaoJson();

            String varDBA_CONT_INVENTARIO = "";

            JSONArray array_DET = new JSONArray();

            if (lista.getCount() > 0) {
                if (lista.moveToFirst()) {
                    do {
                        varDBA_CONT_INVENTARIO = lista.getString(lista.getColumnIndexOrThrow(BancoDados.DBA_CONT_INVENTARIO));
                        String varDBA_CONT_PRODUTO = lista.getString(lista.getColumnIndexOrThrow(BancoDados.DBA_CONT_PRODUTO));
                        String varDBA_CONT_QTD = lista.getString(lista.getColumnIndexOrThrow(BancoDados.DBA_CONT_QTD));
                        String varDBA_CONT_LOJA = lista.getString(lista.getColumnIndexOrThrow(BancoDados.DBA_CONT_LOJA));
                        String varDBA_CONT_TERMINAL = lista.getString(lista.getColumnIndexOrThrow(BancoDados.DBA_CONT_TERMINAL));

                        _DBA_INV_NUM = Integer.parseInt(varDBA_CONT_INVENTARIO);
                        //Date date = new Date();
                        //String varDATA_TRANS = MaskData.DDMMYYYY(date);
                        String param_dados = "DBA_CONT_INVENTARIO=" + varDBA_CONT_INVENTARIO + "&" +
                                "DBA_CONT_PRODUTO=" + varDBA_CONT_PRODUTO + "&" +
                                "DBA_CONT_QTD=" + varDBA_CONT_QTD + "&" +
                                "DBA_CONT_LOJA=" + varDBA_CONT_LOJA + "&" +
                                "DBA_CHAVE=" + MainActivity.SESSION_CHAVE + "&" +
                                "DBA_CONT_TERMINAL=" + varDBA_CONT_TERMINAL + " ";

//                     conexao.get(urls[0], param_dados);

                        try {
                            JSONObject object_DET = new JSONObject();

                            object_DET.put("DBA_CONT_INVENTARIO", varDBA_CONT_INVENTARIO);
                            object_DET.put("DBA_CONT_PRODUTO", varDBA_CONT_PRODUTO);
                            object_DET.put("DBA_CONT_QTD", varDBA_CONT_QTD);
                            object_DET.put("DBA_CONT_LOJA", varDBA_CONT_LOJA);
                            object_DET.put("DBA_CHAVE", MainActivity.SESSION_CHAVE);
                            object_DET.put("DBA_HORA", horaEnvio);
                            object_DET.put("DBA_CONT_TERMINAL", varDBA_CONT_TERMINAL);

                            array_DET.put(object_DET);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } while (lista.moveToNext());

                }
            }

            String json_post = "array=" + array_DET.toString();
            String re = conexao.get(urls[0], json_post);

//            ControleInventario inv = new ControleInventario(getBaseContext());
//            inv.AlterarStatusEnviado(Long.parseLong(varDBA_CONT_INVENTARIO), "SIM");
            return re;

        }

        @Override
        protected void onPostExecute(String c) {
            super.onPostExecute(c);
            load.dismiss();

            ControleInventario inv = new ControleInventario(getBaseContext());
            ControleContagem cont = new ControleContagem(getBaseContext());


            if (c.contains("RESULT_OK")) {
                inv.ExcluirInventario(_DBA_INV_NUM);
                cont.ExcluirContagem(_DBA_INV_NUM);
            }

            onResume();

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        BuscaInventario();
        ListaInventarios();
    }

    public void BuscaInventario() {

        ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String url0 = "http://www.ataneletro.com.br/AtanPed/AppContagem/baixar_inventarios.php";
            String url1 = "http://www.ataneletro.com.br/AtanPed/AppContagem/baixar_filtro_produto.php";
//            String url2 = "http://www.ataneletro.com.br/AtanPed/AppContagem/baixar_contagem_terminal.php";


            parametros = "CHAVE=" + Integer.parseInt(MainActivity.SESSION_CHAVE);
//            parametros2 = "TERMINAL=" + MainActivity.SESSION_CHAVE;


            new ListaInventario.BaixarInventario().execute(url0);
            new ListaInventario.BaixarPermissoes().execute(url1);
//            new ListaInventario.BaixarContagem().execute(url2);

        } else {
            Toast.makeText(getApplicationContext(), "Nenhuma Conexão estabelecida...", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_inventario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_baixar_inventario) {
            BuscaInventario();
        }

        return super.onOptionsItemSelected(item);
    }

    private void ListaInventarios() {

        listaS = (ListView) findViewById(R.id.ListViewInventario);
        final ImageView status = (ImageView) findViewById(R.id.img_sem_dados);

        ControleInventario inventario = new ControleInventario(getBaseContext());
        Cursor dados = inventario.BuscaInventario();

        if (dados.getCount() > 0) {

            AdapterInventario customListAdapter = new AdapterInventario(getBaseContext(), dados);
            listaS.setAdapter(customListAdapter);
            status.setVisibility(View.INVISIBLE);
        } else {
            status.setVisibility(View.VISIBLE);
        }

        listaS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView DBA_INV_NUM = (TextView) view.findViewById(R.id.t_view_inventario_NUM);
                TextView DBA_INV_DATA = (TextView) view.findViewById(R.id.t_view_inventario_DATA);
                TextView DBA_INV_LOJA = (TextView) view.findViewById(R.id.t_view_inventario_LOJA);
                TextView DBA_INV_OBS = (TextView) view.findViewById(R.id.t_view_inventario_OBS);
                TextView DBA_INV_STATUS = (TextView) view.findViewById(R.id.t_view_inventario_STATUS);
                TextView DBA_INV_ENVIADO = (TextView) view.findViewById(R.id.t_view_inventario_ENVIADO);

                _DBA_INV_NUM = Integer.parseInt(DBA_INV_NUM.getText().toString());
                _DBA_INV_DATA = Integer.parseInt(DBA_INV_DATA.getText().toString().replace("/", ""));
                _DBA_INV_LOJA = Integer.parseInt(DBA_INV_LOJA.getText().toString());
                _DBA_INV_OBS = DBA_INV_OBS.getText().toString();
                _DBA_INV_STATUS = DBA_INV_STATUS.getText().toString();
                _DBA_INV_ENVIADO = DBA_INV_ENVIADO.getText().toString();


                ControleProdutos produtos = new ControleProdutos(getBaseContext());

                if (produtos.BuscaProdutos().getCount() > 0) {

                    // Verifica se existe contagem no banco local do inventário
//                    ControleContagem produto = new ControleContagem(getBaseContext());
//                    int qtd = produto.BuscaContagemTerminal(_DBA_INV_NUM, MainActivity.SESSION_CHAVE);
//
//                    // Verifica se existe contagem no banco remoto do inventário
//                    ControleInventario inventario = new ControleInventario(getBaseContext());
//                    int contagem = inventario.BuscaInventarioContagem(_DBA_INV_NUM);

//                Toast.makeText(getBaseContext(), "Inventario: " +qtd, Toast.LENGTH_SHORT).show();
//                    if (qtd == 0 && contagem == 0) {
//                        ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//                        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
//                        if (networkInfo != null && networkInfo.isConnected()) {
//                            String url0 = "http://www.ataneletro.com.br/AtanPed/AppContagem/baixar_contagem_terminal.php";
//
//                            parametros2 = "INVENTARIO=" + _DBA_INV_NUM + "&TERMINAL=" + MainActivity.SESSION_CHAVE;
//
//                            new ListaInventario.BaixarContagem().execute(url0);
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Nenhuma conexão estabelecida... ", Toast.LENGTH_LONG).show();
//                        }
//
//                    } else {

                        if (_DBA_INV_ENVIADO.equals("NAO")) {
                            Intent intent = new Intent(ListaInventario.this, InventarioActivity.class);
                            Bundle param = new Bundle();
                            param.putInt("param_INV_NUM", _DBA_INV_NUM);
                            param.putInt("param_INV_DATA", _DBA_INV_DATA);
                            param.putInt("param_INV_LOJA", _DBA_INV_LOJA);
                            param.putString("param_INV_STATUS", _DBA_INV_STATUS);
                            param.putString("param_INV_OBS", _DBA_INV_OBS);
                            intent.putExtras(param);

                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Operação não permitida.", Toast.LENGTH_SHORT).show();
                        }
                    //}
                } else {
                    Toast.makeText(getApplicationContext(), "Baixe os produtos para continuar.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        listaS.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView DBA_INV_STATUS = (TextView) view.findViewById(R.id.t_view_inventario_STATUS);
                statusLong = DBA_INV_STATUS.getText().toString();
                TextView DBA_INV_ENVIADO = (TextView) view.findViewById(R.id.t_view_inventario_ENVIADO);
                varStatusEnviado = DBA_INV_ENVIADO.getText().toString();
                TextView DBA_NUM_INVENTARIO = (TextView) view.findViewById(R.id.t_view_inventario_NUM);
                InventarioActivity.NUMERO_INV_NUM = Integer.parseInt(DBA_NUM_INVENTARIO.getText().toString());

                return false;
            }
        });
    }

    private class BaixarInventario extends AsyncTask<String, JSONObject, JSONArray> {

        private ProgressDialog load;

        @Override
        protected void onPreExecute() {
            //MOSTRA A MENSAGEM PARA O USUARIO
            load = ProgressDialog.show(ListaInventario.this, "Por favor aguarde!",
                    "Baixando inventário, isso pode demorar um pouco...");
        }

        @Override
        protected JSONArray doInBackground(String... urls) {

            JSONArray jsonArray = null;

            //ARMAZENA NA LISTA O RETORNO
            String listaInventarioJson = ConexaoJson.get(urls[0], parametros);

            try {

                JSONObject obj = new JSONObject(listaInventarioJson);
                jsonArray = obj.getJSONArray("result_sql");

                if (listaInventarioJson.contains("ERRO_SEM_DADOS")) {
                    ControleInventario inv = new ControleInventario(getBaseContext());
                    inv.ExcluirTodosInventario();
                }else {

                    //CONTA OS REGISTROS DA LISTA
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(j);

                        //PASSA COMO PARAMETRO A LINHA ATUAL
                        publishProgress(jsonObject);

                    }
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
            Inventario inventario = new Inventario();

            try {

                String data = c.getString("DBA_INV_DATA");
                data = data.replace("-", "");


                Integer varDBA_INV_NUM = c.getInt("DBA_INV_NUM");
                Integer varDBA_INV_LOJA = c.getInt("DBA_INV_LOJA");
                Integer varDBA_INV_DATA = Integer.parseInt(data);
                String varDBA_INV_STATUS = c.getString("DBA_INV_STATUS");
                String varDBA_ENVIADO = c.getString("DBA_ENVIADO");
                String varDBA_INV_OBS = c.getString("DBA_INV_OBS");

                inventario.setNum_inventario(varDBA_INV_NUM + "");
                inventario.setLoja(varDBA_INV_LOJA + "");
                inventario.setData(varDBA_INV_DATA + "");
                inventario.setStatus(varDBA_INV_STATUS);
                inventario.setEnviado(varDBA_ENVIADO);
                inventario.setObservarcao(varDBA_INV_OBS);


                // Verifica se existe contagem no banco local do inventário
                ControleContagem produto = new ControleContagem(getBaseContext());
                int qtd = produto.BuscaContagemTerminal(varDBA_INV_NUM, MainActivity.SESSION_CHAVE);

                ControleInventario inv = new ControleInventario(getBaseContext());
                inv.ExcluirInventario(varDBA_INV_NUM);

                if(qtd == 0){
                    String url2 = "http://www.ataneletro.com.br/AtanPed/AppContagem/baixar_contagem_terminal.php";
                    parametros2 = "INVENTARIO=" + varDBA_INV_NUM + "&TERMINAL=" + MainActivity.SESSION_CHAVE;
                    new ListaInventario.BaixarContagem().execute(url2);
                }

                //INSTANCIA O CONTROLE PRODUTOS
//                ControleInventario inv = new ControleInventario(getBaseContext());
                //CONTA AS LINHAS DE RETORNO
//                int qtd_registro = inv.VerificaInventarioImportado(varDBA_INV_NUM).getCount();

                /* ************************************* TESTE **************************************************** */
                inv.InserirInventario(
                        varDBA_INV_NUM,
                        varDBA_INV_LOJA,
                        varDBA_INV_DATA,
                        varDBA_INV_STATUS,
                        varDBA_ENVIADO,
                        "",
                        varDBA_INV_OBS
                );

//                if(qtd_registro == 0){
//
//                    //GRAVA OS PRODUTOS NO SQLITE LOCAL
//                    inv.InserirInventario(
//                            varDBA_INV_NUM,
//                            varDBA_INV_LOJA,
//                            varDBA_INV_DATA,
//                            varDBA_INV_STATUS,
//                            varDBA_INV_OBS
//                    );
//                }
//                else{
//                    //ALTERAR OS PRODUTOS NO SQLITE LOCAL
//                    inv.AlterarInventario(
//                            varDBA_INV_NUM,
//                            varDBA_INV_LOJA,
//                            varDBA_INV_DATA,
//                            varDBA_INV_STATUS,
//                            varDBA_INV_OBS
//                    );
//                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPostExecute(JSONArray lista) {
            //RETIRA A MENSAGEM DA TELA
            load.dismiss();

            //SE O RETORNO FOR NULLO
            if (lista == null) {
                Toast.makeText(getApplicationContext(), "Nenhum inventario para baixar...", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(getApplicationContext(), "Os inventarios foram atualizados.", Toast.LENGTH_LONG).show();
            }


            //ATUALIZA O LIST VIEW
            ListaInventarios();

        }
    }

    private class BaixarPermissoes extends AsyncTask<String, JSONObject, JSONArray> {

        private ProgressDialog load;

        @Override
        protected void onPreExecute() {
            //MOSTRA A MENSAGEM PARA O USUARIO
            load = ProgressDialog.show(ListaInventario.this, "Por favor Aguarde!",
                    "Baixando permissões, isso pode demorar um pouco...");
        }

        @Override
        protected JSONArray doInBackground(String... urls) {

            JSONArray jsonArray = null;

            //ARMAZENA NA LISTA O RETORNO
            String listaPermissoesJson = ConexaoJson.get(urls[0], parametros);
            ControlePermissoes permissoes = new ControlePermissoes(getBaseContext());
            permissoes.ExcluirFiltroProduto();


            try {

                JSONObject obj = new JSONObject(listaPermissoesJson);
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

                Integer varDBA_INVENTARIO = c.getInt("DBA_INVENTARIO");
                String varDBA_TIPO = c.getString("DBA_TIPO");
                String varDBA_PARAMETRO = c.getString("DBA_PARAMETRO");
                Integer varDBA_SECAO = c.getInt("DBA_SECAO");

                //INSTANCIA O CONTROLE PRODUTOS
                ControlePermissoes permissoes = new ControlePermissoes(getBaseContext());
                //CONTA AS LINHAS DE RETORNO
//                int  qtd_registro = inv.VerificaInventarioImportado(varDBA_INV_NUM).getCount();

//                if(permissoes.ExcluirInventario() == true){
                //GRAVA OS PRODUTOS NO SQLITE LOCAL
                permissoes.InserirPermissoes(
                        varDBA_INVENTARIO,
                        varDBA_TIPO,
                        varDBA_PARAMETRO,
                        varDBA_SECAO
                );
//                }else{
//                    Toast.makeText(getApplicationContext(), "Erro ao deletar inventários", Toast.LENGTH_SHORT).show();
//                }

//                if(qtd_registro == 0){
//
//                    //GRAVA OS PRODUTOS NO SQLITE LOCAL
//                    inv.InserirInventario(
//                            varDBA_INV_NUM,
//                            varDBA_INV_LOJA,
//                            varDBA_INV_DATA,
//                            varDBA_INV_STATUS,
//                            varDBA_INV_OBS
//                    );
//                }
//                else{
//                    //ALTERAR OS PRODUTOS NO SQLITE LOCAL
//                    inv.AlterarInventario(
//                            varDBA_INV_NUM,
//                            varDBA_INV_LOJA,
//                            varDBA_INV_DATA,
//                            varDBA_INV_STATUS,
//                            varDBA_INV_OBS
//                    );
//                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPostExecute(JSONArray lista) {
            //RETIRA A MENSAGEM DA TELA
            load.dismiss();

            //SE O RETORNO FOR NULLO
            if (lista == null) {
                Toast.makeText(getApplicationContext(), "Nenhum inventario para baixar...", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(getApplicationContext(), "Os inventarios foram atualizados.", Toast.LENGTH_LONG).show();
            }


            //ATUALIZA O LIST VIEW
            ListaInventarios();
        }

    }

    private class BaixarContagem extends AsyncTask<String, JSONObject, JSONArray> {

        private ProgressDialog load;

        @Override
        protected void onPreExecute() {
            //MOSTRA A MENSAGEM PARA O USUARIO
            load = ProgressDialog.show(ListaInventario.this, "Por favor aguarde!",
                    "Baixando contagem do inventário, isso pode demorar um pouco...");
        }

        @Override
        protected JSONArray doInBackground(String... urls) {

            JSONArray jsonArray = null;

            //ARMAZENA NA LISTA O RETORNO
            String listaInventarioJson = ConexaoJson.get(urls[0], parametros2);

            if (listaInventarioJson.contains("ERRO_SEM_DADOS")) {
//                ControleInventario inventario = new ControleInventario(getBaseContext());
//                inventario.AlterarChecagemContagem(_DBA_INV_NUM);
            } else {
                try {

                    JSONObject obj = new JSONObject(listaInventarioJson);
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
            }

            return jsonArray;
        }

        @Override
        protected void onProgressUpdate(JSONObject... objeto) {
            //RECEBE A LISTA
            JSONObject c = objeto[0];

            try {

                Integer varDBA_CONT_INVENTARIO = c.getInt("DBA_CONT_INVENTARIO");
                Integer varDBA_CONT_LOJA = c.getInt("DBA_CONT_LOJA");
                Integer varDBA_CONT_TERMINAL = c.getInt("DBA_CONT_TERMINAL");
                Long varDBA_CONT_PRODUTO = c.getLong("DBA_CONT_PRODUTO");
                Integer varDBA_CONT_QTD = c.getInt("DBA_CONT_QTD");
//                Integer varDBA_CONT_QTD_REC = c.getInt("DBA_CONT_QTD_REC");
//                String varDBA_CONT_REC = c.getString("DBA_CONT_REC");

                //INSTANCIA O CONTROLE PRODUTOS
                ControleContagem contagem = new ControleContagem(getBaseContext());
                //CONTA AS LINHAS DE RETORNO
//                int qtd_registro = inv.VerificaInventarioImportado(varDBA_INV_NUM).getCount();

                contagem.InserirProdutoContagem(varDBA_CONT_INVENTARIO,
                        varDBA_CONT_PRODUTO,
                        varDBA_CONT_QTD,
                        varDBA_CONT_LOJA,
                        varDBA_CONT_TERMINAL);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPostExecute(JSONArray lista) {
            //RETIRA A MENSAGEM DA TELA
            load.dismiss();

//            if (_DBA_INV_ENVIADO.equals("NAO")) {
//                Intent intent = new Intent(ListaInventario.this, InventarioActivity.class);
//                Bundle param = new Bundle();
//                param.putInt("param_INV_NUM", _DBA_INV_NUM);
//                param.putInt("param_INV_DATA", _DBA_INV_DATA);
//                param.putInt("param_INV_LOJA", _DBA_INV_LOJA);
//                param.putString("param_INV_STATUS", _DBA_INV_STATUS);
//                param.putString("param_INV_OBS", _DBA_INV_OBS);
//                intent.putExtras(param);
//
//                startActivity(intent);
//            } else {
//                Toast.makeText(getApplicationContext(), "Operação não permitida.", Toast.LENGTH_SHORT).show();
//            }
        }
    }

    /* Compara se os inventários baixados já existem no banco local - Não tá sendo usado a té a versão 2018.4.21 */
    public boolean ComparaInventarios(Inventario inventario) {
        for (int j = 0; j < listaInventariosLocal.size(); j++) {

            if(inventario.getNum_inventario().equals(listaInventariosLocal.get(j).getNum_inventario())){
                if(listaInventariosLocal.get(j).getContagem().equals("N")){
                    Log.i("COMPARACAO", " é igual mas não pode apagar");
                }else {
                    Log.i("COMPARACAO", " é igual e pode apagar");
                }
                return true;
            }else {
                Log.i("COMPARACAO", " é igual mas não pode apagar");
                return false;
            }
        }
        return false;
    }
}

