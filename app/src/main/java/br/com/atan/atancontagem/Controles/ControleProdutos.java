package br.com.atan.atancontagem.Controles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ODILON on 09/02/2017.
 */

public class ControleProdutos {
    private SQLiteDatabase db;
    private BancoDados banco;

    public ControleProdutos(Context context){
        banco = new BancoDados(context);
    }

    public String InserirProduto(
            Integer varDBA_PROD_CODIGO,
            String varDBA_PROD_DESCRICAO,
            String varDBA_PROD_REFERENCIA,
            Long varDBA_PROD_EAN13,
            String varDBA_PROD_MARCA,
            Integer varDBA_PROD_SECAO,
            Integer varDBA_PROD_GRUPO,
            Integer varDBA_PROD_SUBGRUPO
    ){

        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BancoDados.DBA_PROD_CODIGO, varDBA_PROD_CODIGO);
        valores.put(BancoDados.DBA_PROD_DESCRICAO, varDBA_PROD_DESCRICAO);
        valores.put(BancoDados.DBA_PROD_REFERENCIA, varDBA_PROD_REFERENCIA);
        valores.put(BancoDados.DBA_PROD_EAN13, varDBA_PROD_EAN13);
        valores.put(BancoDados.DBA_PROD_MARCA, varDBA_PROD_MARCA);
        valores.put(BancoDados.DBA_PROD_SECAO, varDBA_PROD_SECAO);
        valores.put(BancoDados.DBA_PROD_GRUPO, varDBA_PROD_GRUPO);
        valores.put(BancoDados.DBA_PROD_SUBGRUPO, varDBA_PROD_SUBGRUPO);


        resultado = db.insert(BancoDados.TABELA_A1_PRODUTOS, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao gravar registro.";
        else
            return "Registro gravado com sucesso.";

    }

    public boolean ExcluirProdutos(){
        try {
            db = banco.getWritableDatabase();
            db.execSQL("DELETE FROM PED_PRODUTOS");
            db.close();
            return true;
        }catch (Exception e){

        }

        return false;
    }

    public Cursor BuscaProdutos() {
        Cursor c;

        db = banco.getReadableDatabase();
        c = db.rawQuery("SELECT * FROM " + banco.TABELA_A1_PRODUTOS + " ORDER BY DBA_PROD_DESCRICAO ", null);
        if(c != null){
            c.moveToFirst();
        }
        db.close();

        return c;
    }

//    public Cursor BuscaProdutosFiltro(Long inventario, String busca) {
//        Cursor c;
//
//        db = banco.getReadableDatabase();
//        c = db.rawQuery("SELECT * FROM " +
//                "PED_PRODUTOS P " +
//                "WHERE " +
//                "P.DBA_PROD_CODIGO LIKE '%"+busca+"%' OR " +
//                "P.DBA_PROD_EAN13 LIKE '%"+busca+"%' OR " +
//                "P.DBA_PROD_DESCRICAO LIKE '%"+busca+"%' OR " +
//                "P.DBA_PROD_REFERENCIA LIKE '%"+busca+"%' ", null);
//        if(c != null){
//            c.moveToFirst();
//        }
//        db.close();
//
//        return c;
//    }

    public Cursor BuscaProdutosFiltro(Long inventario, String busca) {
        Cursor c;

        db = banco.getReadableDatabase();
        c = db.rawQuery("SELECT * FROM " +
                "(SELECT * FROM PED_PRODUTOS P WHERE " +
                "(DBA_PROD_CODIGO LIKE '%"+ busca +"%'  OR " +
                "DBA_PROD_EAN13 LIKE '%"+ busca +"%' OR " +
                "DBA_PROD_DESCRICAO LIKE '%"+ busca +"%' OR " +
                "DBA_PROD_REFERENCIA LIKE '%"+ busca +"%')) " +
                "WHERE " +
                "               DBA_PROD_MARCA IN (SELECT DBA_PARAMETRO FROM PED_FILTRO_PRODUTO WHERE DBA_INVENTARIO = "+inventario+" AND DBA_TIPO = 'MARCA') OR \n" +
                "               DBA_PROD_CODIGO IN (SELECT DBA_PARAMETRO FROM PED_FILTRO_PRODUTO WHERE DBA_INVENTARIO = "+inventario+" AND DBA_TIPO = 'CODIGO') OR \n" +
                "               DBA_PROD_SECAO IN (SELECT DBA_SECAO FROM PED_FILTRO_PRODUTO WHERE DBA_INVENTARIO = "+inventario+" AND DBA_TIPO = 'SECAO')\n" +
                " ORDER BY DBA_PROD_DESCRICAO ", null);
        if(c != null){
            c.moveToFirst();
        }
        db.close();
        return c;
    }

    public void AlterarProdutos(Integer varDBA_PROD_CODIGO,
                               String varDBA_PROD_DESCRICAO,
                               String varDBA_PROD_REFERENCIA,
                               Long varDBA_PROD_EAN13
    ) {
        db = banco.getWritableDatabase();
        db.execSQL("UPDATE " + banco.TABELA_A1_PRODUTOS + " SET  " +
                //"DBA_PROD_CODIGO  ='"+varDBA_PROD_CODIGO+"'," +
                "DBA_PROD_DESCRICAO ='"+varDBA_PROD_DESCRICAO+"', " +
                "DBA_PROD_REFERENCIA ='"+varDBA_PROD_REFERENCIA+"', " +
                "DBA_PROD_EAN13  ='"+varDBA_PROD_EAN13+"' " +
                "WHERE DBA_PROD_CODIGO = '"+varDBA_PROD_CODIGO+"' ");

        db.close();
    }



}
