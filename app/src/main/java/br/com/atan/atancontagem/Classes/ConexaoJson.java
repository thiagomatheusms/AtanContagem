package br.com.atan.atancontagem.Classes;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ODILON on 26/04/2017.
 */

public class ConexaoJson {

    public static String get(String urlString, String paramString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resposta = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(paramString.getBytes().length));
            urlConnection.setRequestProperty("Content-Language", "pt-BR");

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream(),"UTF-8");
            outputStreamWriter.write(paramString);
            outputStreamWriter.flush();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));

            String line = "";
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }
            resposta = buffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            try {
                reader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return resposta;
    }

}
