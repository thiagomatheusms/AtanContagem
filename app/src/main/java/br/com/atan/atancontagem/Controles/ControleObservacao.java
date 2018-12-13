package br.com.atan.atancontagem.Controles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ControleObservacao {

    private SQLiteDatabase db;
    private BancoDados banco;

    public ControleObservacao(Context context){
        banco = new BancoDados(context);
    }

    public String InserirObservacao(
            Integer varDBA_OBS_INVENTARIO,
            String varDBA_OBS_OBS,
            Integer varDBA_OBS_DATA,
            String varDBA_OBS_HORA
    ){

        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BancoDados.DBA_OBS_INVENTARIO, varDBA_OBS_INVENTARIO);
        valores.put(BancoDados.DBA_OBS_OBS, varDBA_OBS_OBS);
        valores.put(BancoDados.DBA_OBS_DATA, varDBA_OBS_DATA);
        valores.put(BancoDados.DBA_OBS_HORA, varDBA_OBS_HORA);

        resultado = db.insert(BancoDados.TABELA_A1_OBS, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao gravar registro.";
        else
            return "Registro gravado com sucesso.";

    }

    public boolean ExcluirInventario(){
        try {
            db = banco.getWritableDatabase();
            db.execSQL("DELETE FROM " + BancoDados.TABELA_A1_OBS);
            db.close();
            return true;
        }catch (Exception e){

        }

        return false;
    }

    public Cursor BuscaObservacao(Integer varDBA_OBS_INVENTARIO) {
        Cursor c;

        db = banco.getReadableDatabase();
        c = db.rawQuery("SELECT DISTINCT * FROM " + banco.TABELA_A1_OBS + " WHERE DBA_INVENTARIO = " + varDBA_OBS_INVENTARIO, null);
        if(c != null){
            c.moveToFirst();
        }
        db.close();

        return c;
    }

}
