package br.com.atan.atancontagem.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import br.com.atan.atancontagem.Classes.MaskData;
import br.com.atan.atancontagem.Controles.BancoDados;
import br.com.atan.atancontagem.R;

/**
 * Created by ODILON on 09/02/2017.
 */

public class AdapterInventario extends CursorAdapter {
    public AdapterInventario(Context context, Cursor lis) {
        super(context, lis, 0);
    }

    @Override
    public View newView(Context context, Cursor c, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.view_inventario, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor c) {

        TextView varDBA_INV_NUM = (TextView) view.findViewById(R.id.t_view_inventario_NUM);
        TextView varDBA_INV_DATA = (TextView) view.findViewById(R.id.t_view_inventario_DATA);
        TextView varDBA_INV_LOJA = (TextView) view.findViewById(R.id.t_view_inventario_LOJA);
        TextView varDBA_INV_OBS = (TextView) view.findViewById(R.id.t_view_inventario_OBS);
        TextView varDBA_INV_STATUS = (TextView) view.findViewById(R.id.t_view_inventario_STATUS);
        TextView varDBA_INV_ENVIADO = (TextView) view.findViewById(R.id.t_view_inventario_ENVIADO);

        Integer DBA_INV_NUM = c.getInt(c.getColumnIndexOrThrow(BancoDados.DBA_INV_NUM));
        String DBA_INV_DATA = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_INV_DATA));
        String DBA_INV_LOJA = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_INV_LOJA));
        String DBA_INV_OBS = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_INV_OBS));
        String DBA_INV_STATUS = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_INV_STATUS));
        String DBA_INV_ENVIADO = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_INV_ENVIADO));

        varDBA_INV_NUM.setText(String.format("%08d", DBA_INV_NUM));
        //varDBA_INV_NUM.setText(DBA_INV_NUM);
        varDBA_INV_DATA.setText(MaskData.DDMMYYYY_barra(DBA_INV_DATA));
        varDBA_INV_LOJA.setText(DBA_INV_LOJA);
        varDBA_INV_OBS.setText(DBA_INV_OBS);
        varDBA_INV_STATUS.setText(DBA_INV_STATUS);
        varDBA_INV_ENVIADO.setText(DBA_INV_ENVIADO);

    }

}