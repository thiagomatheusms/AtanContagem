package br.com.atan.atancontagem.Classes;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ODILON on 15/03/2017.
 */

public class LogStatusDocRemoto extends AppCompatActivity {

    private String parametros_status_doc;

    public void GravaStatusDocRemoto(Long doc_remoto, String status) {

        String url1 = "http://www.ataneletro.com.br/AtanPed/AppControle/grava_status_doc.php";

        Date data = new Date();
        SimpleDateFormat for_data = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat for_hora = new SimpleDateFormat("HHmmss");
        String dataEnvio = for_data.format(data);
        String horaEnvio = for_hora.format(data);
        String doc = doc_remoto.toString();

        parametros_status_doc = "DBA_PROPS_NUM=" + doc +
                "&DBA_PROPS_STATUS=" + status +
                "&DBA_PROPS_DATA=" + dataEnvio +
                "&DBA_PROPS_HORA=" + horaEnvio;
        new LogStatusDocRemoto.GravaStatusDoc().execute(url1);

    }

    private class GravaStatusDoc extends AsyncTask<String,Long,String> {

        @Override
        protected String doInBackground(String... urls){
            String ret = null;

            Sincronizar conexao = new Sincronizar();
            ret = conexao.postSinc(getBaseContext(),urls[0], parametros_status_doc);
            return ret;
        }


        @Override
        protected void onPostExecute(String url){
            super.onPostExecute(url);
        }

    }
}
