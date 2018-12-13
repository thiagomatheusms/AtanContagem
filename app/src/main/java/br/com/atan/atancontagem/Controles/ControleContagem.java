package br.com.atan.atancontagem.Controles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by ODILON on 09/02/2017.
 */

public class ControleContagem {
    private SQLiteDatabase db;
    private BancoDados banco;

    public ControleContagem(Context context){
        banco = new BancoDados(context);
    }


    public static  final String TABELA_A1_CONTAGEM = "PED_CONTAGEM";
    public static  final String DBA_CONT_INVENTARIO = "DBA_CONT_INVENTARIO";
    public static  final String DBA_CONT_PRODUTO = "DBA_CONT_PRODUTO";
    public static  final String DBA_CONT_QTD = "DBA_CONT_QTD";
    public static  final String DBA_CONT_LOJA = "DBA_CONT_LOJA";
    public static  final String DBA_CONT_TERMINAL = "DBA_CONT_TERMINAL";


    public String InserirProdutoContagem(
                                int varINVENTARIO,
                                Long varPRODUTO,
                                int varQTD,
                                int varLOJA,
                                int varTERMINAL
                                  ){

        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BancoDados.DBA_CONT_INVENTARIO, varINVENTARIO);
        valores.put(BancoDados.DBA_CONT_PRODUTO, varPRODUTO);
        valores.put(BancoDados.DBA_CONT_QTD, varQTD);
        valores.put(BancoDados.DBA_CONT_LOJA, varLOJA);
        valores.put(BancoDados.DBA_CONT_TERMINAL, varTERMINAL);



        resultado = db.insert(BancoDados.TABELA_A1_CONTAGEM, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao gravar registro.";
        else
            return "Registro gravado com sucesso.";

    }

    public boolean ExcluirContagem(int numInventario){
        try {
            db = banco.getWritableDatabase();
            db.execSQL("DELETE FROM PED_CONTAGEM WHERE DBA_CONT_INVENTARIO = " + numInventario);
            db.close();
            return true;
        }catch (Exception e){

        }

        return false;
    }

    public Cursor BuscaProdutosContados(long inventario) {
        Cursor c;

        db = banco.getReadableDatabase();
        c = db.rawQuery("SELECT * FROM " +
                "PED_CONTAGEM C, " +
                "PED_PRODUTOS P " +
                "WHERE P.DBA_PROD_CODIGO = C.DBA_CONT_PRODUTO AND C.DBA_CONT_INVENTARIO = "+inventario+" ORDER BY DBA_PROD_DESCRICAO", null);
        if(c != null){
            c.moveToFirst();
        }
        db.close();

        return c;
    }

    public int BuscaContagemTerminal(long inventario, String terminal) {
        Cursor c;
        int retorno = 0;

        db = banco.getReadableDatabase();
        c = db.rawQuery("SELECT COUNT(*) FROM " +
                "PED_CONTAGEM " +
                "WHERE DBA_CONT_INVENTARIO = "+inventario+" AND DBA_CONT_TERMINAL = " + terminal, null);
        if(c.moveToFirst()){
           do{
               retorno = c.getInt(0);
           }while(c.moveToNext());
        }

        db.close();

        return retorno;
    }

    public int BuscaProdutos(Long codProduto) {
        Cursor c;
        int quant=0;

        db = banco.getReadableDatabase();
        c = db.rawQuery("SELECT COUNT(*) FROM " + banco.TABELA_A1_CONTAGEM + " WHERE DBA_CONT_PRODUTO = " + codProduto, null);
        if(c.moveToFirst()){
            do {
               quant = c.getInt(0);
            }while (c.moveToNext());

        }
        Log.d("ESTADO", quant + "");
        db.close();

        return quant;
    }

    public int BuscaQtdProdutos(Long codProduto, int varINVENTARIO) {
        Cursor c;

        int qtd = 0;

        db = banco.getReadableDatabase();
        c = db.rawQuery("SELECT DBA_CONT_QTD FROM " + banco.TABELA_A1_CONTAGEM + " WHERE DBA_CONT_PRODUTO = " + codProduto + " AND DBA_CONT_INVENTARIO = " + varINVENTARIO, null);
        if(c.moveToFirst()){
            do {
                qtd = c.getInt(0);
            }while (c.moveToNext());

        }
        db.close();

        return qtd;
    }


    public void AlterarContagem(int varINVENTARIO,
                                Long varPRODUTO,
                                int varQTD
    ) {

        db = banco.getWritableDatabase();
        db.execSQL("UPDATE " + banco.TABELA_A1_CONTAGEM + " SET  " +
                "DBA_CONT_QTD  = "+varQTD+" " +
                "WHERE DBA_CONT_PRODUTO = "+varPRODUTO+" " +
                "AND DBA_CONT_INVENTARIO =  "+varINVENTARIO+" ");

        db.close();
    }



}
