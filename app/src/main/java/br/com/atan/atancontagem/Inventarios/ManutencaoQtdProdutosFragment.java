package br.com.atan.atancontagem.Inventarios;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.atan.atancontagem.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ManutencaoQtdProdutosFragment extends Fragment {

    public ManutencaoQtdProdutosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contagem_manut_qtd, container, false);
    }
}
