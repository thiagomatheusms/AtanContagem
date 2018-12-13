package br.com.atan.atancontagem.Classes;

import java.text.DecimalFormat;

/**
 * Created by ODILON on 05/03/2017.
 */

public class MaskReal {

    public static String FormatMilhar(double x) {
        DecimalFormat df = new DecimalFormat(",##0.00");
        return df.format(x);
    }

    public static String FormatMilhar4D(double x) {
        DecimalFormat df = new DecimalFormat(",##0.0000");
        return df.format(x);
    }

    public static String FormatCentavos(double x) {
        DecimalFormat df = new DecimalFormat("##0.00");
        return df.format(x);
    }

    public static String unFormat(String x) {
        String y = x.replace(".","").replace(",",".");
        return y;
    }

    public static String unFormatx(String x) {
        String y = null;

        if(x.length() <= 6){
            y = x.replace(",",".");
        }
        else if(x.length() > 6 && x.contains(".") && x.contains(",")) {
            y = x.replace(".","").replace(",",".");
        }
        else {
            y = x;
        }

        return y;
    }
}
