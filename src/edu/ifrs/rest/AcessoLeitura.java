package edu.ifrs.rest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aularest2311dama.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.ifrs.Atividades.Atividade;
import edu.ifrs.aularest.DadoActivity;
import edu.ifrs.aularest.LeituraAdapter;
import edu.ifrs.modelo.Leitura;

/**
 * Created by ti on 15/06/16.
 */
public class AcessoLeitura extends AcessoRestTask{
    public AcessoLeitura(Atividade atividade){
        super(atividade);
    }
    @Override
    protected void onPostExecute(String result) { // Recebe a saida do doInBackground()
        JSONArray ja;
        JSONObject jo;

        final List<Leitura> leituraList = new ArrayList<Leitura>();;
        LeituraAdapter leituraAdapter;
        ListView listView = (ListView) atividade.findViewById(R.id.Lista);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Integer idLeitura = leituraList.get(position).getIdLeitura();

                Toast toast = Toast.makeText(view.getContext(), idLeitura.toString(),
                        Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(atividade, DadoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idLeitura", idLeitura);
                intent.putExtras(bundle);
                atividade.startActivityForResult(intent, 0);
            }
        });

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
    }
}