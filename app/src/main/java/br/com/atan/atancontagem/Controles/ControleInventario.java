package br.com.atan.atancontagem.Controles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.atan.atancontagem.Modelo.Inventario;

/**
 * Created by ODILON on 09/02/2017.
 */

public class ControleInventario {
    private SQLiteDatabase db;
    private BancoDados banco;

    public ControleInventario(Context context) {
        banco = new BancoDados(context);
    }

    public String InserirInventario(
            Integer varDBA_INV_NUM,
            Integer varDBA_INV_LOJA,
            Integer varDBA_INV_DATA,
            String varDBA_INV_STATUS,
            String varDBA_ENVIADO,
            String varDBA_CONTAGEM,
            String varDBA_INV_OBS
    ) {

        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BancoDados.DBA_INV_NUM, varDBA_INV_NUM);
        valores.put(BancoDados.DBA_INV_LOJA, varDBA_INV_LOJA);
        valores.put(BancoDados.DBA_INV_DATA, varDBA_INV_DATA);
        valores.put(BancoDados.DBA_INV_STATUS, varDBA_INV_STATUS);
        valores.put(BancoDados.DBA_INV_ENVIADO, varDBA_ENVIADO);
        valores.put(BancoDados.DBA_INV_CONTAGEM, varDBA_CONTAGEM);
        valores.put(BancoDados.DBA_INV_OBS, varDBA_INV_OBS);

        resultado = db.insert(BancoDados.TABELA_A1_INVENTARIO, null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao gravar registro.";
        else
            return "Registro gravado com sucesso.";

    }

    public boolean ExcluirInventario(int numInventario) {
        try {
            db = banco.getWritableDatabase();
            db.execSQL("DELETE FROM PED_INVENTARIO WHERE DBA_INV_NUM = " + numInventario);
            db.close();
            return true;
        } catch (Exception e) {

        }

        return false;
    }

    public boolean ExcluirTodosInventario() {
        try {
            db = banco.getWritableDatabase();
            db.execSQL("DELETE FROM PED_INVENTARIO");
            db.close();
            return true;
        } catch (Exception e) {

        }

        return false;
    }

    public Cursor BuscaInventario() {
        Cursor c;

        db = banco.getReadableDatabase();
        c = db.rawQuery("SELECT DISTINCT * FROM " + banco.TABELA_A1_INVENTARIO + " ORDER BY _id DESC", null);
        if (c != null) {
            c.moveToFirst();
        }
        db.close();

        return c;
    }


    public int BuscaInventarioContagem(Integer numInventario) {
        Cursor c;
        int retorno = 0;

        db = banco.getReadableDatabase();
        c = db.rawQuery("SELECT * FROM " + banco.TABELA_A1_INVENTARIO + " WHERE DBA_INV_CONTAGEM = 'N' AND DBA_INV_NUM = " + numInventario, null);

        if (c.moveToFirst()) {
            do {
                retorno = c.getInt(0);
            } while (c.moveToNext());
        }

        db.close();

        return retorno;
    }


    public Cursor BuscaInventarioFiltro(String busca) {
        Cursor c;

        db = banco.getReadableDatabase();
        c = db.rawQuery("SELECT * FROM " + banco.TABELA_A1_INVENTARIO + " " +
                "WHERE (DBA_PROD_CODIGO LIKE '%" + busca + "%' OR " +
                "DBA_PROD_EAN13 LIKE '%" + busca + "%' OR " +
                "DBA_PROD_DESCRICAO LIKE '%" + busca + "%' OR " +
                "DBA_PROD_REFERENCIA LIKE '%" + busca + "%')", null);
        if (c != null) {
            c.moveToFirst();
        }
        db.close();

        return c;
    }

    public Cursor VerificaInventarioImportado(Integer varDBA_INV_NUM) {
        Cursor c;

        db = banco.getReadableDatabase();
        c = db.rawQuery("SELECT * FROM " + banco.TABELA_A1_INVENTARIO + " WHERE DBA_INV_NUM = '" + varDBA_INV_NUM + "' ", null);
        if (c != null) {
            c.moveToFirst();
        }
        db.close();

        return c;
    }

    public void AlterarInventario(Integer varDBA_INV_NUM,
                                  Integer varDBA_INV_LOJA,
                                  String varDBA_INV_DATA,
                                  String varDBA_INV_STATUS,
                                  String varDBA_INV_OBS
    ) {
        db = banco.getWritableDatabase();
        db.execSQL("UPDATE " + banco.TABELA_A1_INVENTARIO + " SET  " +
                //"DBA_INV_NUM  ='"+varDBA_INV_NUM+"'," +
                "DBA_INV_LOJA ='" + varDBA_INV_LOJA + "', " +
                "DBA_INV_DATA ='" + varDBA_INV_DATA + "', " +
                "DBA_INV_STATUS  ='" + varDBA_INV_STATUS + "', " +
                "DBA_INV_OBS  ='" + varDBA_INV_OBS + "'  " +
                "WHERE DBA_INV_NUM = '" + varDBA_INV_NUM + "' ");

        db.close();
    }

    public void AlterarChecagemContagem(int varDBA_INV_NUM) {

        db = banco.getWritableDatabase();
        db.execSQL("UPDATE " + banco.TABELA_A1_INVENTARIO + " SET  " +
                "DBA_INV_CONTAGEM  ='N' " +
                "WHERE DBA_INV_NUM = '" + varDBA_INV_NUM + "' ");

        db.close();
    }

    public void AlterarStatusEnviado(Long varDBA_INV_NUM, String varSTATUS) {

        db = banco.getWritableDatabase();
        db.execSQL("UPDATE " + banco.TABELA_A1_INVENTARIO + " SET  " +
                "DBA_INV_ENVIADO  ='" + varSTATUS + "' " +
                "WHERE DBA_INV_NUM = '" + varDBA_INV_NUM + "' ");

        db.close();
    }


    public List<Inventario> Buscar() {
        List<Inventario> listaEnderecos = new ArrayList<>();

        String sql = "SELECT * FROM PED_INVENTARIO";
        db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Inventario inventario = new Inventario();

            inventario.setContagem(cursor.getString(cursor.getColumnIndex("DBA_INV_NUM")));
            inventario.setData(cursor.getString(cursor.getColumnIndex("DBA_INV_DATA")));
            inventario.setEnviado(cursor.getString(cursor.getColumnIndex("DBA_INV_ENVIADO")));
            inventario.setLoja(cursor.getString(cursor.getColumnIndex("DBA_INV_LOJA")));
            inventario.setNum_inventario(cursor.getString(cursor.getColumnIndex("DBA_INV_NUM")));
            inventario.setObservarcao(cursor.getString(cursor.getColumnIndex("DBA_INV_OBS")));
            inventario.setStatus(cursor.getString(cursor.getColumnIndex("DBA_INV_STATUS")));
            inventario.setContagem(cursor.getString(cursor.getColumnIndex("DBA_INV_CONTAGEM")));

            listaEnderecos.add(inventario);
        }

        return listaEnderecos;
    }


}
