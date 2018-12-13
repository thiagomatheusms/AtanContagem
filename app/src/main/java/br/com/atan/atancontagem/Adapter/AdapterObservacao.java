package br.com.atan.atancontagem.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.atan.atancontagem.Classes.MaskData;
import br.com.atan.atancontagem.Controles.BancoDados;
import br.com.atan.atancontagem.R;

public class AdapterObservacao extends BaseAdapter {

    private Context context;
    private JSONArray jsonArray;

    public AdapterObservacao(Context context, JSONArray lis) {
        this.context = context;
        this.jsonArray = lis;
    }

    public View getView(int posicao, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.adapter_observacao, parent, false);
        //String[] values = lista.get(posicao).split(";");
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(posicao);

            TextView varDBA_OBS_INVENTARIO = (TextView) layout.findViewById(R.id.obs_numInv);
            TextView varDBA_OBS_OBS = (TextView) layout.findViewById(R.id.obs_Obs);
            TextView varDBA_OBS_DATA = (TextView) layout.findViewById(R.id.obs_Data);
            TextView varDBA_OBS_HORA = (TextView) layout.findViewById(R.id.obs_Hora);


            Integer dba_obs_num = jsonObject.getInt("DBA_INVENTARIO");
            varDBA_OBS_INVENTARIO.setText(String.format("%08d",  dba_obs_num));

            String dba_obs = jsonObject.getString("DBA_OBS");
            varDBA_OBS_OBS.setText(dba_obs);


            String dba_obs_data = jsonObject.getString("DBA_DATA");
            dba_obs_data = dba_obs_data.replace("-", "");
            varDBA_OBS_DATA.setText(MaskData.DDMMYYYY_barra(dba_obs_data));

            String dba_obs_hora = jsonObject.getString("DBA_HORA");
            varDBA_OBS_HORA.setText(dba_obs_hora);



//            //varDBA_INV_NUM.setText(DBA_INV_NUM);
//            varDBA_OBS_OBS.setText(DBA_OBS_OBS);
//            varDBA_OBS_DATA.setText(MaskData.DDMMYYYY_barra(DBA_OBS_DATA));
//            varDBA_OBS_HORA.setText(DBA_OBS_HORA);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return layout;

    }


    public int getCount() {
        return jsonArray.length();
    }

    public Object getItem(int posicao) {
        return 0;

    }

    public long getItemId(int posicao) {
        return 0;
    }
}
