package edu.ifrs.rest;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import edu.ifrs.Atividades.Atividade;
import edu.ifrs.aularest.LeituraAdapter;
import edu.ifrs.modelo.Leitura;

/**
 * Created by ti on 15/06/16.
 */
public class AcessoLeitura extends AcessoRestTask{
    Context context;
    List leituraList;
    LeituraAdapter leituraAdapter;
    ListView listView;

    public AcessoLeitura(Atividade atividade, List leituraList, LeituraAdapter leituraAdapter, ListView listView) {
        super(atividade);
        this.context = atividade.getApplicationContext();
        this.leituraList = leituraList;
        this.leituraAdapter = leituraAdapter;
        this.listView = listView;
    }

    @Override
    protected void onPostExecute(String result) { // Recebe a saida do doInBackground()
        JSONArray ja;
        JSONObject jo;

        try {
            ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                Integer idLeitura = jo.getInt("idLeitura");
                String dataLeitura = jo.getString("dataLeitura");
                int quantidadeSensores = jo.getInt("quantidadeSensores");
                float intervaloEntreAmostras = (float) jo.getDouble("intervaloEntreAmostras");
                //String descricao=jo.getString("descricao");
                //se não tiver valor no campo, dá exceção
                String descricao = null;
                String ip = jo.getString("ip");
                Leitura lei = new Leitura(idLeitura, dataLeitura, quantidadeSensores, intervaloEntreAmostras, descricao, ip);

                leituraList.add(lei);
            }
            leituraAdapter = new LeituraAdapter(context, leituraList);
            listView.setAdapter(leituraAdapter);
        } catch (JSONException je) {
            Log.e("JSON Parser", "Error parsing data " + je.toString());
        }
        atividade.escondeAviso();
        atividade.setSaida(result);

        //super.onPostExecute(result);
    }
}
