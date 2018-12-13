package br.com.atan.atancontagem.Inventarios;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.atan.atancontagem.Controles.ControleInventario;
import br.com.atan.atancontagem.Fragment.InventarioTab01Fragment;
import br.com.atan.atancontagem.Fragment.InventarioTab02Fragment;
import br.com.atan.atancontagem.Fragment.InventarioTab03Fragment;
import br.com.atan.atancontagem.R;


public class InventarioActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    public static Integer NUMERO_INV_NUM;
    public static Integer NUMERO_INV_LOJA;
    public static Integer NUMERO_INV_DATA;
    public static String NUMERO_INV_STATUS;
    public static String NUMERO_INV_OBS;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private int[] images = {
            R.drawable.icon_tab_pedido_capa,
            R.drawable.icon_tab_produtos,
            R.drawable.icon_tab_desc_acresc,};


    @Override
    public void onResume(){
        super.onResume();
        //CheckConfirmacao();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventarios);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        Bundle param = intent.getExtras();
        if (param != null) {
            NUMERO_INV_NUM = param.getInt("param_INV_NUM");
            NUMERO_INV_LOJA = param.getInt("param_INV_LOJA");
            NUMERO_INV_DATA = param.getInt("param_INV_DATA");
            NUMERO_INV_STATUS = param.getString("param_INV_STATUS");
            NUMERO_INV_OBS = param.getString("param_INV_OBS");
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();

        mViewPager.setCurrentItem(0);


    }

    private void setupTabIcons(){
        tabLayout.getTabAt(0).setIcon(images[0]);
        tabLayout.getTabAt(1).setIcon(images[1]);
        tabLayout.getTabAt(2).setIcon(images[2]);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventarios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        if(NUMERO_INV_STATUS.equals("ABERTO")) {
//
//            if (id == R.id.action_finalizar_inventario) {
//
//
//                new AlertDialog.Builder(InventarioActivity.this)
//                        .setTitle("Atenção!")
//                        .setMessage("Deseja realmente FINALIZAR o inventário?")
//                        .setPositiveButton(R.string.alert_dialog_sim, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                ControleInventario alterar_status = new ControleInventario(getBaseContext());
//                                alterar_status.AlterarStatusInventario(Long.parseLong(NUMERO_INV_NUM.toString()),"FECHADO");
//
//                                finish();
//                            }
//                        })
//                        .setNegativeButton(R.string.alert_dialog_nao, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                //não exclui, apenas fecha a mensagem
//                                dialog.dismiss();
//                            }
//                        })
//                        .show();
//
//                return true;
//            }
//
//
//        }
//        else {
//            Toast.makeText(getBaseContext(), "Status não permite alteração!", Toast.LENGTH_LONG).show();
//        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position){
                case 0:
                    InventarioTab01Fragment f = new InventarioTab01Fragment();
                    return f;
                case 1:
                    InventarioTab02Fragment f1 = new InventarioTab02Fragment();
                    return f1;

                case 2:
                    InventarioTab03Fragment f2 = new InventarioTab03Fragment();
                    return f2;
                    //PlaceholderFragment.newInstance(position + 1);
                default:

                    return null;
            }


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "PEDIDO";
                case 1:
                    return "DIGITAÇÃO";
                case 2:
                    return "CONTAGEM";
            }
            return null;
        }
    }
}
