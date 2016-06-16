package edu.ifrs.rest;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.ifrs.Atividades.Atividade;
import edu.ifrs.aularest.DadoAdapter;
import edu.ifrs.modelo.Dado;

/**
 * Created by ti on 15/06/16.
 */
public class AcessoDado extends AcessoRestTask {
    Context context;
    DadoAdapter dadoAdapter;
    ListView listView;

    public AcessoDado(Atividade atividade, DadoAdapter dadoAdapter, ListView listView) {
        super(atividade);
        this.context = atividade.getApplicationContext();
        this.dadoAdapter = dadoAdapter;
        this.listView = listView;
    }

    @Override
    protected void onPostExecute(String result) { // Recebe a saida do doInBackground()
        JSONArray ja;
        JSONObject jo;

        List<Dado> dadoList = new ArrayList<Dado>();

        try {
            ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                Integer idDado = jo.getInt("idDado");
                String tempo = jo.getString("tempo");
                Integer sensor = jo.getInt("sensor");
                float valor = (float) jo.getDouble("valor");
                String hash = jo.getString("hash");
                //public Dado(Integer idDado, Integer sensor, Float valor, Date tempo, String hash) {
                Dado dado = new Dado(idDado, sensor, valor, tempo, hash);

                dadoList.add(dado);
            }
            dadoAdapter = new DadoAdapter(context, dadoList);
            listView.setAdapter(dadoAdapter);
        } catch (JSONException je) {
            Log.e("JSON Parser", "Error parsing data " + je.toString());
        }
        atividade.escondeAviso();
        atividade.setSaida(result);

        //super.onPostExecute(result);
    }
}
