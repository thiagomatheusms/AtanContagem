package br.com.atan.atancontagem.Fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import br.com.atan.atancontagem.Adapter.AdapterDigitacao;
import br.com.atan.atancontagem.Adapter.AdapterProdutos;
import br.com.atan.atancontagem.Controles.ControleProdutos;
import br.com.atan.atancontagem.Inventarios.InventarioActivity;
import br.com.atan.atancontagem.Inventarios.ManutencaoQtdProdutosActivity;
import br.com.atan.atancontagem.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class InventarioTab02Fragment extends Fragment {

    private View rootView;
    ListView listView;
    ImageView sem_dados;
    String varBusca = "";
    Switch btn_tipo_pesquisa;
    SearchView searchView;
    RadioButton rb_desc_ref;
    RadioButton rb_cod_int;

    public InventarioTab02Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_inventario_tab_02, container, false);

        listView = (ListView) rootView.findViewById(R.id.ListViewProdutosTab02);
        sem_dados = (ImageView) rootView.findViewById(R.id.img_sem_dados);
        //EditText var_pesquisaProduto = (EditText) rootView.findViewById(R.id.t_pesquisaProduto);

        searchView = (SearchView) rootView.findViewById(R.id.t_pesquisaProduto);
        btn_tipo_pesquisa = (Switch) rootView.findViewById(R.id.btn_tipo_pesquisa);
        //rb_desc_ref = (RadioButton) rootView.findViewById(R.id.rb_desc_ref);
        //rb_cod_int = (RadioButton) rootView.findViewById(R.id.rb_codigo_int_ean13);

        btn_tipo_pesquisa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(btn_tipo_pesquisa.isChecked() == true){

                    searchView.setInputType(InputType.TYPE_CLASS_NUMBER);

                }
                else if(btn_tipo_pesquisa.isChecked() == false){
                    searchView.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //searchView.setQuery("", false);
                //searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                varBusca = newText.toString().replaceFirst("0*", "");

                if(btn_tipo_pesquisa.isChecked() == true){
                    if(varBusca.length() == 6 || varBusca.length() == 13) {
                        onResume();
                    }else if(varBusca.length() > 6 || varBusca.length() < 13){
                        listView.setAdapter(null);
                    }
                }
                else if(btn_tipo_pesquisa.isChecked() == false){
                    onResume();

                }



                return false;
            }
        });


        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(btn_tipo_pesquisa.isChecked() == true){

            searchView.setInputType(InputType.TYPE_CLASS_NUMBER);

        }
        else if(btn_tipo_pesquisa.isChecked() == false){
            searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        ListaProdutos();
    }

    private void ListaProdutos(){

        ControleProdutos produto = new ControleProdutos(getContext());

        Cursor cursor = produto.BuscaProdutosFiltro(Long.parseLong(InventarioActivity.NUMERO_INV_NUM+""), varBusca);
        if (cursor.getCount() > 0) {
            AdapterDigitacao adaptador = new AdapterDigitacao(getContext(),cursor);
            listView.setAdapter(adaptador);

            sem_dados.setVisibility(rootView.INVISIBLE);
        }
        else {
            listView.setAdapter(null);
            sem_dados.setVisibility(rootView.VISIBLE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchView.setQuery("", false);
//                searchView.clearFocus();

                TextView _varCOD_PRODUTO = (TextView) view.findViewById(R.id.t_view_produtos_codigo_produto);
                TextView _varNOME_PRODUTO = (TextView) view.findViewById(R.id.t_view_produtos_nome_produto);
                TextView _varREF = (TextView) view.findViewById(R.id.t_view_produtos_ref_produto);
                //TextView _varVLR_UN = (TextView) view.findViewById(R.id.t_view_ped_det_tab_02_VLR_UN);
                //TextView _varQTD = (TextView) view.findViewById(R.id.t_view_ped_det_tab_02_QTD);


                String PROD_cod = _varCOD_PRODUTO.getText().toString();
                String PROD_desc = _varNOME_PRODUTO.getText().toString();
                //String PROD_valor = _varVLR_UN.getText().toString();
                String PROD_ref= _varREF.getText().toString();

                Intent intent = new Intent(getContext(), ManutencaoQtdProdutosActivity.class);
                Bundle param = new Bundle();
                param.putLong("paramCodProduto", Long.parseLong(PROD_cod));
                param.putString("paramDescProduto", PROD_desc);
                param.putString("paramRefProduto", PROD_ref);

                intent.putExtras(param);
                startActivity(intent);
            }
        });
    }


}
