package br.com.atan.atancontagem.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.atan.atancontagem.Classes.MaskData;
import br.com.atan.atancontagem.R;

import static br.com.atan.atancontagem.Inventarios.InventarioActivity.NUMERO_INV_DATA;
import static br.com.atan.atancontagem.Inventarios.InventarioActivity.NUMERO_INV_LOJA;
import static br.com.atan.atancontagem.Inventarios.InventarioActivity.NUMERO_INV_NUM;
import static br.com.atan.atancontagem.Inventarios.InventarioActivity.NUMERO_INV_OBS;
import static br.com.atan.atancontagem.Inventarios.InventarioActivity.NUMERO_INV_STATUS;

/**
 * A simple {@link Fragment} subclass.
 */


public class InventarioTab01Fragment extends Fragment {

    private View rootView;
    private Button btn_tabela;
    String varOBS;

    private boolean CHK_TIPO = false;

    public InventarioTab01Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_inventario_tab_01, container, false);

        TextView DBA_INV_NUM = (TextView) rootView.findViewById(R.id.t_tab01_inv_NUM);
        TextView DBA_INV_DATA = (TextView) rootView.findViewById(R.id.t_tab01_inv_DATA);
        TextView DBA_INV_LOJA = (TextView) rootView.findViewById(R.id.t_tab01_inv_LOJA);
        TextView DBA_INV_OBS = (TextView) rootView.findViewById(R.id.t_tab01_inv_OBS);
        TextView DBA_INV_STATUS = (TextView) rootView.findViewById(R.id.t_tab01_inv_STATUS);

        DBA_INV_NUM.setText(String.format("%08d",NUMERO_INV_NUM));
        //DBA_INV_NUM.setText(NUMERO_INV_NUM.toString());
        DBA_INV_DATA.setText(MaskData.DDMMYYYY_barra(NUMERO_INV_DATA.toString()));
        DBA_INV_LOJA.setText(NUMERO_INV_LOJA.toString());
        DBA_INV_OBS.setText(NUMERO_INV_OBS.toString());
        DBA_INV_STATUS.setText(NUMERO_INV_STATUS.toString());



        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        ListarCheck();
    }

    private void ListarCheck() {



    }
}
