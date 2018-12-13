package br.com.atan.atancontagem.Controles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ODILON on 09/02/2017.
 */

public class BancoDados  extends SQLiteOpenHelper {
    public static final int VERSAO = 22;
    public static final String NOME_BANCO = "atan_contagem.db";

    //TABELA A1_
    public static  final String TABELA_A1_SESSAO = "PED_SESSAO";
    public static  final String DBA_SESS_TERMINAL = "DBA_SESS_TERMINAL";
    public static  final String DBA_SESS_CHAVE = "DBA_SESS_CHAVE";
    public static  final String DBA_SESS_NOME = "DBA_SESS_NOME";

    //TABELA A1_
    public static  final String TABELA_A1_INVENTARIO = "PED_INVENTARIO";
    public static  final String DBA_INV_NUM = "DBA_INV_NUM";
    public static  final String DBA_INV_LOJA = "DBA_INV_LOJA";
    public static  final String DBA_INV_DATA = "DBA_INV_DATA";
    public static  final String DBA_INV_STATUS = "DBA_INV_STATUS";
    public static  final String DBA_INV_ENVIADO = "DBA_INV_ENVIADO";
    public static  final String DBA_INV_CONTAGEM = "DBA_INV_CONTAGEM";
    public static  final String DBA_INV_OBS = "DBA_INV_OBS";

    //TABELA A1_
    public static  final String TABELA_A1_CONTAGEM = "PED_CONTAGEM";
    public static  final String DBA_CONT_INVENTARIO = "DBA_CONT_INVENTARIO";
    public static  final String DBA_CONT_PRODUTO = "DBA_CONT_PRODUTO";
    public static  final String DBA_CONT_QTD = "DBA_CONT_QTD";
    public static  final String DBA_CONT_LOJA = "DBA_CONT_LOJA";
    public static  final String DBA_CONT_TERMINAL = "DBA_CONT_TERMINAL";

    //TABELA A1_
    public static  final String TABELA_A1_PRODUTOS = "PED_PRODUTOS";
    public static  final String DBA_PROD_CODIGO = "DBA_PROD_CODIGO";
    public static  final String DBA_PROD_DESCRICAO = "DBA_PROD_DESCRICAO";
    public static  final String DBA_PROD_REFERENCIA = "DBA_PROD_REFERENCIA";
    public static  final String DBA_PROD_EAN13 = "DBA_PROD_EAN13";
    public static  final String DBA_PROD_MARCA = "DBA_PROD_MARCA";
    public static  final String DBA_PROD_SECAO = "DBA_PROD_SECAO";
    public static  final String DBA_PROD_GRUPO = "DBA_PROD_GRUPO";
    public static  final String DBA_PROD_SUBGRUPO = "DBA_PROD_SUBGRUPO";

    //TABELA A1_
    public static  final String TABELA_A1_ATIVACAO = "PED_ATIVACAO";
    public static  final String DBA_ATIV_CHAVE = "DBA_ATIV_CHAVE";
    public static  final String DBA_ATIV_ATIVACAO = "DBA_ATIV_ATIVACAO";

    //TABELA A1_
    public static  final String TABELA_A1_FILTRO_PRODUTO = "PED_FILTRO_PRODUTO";
    public static  final String DBA_INVENTARIO = "DBA_INVENTARIO";
    public static  final String DBA_TIPO = "DBA_TIPO";
    public static  final String DBA_PARAMETRO = "DBA_PARAMETRO";
    public static  final String DBA_SECAO = "DBA_SECAO";

    //TABELA A1_
    public static  final String TABELA_A1_OBS = "TAB_OBS";
    public static  final String DBA_OBS_INVENTARIO = "DBA_INVENTARIO";
    public static  final String DBA_OBS_OBS = "DBA_OBS";
    public static  final String DBA_OBS_DATA = "DBA_DATA";
    public static  final String DBA_OBS_HORA = "DBA_HORA";




