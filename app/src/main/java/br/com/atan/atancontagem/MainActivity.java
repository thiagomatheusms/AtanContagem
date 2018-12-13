package br.com.atan.atancontagem;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import br.com.atan.atancontagem.Classes.ConexaoJson;
import br.com.atan.atancontagem.Controles.BancoDados;
import br.com.atan.atancontagem.Controles.ControleContagem;
import br.com.atan.atancontagem.Controles.ControleSessaoLog;
import br.com.atan.atancontagem.Paginas.SobreActivity;
import br.com.atan.atancontagem.Inventarios.ListaInventario;
import br.com.atan.atancontagem.Inventarios.ProdutosActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String VERSAO_APP = BuildConfig.VERSION_NAME;

    private Long num_pedido_remoto;
    public  static String SESSION_CHAVE;
//    public  static String SESSION_TERMINAL;
//    public  static String SESSION_LOJA;
//    public  static String SESSION_INVENTARIO;

//    public static Long NUMERO_PEDIDO_ATUAL;
//    public static String NUMERO_TABELA_PEDIDO = null;
//    public static String PRECO_TABELA_PEDIDO = null;
//    public static String TIPO_PEDIDO = null;
//    public static String STATUS_PEDIDO = null;

    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //notificacao();

        //SESSÃO LOGIN
        ControleSessaoLog SessaoLog = new ControleSessaoLog(getBaseContext());
        Cursor c = SessaoLog.BuscaDadosSessao();

        String chave = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_SESS_CHAVE));
        final String nome = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_SESS_NOME));

        SESSION_CHAVE = chave;

//        SESSION_INVENTARIO = SESSION_CHAVE.substring(0,5);
//        SESSION_TERMINAL = SESSION_CHAVE.substring(6,9);
//        SESSION_LOJA = SESSION_CHAVE.substring(10,14);
        //FIM --

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                TextView Layout_NOME_TERMINAL = (TextView) drawerView.findViewById(R.id.t_NOME_TERMINAL);
                TextView Layout_NUMERO_TERMINAL = (TextView) drawerView.findViewById(R.id.t_NUMERO_TERMINAL);

                //invalidateOptionsMenu();
                Layout_NOME_TERMINAL.setText("ESTOQUISTA:");
                Layout_NUMERO_TERMINAL.setText(nome);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer.closeDrawer(GravityCompat.START);
    }


    public void notificacao(){
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ico)
                        .setContentTitle("Atan Pedidos")
                        .setContentText("Atualização de status.");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, SobreActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(R.drawable.ico, mBuilder.build());
    }

    @Override
    protected void onResume() {

        //SESSÃO LOGIN
        ControleSessaoLog SessaoLog = new ControleSessaoLog(getBaseContext());
        Cursor c = SessaoLog.BuscaDadosSessao();

        SESSION_CHAVE = c.getString(c.getColumnIndexOrThrow(BancoDados.DBA_SESS_TERMINAL));

        //FIM --
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        onResume();
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sobre) {
            Intent i = new Intent(MainActivity.this, SobreActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //menu.removeItem(R.id.nav_proposta);
        //menu.findItem(R.id.nav_proposta).setVisible(false);

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_produtos) {
            Intent i = new Intent(MainActivity.this, ProdutosActivity.class);
            startActivity(i);

        }
        if (id == R.id.nav_inventario) {
            //Intent i = new Intent(MainActivity.this, InventarioActivity.class);
            //startActivity(i);

            Intent i = new Intent(MainActivity.this, ListaInventario.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }


    private void EnviarPedidoRemotoCapa() {
        ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            String url0 = "http://www.ataneletro.com.br/AtanPed/AppControle/devolve_doc_capa.php";

            //new MainActivity.EnviarPedidoCapa().execute(url0);
        }
        else {
            Toast.makeText(getApplicationContext(), "Nenhuma Conexão estabelecida...", Toast.LENGTH_LONG).show();
        }
    }


}
