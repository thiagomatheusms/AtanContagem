package br.com.atan.atancontagem.Controles;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ControlePermissoes {

    private SQLiteDatabase db;
    private BancoDados banco;

    public ControlePermissoes(Context context){
        banco = new BancoDados(context);
    }

    public String InserirPermissoes(
            Integer varDBA_INVENTARIO,
            String varDBA_TIPO,
            String varDBA_PARAMETRO,
            Integer varDBA_SECAO
    ){

        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BancoDados.DBA_INVENTARIO, varDBA_INVENTARIO);
        valores.put(BancoDados.DBA_TIPO, varDBA_TIPO);
        valores.put(BancoDados.DBA_PARAMETRO, varDBA_PARAMETRO);
        valores.put(BancoDados.DBA_SECAO, varDBA_SECAO);


        resultado = db.insert(BancoDados.TABELA_A1_FILTRO_PRODUTO, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao gravar registro.";
        else
            return "Registro gravado com sucesso.";

    }


    public boolean ExcluirFiltroProduto(){
        try {
            db = banco.getWritableDatabase();
            db.execSQL("DELETE FROM PED_FILTRO_PRODUTO");
            db.close();
            return true;
        }catch (Exception e){

        }

        return false;
    }
}
