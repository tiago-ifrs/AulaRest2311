package edu.ifrs.aularest;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.aularest2311dama.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.List;
import java.util.Map;

import edu.ifrs.Atividades.Atividade;
import edu.ifrs.modelo.Dado;
import edu.ifrs.rest.AcessoDado;

public class DadoActivity extends Atividade {

    // http://developer.android.com/tools/devices/emulator.html#networkaddresses
    //private static String SERVICE_RAIZ = "http://10.0.2.2:8080/ServicoEnvioMysql/webresources/";
    private final static String SERVICE_RAIZ = "http://10.0.2.2:8080/ServicoEnvioMysql/webresources/";
    //private final static String SERVICE_LEITURA = SERVICE_RAIZ + "entidades.leitura";
    private final static String SERVICE_DADO = SERVICE_RAIZ + "entidades.dado/idLeitura/";
    ListView lvDados;
    DadoAdapter dadoAdapter;
    ArrayList<Dado> leituras;
    Integer idLeitura;
    String stLeitura;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.c = this;
        lvDados = (ListView) findViewById(R.id.Lista);
        Bundle bundle = getIntent().getExtras();
        idLeitura = bundle.getInt("idLeitura");

        Toast toast = Toast.makeText(getApplicationContext(), "idleitura:"+idLeitura.toString(),
                Toast.LENGTH_LONG);
        toast.show();

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

                new AcessoDado(DadoActivity.this, dadoAdapter, lvDados).execute(SERVICE_DADO+idLeitura.toString(), stLeitura);
/*
public AcessoDado(Atividade atividade, DadoAdapter dadoAdapter, ListView listView) {
        super(atividade);
        this.context = atividade.getApplicationContext();
        this.dadoAdapter = dadoAdapter;
        this.listView = listView;
    }
 */
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