    public BancoDados(Context context){
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CriaTabTABELA_A1_SESSAO_LOG());
            db.execSQL(CriaTabTABELA_A1_INVENTARIO());
            db.execSQL(CriaTabTABELA_A1_CONTAGEM());
            db.execSQL(CriaTabTABELA_A1_PRODUTOS());
            db.execSQL(CriaTabTABELA_A1_ATIVACAO());
            db.execSQL(CriaTabTABELA_A1_FILTRO_PRODUTO());
            db.execSQL(CriaTabTABELA_A1_OBS());
        }
        catch (Exception e ){
            e.printStackTrace();
        }

    }

    private String CriaTabTABELA_A1_OBS() {

        return "CREATE TABLE TAB_OBS ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DBA_INVENTARIO  INTEGER," +
                "DBA_OBS TEXT, " +
                "DBA_DATA TEXT, " +
                "DBA_HORA TEXT " + ")";

    }

    private String CriaTabTABELA_A1_FILTRO_PRODUTO() {

        return "CREATE TABLE PED_FILTRO_PRODUTO ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DBA_INVENTARIO  INTEGER," +
                "DBA_TIPO  TEXT," +
                "DBA_PARAMETRO  TEXT," +
                "DBA_SECAO INTEGER " + ")";

    }


    private String CriaTabTABELA_A1_SESSAO_LOG() {

        return "CREATE TABLE PED_SESSAO ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DBA_SESS_TERMINAL TEXT ," +
                "DBA_SESS_CHAVE TEXT ," +
                "DBA_SESS_NOME TEXT " + ")";

    }

    private String CriaTabTABELA_A1_INVENTARIO() {

        return "CREATE TABLE PED_INVENTARIO ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DBA_INV_NUM  INTEGER," +
                "DBA_INV_LOJA  INTEGER," +
                "DBA_INV_DATA  TEXT," +
                "DBA_INV_STATUS  TEXT," +
                "DBA_INV_ENVIADO  TEXT," +
                "DBA_INV_CONTAGEM  TEXT," +
                "DBA_INV_OBS  TEXT " + ")";

    }

    private String CriaTabTABELA_A1_CONTAGEM() {

        return "CREATE TABLE PED_CONTAGEM ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DBA_CONT_INVENTARIO  INTEGER," +
                "DBA_CONT_PRODUTO  INTEGER," +
                "DBA_CONT_LOJA  INTEGER," +
                "DBA_CONT_TERMINAL  INTEGER," +
                "DBA_CONT_QTD  INTEGER " + ")";

    }

    private String CriaTabTABELA_A1_PRODUTOS() {

        return "CREATE TABLE PED_PRODUTOS ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DBA_PROD_CODIGO  INTEGER," +
                "DBA_PROD_DESCRICAO  TEXT," +
                "DBA_PROD_REFERENCIA  TEXT," +
                "DBA_PROD_EAN13  INTEGER, " +
                "DBA_PROD_MARCA  TEXT, " +
                "DBA_PROD_SECAO  INTEGER, " +
                "DBA_PROD_GRUPO  INTEGER, " +
                "DBA_PROD_SUBGRUPO  INTEGER " + ")";

    }

    private String CriaTabTABELA_A1_ATIVACAO() {

        return "CREATE TABLE PED_ATIVACAO ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DBA_ATIV_CHAVE TEXT," +
                "DBA_ATIV_ATIVACAO  TEXT" + ")";

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//                db.execSQL("DROP TABLE IF EXISTS " + TABELA_A1_SESSAO);
//        db.execSQL("DROP TABLE IF EXISTS " + TABELA_A1_CONTAGEM);
//        db.execSQL("DROP TABLE IF EXISTS " + TABELA_A1_PRODUTOS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABELA_A1_INVENTARIO);
//        db.execSQL("DROP TABLE IF EXISTS " + TABELA_A1_ATIVACAO);
//        db.execSQL("DROP TABLE IF EXISTS " + TABELA_A1_FILTRO_PRODUTO);

        if (oldVersion == 17) {
//            db.execSQL("ALTER TABLE PED_PEDIDOS_DET ADD COLUMN DBA_PEDD_PERC_DESCONTO REAL ");
//            db.execSQL("ALTER TABLE PED_PEDIDOS_DET ADD COLUMN DBA_PEDD_PERC_ACRESCIMO REAL");
//            db.execSQL("ALTER TABLE PED_PEDIDOS_CAPA ADD COLUMN DBA_PEDC_PERC_DESCONTO REAL");
//            db.execSQL("ALTER TABLE PED_PEDIDOS_CAPA ADD COLUMN DBA_PEDC_PERC_ACRESCIMO REAL");
//
//            db.execSQL("UPDATE PED_PEDIDOS_CAPA SET DBA_PEDC_PERC_DESCONTO = 0," +
//                    "DBA_PEDC_PERC_ACRESCIMO = 0");
//
//            db.execSQL("UPDATE PED_PEDIDOS_DET SET DBA_PEDD_PERC_DESCONTO = 0," +
//                    "DBA_PEDD_PERC_ACRESCIMO = 0");
            //db.execSQL("ALTER TABLE PED_TABELAS ADD COLUMN DBA_TAB_PESO REAL");
//            db.execSQL(CriaTabTABELA_A1_FILTRO_PRODUTO());
        }

        if(newVersion == 19){
            db.execSQL(CriaTabTABELA_A1_OBS());
        }

        if(newVersion == 21){
            db.execSQL("ALTER TABLE PED_SESSAO ADD COLUMN DBA_SESS_NOME TEXT ");
        }

        if(newVersion == 22){
            db.execSQL("ALTER TABLE PED_INVENTARIO ADD COLUMN DBA_INV_CONTAGEM TEXT ");
        }

        //onCreate(db);
    }


}
