package br.com.atan.atancontagem.Classes;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ODILON on 09/02/2017.
 */

public class Sincronizar {

    public static String postSinc(Context context, String urlString, String paramString) {

        URL url;
        HttpURLConnection connection = null;

        try {

            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(paramString.getBytes().length));
            connection.setRequestProperty("Content-Language", "pt-BR");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            outputStreamWriter.write(paramString);
            outputStreamWriter.flush();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String linha;
            //List<String> records = new ArrayList<String>();

            while ((linha = bufferedReader.readLine()) != null) {
                stringBuffer.append(linha);
                stringBuffer.append('\n');
            //records.add(linha);
             }

            bufferedReader.close();
            //return records;
            return stringBuffer.toString();


        } catch (IOException erro) {

            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }


    }
}





