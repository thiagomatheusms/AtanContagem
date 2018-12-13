package br.com.atan.atancontagem.Controles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ODILON on 09/02/2017.
 */

public class ControleSessaoLog {
    private SQLiteDatabase db;
    private BancoDados banco;

    public ControleSessaoLog(Context context){
        banco = new BancoDados(context);
    }

    public String InserirSessao(
                                String varTERMINAL,
                                String varCHAVE,
                                String varNOME
                                  ){

        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BancoDados.DBA_SESS_TERMINAL, varTERMINAL);
        valores.put(BancoDados.DBA_SESS_CHAVE, varCHAVE);
        valores.put(BancoDados.DBA_SESS_NOME, varNOME);

        resultado = db.insert(BancoDados.TABELA_A1_SESSAO, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao gravar registro.";
        else
            return "Registro gravado com sucesso.";

    }

    public Cursor BuscaDadosSessao() {
        Cursor c;

        db = banco.getReadableDatabase();
        c = db.rawQuery("SELECT * FROM " + banco.TABELA_A1_SESSAO + " ", null);
        if(c != null){
            c.moveToFirst();
        }
        db.close();

        return c;
    }

    public int VerificaSessao() {
        Cursor c;
        int log = 0;
        db = banco.getReadableDatabase();
        c = db.rawQuery("SELECT COUNT(*) FROM " + banco.TABELA_A1_SESSAO + " ", null);

        if (c.moveToFirst()) {
            do {
                log = c.getInt(0);
            } while (c.moveToNext());
        }
        db.close();

        return log;
    }

    public void ExcluirSessao(){
        db = banco.getWritableDatabase();
        db.execSQL("DELETE FROM PED_SESSAO_LOGIN");
        db.close();
    }

}
