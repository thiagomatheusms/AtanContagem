package br.com.atan.atancontagem.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import br.com.atan.atancontagem.Controles.BancoDados;
import br.com.atan.atancontagem.R;

/**
 * Created by ODILON on 09/02/2017.
 */

public class AdapterContagem extends CursorAdapter {
    public AdapterContagem(Context context, Cursor lis) {
        super(context, lis, 0);
    }

    @Override
    public View newView(Context context, Cursor c, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.view_contagem, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor c) {

        TextView Layout_DBA_CONT_PRODUTO = (TextView) view.findViewById(R.id.t_view_contagem_codigo_produto);
        TextView Layout_DBA_PROD_EAN13 = (TextView) view.findViewById(R.id.t_view_contagem_COD_EAN13);
        TextView Layout_DBA_PROD_DESCRICAO = (TextView) view.findViewById(R.id.t_view_contagem_nome_produto);
        TextView Layout_DBA_PROD_REFERENCIA = (TextView) view.findViewById(R.id.t_view_contagem_ref_produto);
        //TextView _DBA_PROD_REFERENCIA = (TextView) view.findViewById(R.id.t_view_contagem_QTD_SISTEMA);
        //TextView _DBA_PROD_REFERENCIA = (TextView) view.findViewById(R.id.t_view_contagem_QTD_NEGATIVA);
        TextView Layout_DBA_CONT_QTD = (TextView) view.findViewById(R.id.t_view_contagem_QTD_CONTAGEM_ATUAL);

        String varDBA_CONT_PRODUTO = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_CONT_PRODUTO));
        String varDBA_CONT_QTD = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_CONT_QTD));

        String varDBA_PROD_DESCRICAO = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_PROD_DESCRICAO));
        String varDBA_PROD_REFERENCIA = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_PROD_REFERENCIA));
        String varDBA_PROD_EAN13 = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_PROD_EAN13));

        Layout_DBA_CONT_PRODUTO.setText(varDBA_CONT_PRODUTO);
        Layout_DBA_PROD_EAN13.setText(varDBA_PROD_EAN13);
        Layout_DBA_PROD_DESCRICAO.setText(varDBA_PROD_DESCRICAO);
        Layout_DBA_PROD_REFERENCIA.setText(varDBA_PROD_REFERENCIA);
        Layout_DBA_CONT_QTD.setText(varDBA_CONT_QTD);

    }

}