package br.com.atan.atancontagem.Classes;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ODILON on 05/03/2017.
 */

public class MaskData {

    public static String DDMMYYYY(Date x) {
        SimpleDateFormat dataFomatada = new SimpleDateFormat("yyyyMMdd");
        return dataFomatada.format(x);
    }


    public static String DDMMYYYY_barra(String x) {
//        SimpleDateFormat dataFomatada = new SimpleDateFormat("dd/MM/yyyy");
//        Date data = null;
//        try {
//            data = dataFomatada.parse(x);
//        }catch (ParseException e){
//            e.printStackTrace();
//        }
        String data = "00/00/0000";
        if(x.length() == 8) {
            String dd = x.substring(6, 8);
            String mm = x.substring(4, 6);
            String yy = x.substring(0, 4);
            data = dd + "/" + mm + "/" + yy;
        }

        return data;
    }
}
