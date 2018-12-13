package br.com.atan.atancontagem.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import br.com.atan.atancontagem.Adapter.AdapterContagem;
import br.com.atan.atancontagem.Controles.ControleContagem;
import br.com.atan.atancontagem.Inventarios.InventarioActivity;
import br.com.atan.atancontagem.R;


/**
 * A simple {@link Fragment} subclass.
 */


public class InventarioTab03Fragment extends Fragment {

    private View rootView;

    ListView listView;
    ImageView sem_dados;

    public InventarioTab03Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_inventario_tab_03, container, false);

        listView = (ListView) rootView.findViewById(R.id.ListViewProdutosContadosTab03);
        sem_dados = (ImageView) rootView.findViewById(R.id.img_sem_dados);
        ControleContagem produto = new ControleContagem(getContext());

        Cursor cursor = produto.BuscaProdutosContados(InventarioActivity.NUMERO_INV_NUM);
        if (cursor.getCount() > 0) {
            AdapterContagem adaptador = new AdapterContagem(getContext(),cursor);
            listView.setAdapter(adaptador);

            sem_dados.setVisibility(rootView.INVISIBLE);
        }
        else {
            sem_dados.setVisibility(rootView.VISIBLE);
        }


        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();

        ControleContagem produto = new ControleContagem(getContext());

        Cursor cursor = produto.BuscaProdutosContados(InventarioActivity.NUMERO_INV_NUM);
        if (cursor.getCount() > 0) {
            AdapterContagem adaptador = new AdapterContagem(getContext(),cursor);
            listView.setAdapter(adaptador);

            sem_dados.setVisibility(rootView.INVISIBLE);
        }
        else {
            sem_dados.setVisibility(rootView.VISIBLE);
        }

    }


}
