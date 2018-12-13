package br.com.atan.atancontagem.Inventarios;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import br.com.atan.atancontagem.Controles.ControleContagem;
import br.com.atan.atancontagem.MainActivity;
import br.com.atan.atancontagem.R;

public class ManutencaoQtdProdutosActivity extends AppCompatActivity {

    private Long   CODIGO_PRODUTO;
    private String NOME_PRODUTO;
    private String REF_PRODUTO;

    private Integer QTD_JA_CONTADA = 0;
    private Integer QTD_PARA_SOMAR  = 0;
    private Integer QTD_DIGITADA = 0;

    private TextView Layout_QTD_TOTAL;
    private SearchView Layout_QTD_DIGITADA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contagem_manut_qtd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        Bundle param = intent.getExtras();
        if (param != null) {
            CODIGO_PRODUTO = param.getLong("paramCodProduto");
            NOME_PRODUTO = param.getString("paramDescProduto");
            REF_PRODUTO = param.getString("paramRefProduto");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //IMPORTANDO OS LAYOUT
        TextView Layout_NUM_INVENTARIO   = (TextView) findViewById(R.id.t_fragment_ped_manut_qtd_produtos_NUMERO_PEDIDO);
        TextView Layout_NOME_PRODUTO     = (TextView) findViewById(R.id.t_fragment_ped_manut_qtd_produtos_NOME_PRODUTO);
        TextView Layout_REF_PRODUTO      = (TextView) findViewById(R.id.t_fragment_ped_manut_qtd_produtos_REF_PRODUTO);
        TextView Layout_QTD_JA_CONTADA   = (TextView) findViewById(R.id.t_fragment_ped_manut_qtd_produtos_VLR_CONT_ATUAL);
        Layout_QTD_TOTAL                 = (TextView) findViewById(R.id.t_fragment_ped_manut_qtd_produtos_CONTAGEM_TOTAL_);
        Layout_QTD_DIGITADA              = (SearchView) findViewById(R.id.t_fragment_ped_manut_qtd_produtos_QTD);

        Button Layout_BTN_MAIS = (Button) findViewById(R.id.btn_fragment_ped_manut_qtd_produtos_MAIS);
        Button Layout_BTN_MENOS = (Button) findViewById(R.id.btn_fragment_ped_manut_qtd_produtos_MENOS);
        Button Layout_BTN_GRAVAR = (Button) findViewById(R.id.btn_fragment_ped_manut_qtd_produtos_CONFIRMAR);
        //FIM DAS IMPORTAÇÕES DOS LAYOUT

        Layout_NOME_PRODUTO.setText(NOME_PRODUTO);
        Layout_REF_PRODUTO.setText(REF_PRODUTO);
        Layout_NUM_INVENTARIO.setText(InventarioActivity.NUMERO_INV_NUM.toString());

        Layout_QTD_DIGITADA.setQuery("0",true);
        Layout_QTD_DIGITADA.setFocusable(true);
        Layout_QTD_DIGITADA.setIconifiedByDefault(false);
        Layout_QTD_DIGITADA.requestFocusFromTouch();


        //BUSCANDO A QUANTIDADE JA CONTADA
        ControleContagem contagemA = new ControleContagem(getBaseContext());
        QTD_JA_CONTADA = contagemA.BuscaQtdProdutos(CODIGO_PRODUTO, InventarioActivity.NUMERO_INV_NUM);
        Layout_QTD_JA_CONTADA.setText(QTD_JA_CONTADA.toString());

        //----------------------------------------------------------------------------------------------------



        InputMethodManager imm=(InputMethodManager) getSystemService(ManutencaoQtdProdutosActivity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(Layout_QTD_DIGITADA, InputMethodManager.SHOW_IMPLICIT);

        Layout_QTD_DIGITADA.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //OBTEM A QUANDIDADE DIGITADA
                if(Layout_QTD_DIGITADA.getQuery().toString().equals("") ||
                        Layout_QTD_DIGITADA.getQuery().toString() == null ||
                        Layout_QTD_DIGITADA.getQuery().toString().equals("-")){

                    QTD_DIGITADA = 0;

                }else {
                    QTD_DIGITADA = Integer.parseInt(newText.toString());

                }

                //NOVA QUANDIDADE PARA ATUALIZAÇÃO
                QTD_PARA_SOMAR = (QTD_JA_CONTADA + QTD_DIGITADA);

                if (QTD_PARA_SOMAR >= 0) {
                    Layout_QTD_TOTAL.setText(QTD_PARA_SOMAR.toString());

                }

                return false;
            }
        });


        //-------------------------------------------------------------------------------------------



        Layout_BTN_MAIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //OBTEM A QUANDIDADE DIGITADA
                if(Layout_QTD_DIGITADA.getQuery().toString().equals("") ||
                        Layout_QTD_DIGITADA.getQuery().toString() == null ||
                        Layout_QTD_DIGITADA.getQuery().toString().equals("-")){

                    QTD_DIGITADA = 0;

                }else {
                    QTD_DIGITADA = Integer.parseInt(Layout_QTD_DIGITADA.getQuery().toString());

                }


                //ACRESCENTA MAIS 1
                Integer contagem = (QTD_DIGITADA + 1);

                //NOVA QUANDIDADE PARA ATUALIZAÇÃO
                QTD_PARA_SOMAR = (QTD_JA_CONTADA + contagem);

                if (QTD_PARA_SOMAR >= 0) {
                    Layout_QTD_DIGITADA.setQuery(contagem.toString(),true);
                    Layout_QTD_TOTAL.setText(QTD_PARA_SOMAR.toString());
                }
                else{
                    //SE O RESULTADO FOR NEGATIVO SETA 0
                    QTD_PARA_SOMAR = 0;
                }

                if (QTD_DIGITADA >= 0) {
                    Layout_QTD_DIGITADA.setBackgroundColor(Color.parseColor("#e0e0e0"));


                } else {
                    Layout_QTD_DIGITADA.setBackgroundColor(Color.parseColor("#e57373"));

                }

            }
        });



        Layout_BTN_MENOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //OBTEM A QUANDIDADE DIGITADA
                if(Layout_QTD_DIGITADA.getQuery().toString().equals("") ||
                        Layout_QTD_DIGITADA.getQuery().toString() == null ||
                        Layout_QTD_DIGITADA.getQuery().toString().equals("-")){

                    QTD_DIGITADA = 0;

                }else {
                    QTD_DIGITADA = Integer.parseInt(Layout_QTD_DIGITADA.getQuery().toString());

                }
                //SUBTRAI 1
                Integer contagem = (QTD_DIGITADA - 1);

                //NOVA QUANDIDADE PARA ATUALIZAÇÃO
                QTD_PARA_SOMAR = (QTD_JA_CONTADA + contagem);

                if (QTD_PARA_SOMAR >= 0) {
                    Layout_QTD_DIGITADA.setQuery(contagem.toString(),true);
                    Layout_QTD_TOTAL.setText(QTD_PARA_SOMAR.toString());
                }
                else{
                    //SE O RESULTADO FOR NEGATIVO SETA 0
                    QTD_PARA_SOMAR = 0;
                }


                if (QTD_DIGITADA < 0) {
                    Layout_QTD_DIGITADA.setBackgroundColor(Color.parseColor("#e57373"));

                }
            }
        });


        Layout_BTN_GRAVAR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //OBTEM A QUANDIDADE DIGITADA
                    if(Layout_QTD_DIGITADA.getQuery().toString().equals("") || Layout_QTD_DIGITADA.getQuery().toString() == null){
                        QTD_DIGITADA = 0;

                    }else {
                        QTD_DIGITADA = Integer.parseInt(Layout_QTD_DIGITADA.getQuery().toString());

                    }

                    if (QTD_DIGITADA != 0) {
                        ControleContagem contagem = new ControleContagem(getBaseContext());

                        if (contagem.BuscaProdutos(CODIGO_PRODUTO) > 0) {
                            contagem.AlterarContagem(InventarioActivity.NUMERO_INV_NUM, CODIGO_PRODUTO, QTD_PARA_SOMAR);

                        } else {
                            contagem.InserirProdutoContagem(InventarioActivity.NUMERO_INV_NUM,
                                    CODIGO_PRODUTO,
                                    QTD_PARA_SOMAR,
                                    InventarioActivity.NUMERO_INV_LOJA,
                                    Integer.parseInt(MainActivity.SESSION_CHAVE));
                        }

                        finish();
                    }
                }
            });
    }


}
