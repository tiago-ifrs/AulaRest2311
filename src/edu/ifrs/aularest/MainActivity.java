package edu.ifrs.aularest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.aularest2311dama.R;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.ifrs.modelo.Leitura;

public class MainActivity extends Activity {

    // http://developer.android.com/tools/devices/emulator.html#networkaddresses
    //private static String SERVICE_RAIZ = "http://10.0.2.2:8080/ServicoEnvioMysql/webresources/";
    private final static String SERVICE_RAIZ = "http://10.0.2.2:8080/ServicoEnvioMysql/webresources/";
    private final static String SERVICE_LEITURA = SERVICE_RAIZ + "entidades.leitura";
    private final static String SERVICE_DADO = SERVICE_RAIZ + "entidades.dado";
    ListView lvLeituras;
    LeituraAdapter leituraAdapter;
    ArrayList<Leitura> leituras;
    String stLeitura;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        lvLeituras = (ListView) findViewById(R.id.Lista);
        this.c = this;

    }

    public void fazAcessoRest(View v) {

        //final EditText campo = (EditText) this.findViewById(R.id.edtNome);
        /*
        if (campo != null) {

            stLeitura = campo.getText().toString();
        }
*/
        if (stLeitura == null) {

            final ConnectivityManager cmgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo netinfo = cmgr.getActiveNetworkInfo();

            if (netinfo != null && netinfo.isConnected()) {

                new AcessoRestTask().execute(SERVICE_LEITURA, stLeitura);

            } else {

                this.setSaida("** Erro de conexao **");
            }
        }
    }

    public void mostraAviso() {

        final Button botao = (Button) this.findViewById(R.id.btnVai);
        botao.setEnabled(false);

        final RelativeLayout aviso = (RelativeLayout) this.findViewById(R.id.avisoWait);
        aviso.setVisibility(View.VISIBLE);
    }

    public void escondeAviso() {

        final Button botao = (Button) this.findViewById(R.id.btnVai);
        botao.setEnabled(true);

        final RelativeLayout aviso = (RelativeLayout) this.findViewById(R.id.avisoWait);
        aviso.setVisibility(View.GONE);
    }

    public void setSaida(String texto) {
        //final EditText campo = (EditText) this.findViewById(R.id.edtNome);
        //final TextView saida = (TextView) this.findViewById(R.id.txtSaida);
        //saida.setText(texto);
        //campo.setText(texto);
    }


    private class AcessoRestTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            MainActivity.this.mostraAviso();
        }

        @Override
        protected String doInBackground(String... params) {

            final String servico = params[0];
            final String nome = params[1];

            InputStream is = null;
            OutputStream os = null;
            String resposta = null;

            // Apenas para dar tempo de visualizar o aviso de aguardar:
            try {
                Thread.sleep(1000);
                if (this.isCancelled()) return "";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                final URL url = new URL(servico);
                final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

//				conn.setReadTimeout(10000 /* milliseconds */);
//		        conn.setConnectTimeout(15000 /* milliseconds */);
//		        conn.setRequestMethod("POST");
                conn.setDoInput(true);
//		        conn.setDoOutput(true);
                conn.setChunkedStreamingMode(0);

                conn.connect();

//		        os = conn.getOutputStream();
//		        this.writeOut(os, "user=" + nome);

                is = conn.getInputStream();
                resposta = this.readIn(is);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException ie) {
                ie.printStackTrace();
            } finally {
                if (is != null)
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            return resposta;
        }

        private void writeOut(OutputStream stream, String postQuery) throws UnsupportedEncodingException {

            final PrintWriter out = new PrintWriter(stream);
            out.print(postQuery);
            out.close();
        }

        private String readIn(InputStream stream) throws IOException, UnsupportedEncodingException {

            final Reader reader = new InputStreamReader(stream, "UTF-8");
            //https://www.learn2crack.com/2013/11/listview-from-json-example.html
            try {
                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "n");
                }
                return sb.toString();
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) { // Recebe a saida do doInBackground()
            JSONArray ja;
            JSONObject jo;
            List<Map<String, String>> l;
            //ArrayAdapter<String> aa = new ArrayAdapter<String>(MainActivity.this,
            //                android.R.layout.simple_expandable_list_item_1);
            List<Leitura> leituraList = new ArrayList<Leitura>();

            try {
                ja = new JSONArray(result);
/*
                ArrayList<String> items = new ArrayList<String>();
                for(int i=0; i < ja.length() ; i++) {
                    jo = ja.getJSONObject(i);
                    int id=jo.getInt("idLeitura");
                    String dataLeitura=jo.getString("dataLeitura");
                    items.add(dataLeitura);
                }
                aa.addAll(items);
                */


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
                    //public Leitura(Integer idLeitura, Date dataLeitura, int quantidadeSensores, float intervaloEntreAmostras, String descricao, String ip) {
                    Leitura lei = new Leitura(idLeitura, dataLeitura, quantidadeSensores, intervaloEntreAmostras, descricao, ip);

                    leituraList.add(lei);
                }

                //setContentView(R.layout.listitems);
                //contextList = this;
                leituraAdapter=new LeituraAdapter(c,leituraList);
                lvLeituras.setAdapter(leituraAdapter);


                //View header = (View)getLayoutInflater().inflate(R.layout.listitems, null);
                //lista.addHeaderView(header);
                //LeituraAdapter la = new LeituraAdapter(getApplicationContext(),leituraList);
                //leituraAdapter.setItems(leituraList);
                //Log.e("JSON Parser", "Error parsing data " + je.toString());

                //leituraAdapter.notifyDataSetChanged();


                //lista.setChoiceMode(1);
                //lista.setAdapter(aa);

                //jaa = new JSONArrayAdapter(MainActivity.this, ja,-1,
                //        new String[]{"dataLeitura", "idLeitura", "ip", "intervaloEntreAmostras", "quantidadeSensores"},
                //        );

            } catch (JSONException je) {
                Log.e("JSON Parser", "Error parsing data " + je.toString());
            }
            MainActivity.this.escondeAviso();

            MainActivity.this.setSaida(result);

            super.onPostExecute(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
