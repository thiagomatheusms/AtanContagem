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

public class AdapterProdutos extends CursorAdapter {
    public AdapterProdutos(Context context, Cursor lis) {
        super(context, lis, 0);
    }

    @Override
    public View newView(Context context, Cursor c, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.view_produtos, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor c) {

        TextView Layout_DBA_CONT_PRODUTO = (TextView) view.findViewById(R.id.t_view_produtos_codigo_produto);
        TextView Layout_DBA_PROD_EAN13 = (TextView) view.findViewById(R.id.t_view_produtos_COD_EAN13);
        TextView Layout_DBA_PROD_DESCRICAO = (TextView) view.findViewById(R.id.t_view_produtos_nome_produto);
        TextView Layout_DBA_PROD_REFERENCIA = (TextView) view.findViewById(R.id.t_view_produtos_ref_produto);
        //TextView _DBA_PROD_REFERENCIA = (TextView) view.findViewById(R.id.t_view_contagem_QTD_SISTEMA);
        //TextView _DBA_PROD_REFERENCIA = (TextView) view.findViewById(R.id.t_view_contagem_QTD_NEGATIVA);
        TextView Layout_DBA_CONT_QTD = (TextView) view.findViewById(R.id.t_view_produtos_QTD_CONTAGEM_ATUAL);

        Layout_DBA_CONT_QTD.setVisibility(View.INVISIBLE);

        String varDBA_PROD_CODIGO = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_PROD_CODIGO));
//        String varDBA_CONT_QTD = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_CONT_QTD));

        String varDBA_PROD_DESCRICAO = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_PROD_DESCRICAO));
        String varDBA_PROD_REFERENCIA = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_PROD_REFERENCIA));
        String varDBA_PROD_EAN13 = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_PROD_EAN13));

        Layout_DBA_CONT_PRODUTO.setText(varDBA_PROD_CODIGO);
        Layout_DBA_PROD_EAN13.setText(varDBA_PROD_EAN13);
        Layout_DBA_PROD_DESCRICAO.setText(varDBA_PROD_DESCRICAO);
        Layout_DBA_PROD_REFERENCIA.setText(varDBA_PROD_REFERENCIA);
//        Layout_DBA_CONT_QTD.setText(varDBA_CONT_QTD);

    }

}