package edu.ifrs.aularest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.aularest2311dama.R;

import edu.ifrs.Atividades.Atividade;
import edu.ifrs.rest.AcessoDado;

public class DadoActivity extends Atividade {
    // http://developer.android.com/tools/devices/emulator.html#networkaddresses
    private final static String SERVICE_RAIZ = "http://10.0.2.2:8080/ServicoEnvioMysql/webresources/";
    private final static String SERVICE_DADO = SERVICE_RAIZ + "entidades.dado/idLeitura/";
//    ListView lvDados;
    Integer idLeitura;
    String stLeitura;
    //Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        //this.c = this;
        Bundle bundle = getIntent().getExtras();
        idLeitura = bundle.getInt("idLeitura");
        fazAcessoRest();
        Toast toast = Toast.makeText(getApplicationContext(), "idleitura:" + idLeitura.toString(),
                Toast.LENGTH_LONG);
        toast.show();
    }

    public void fazAcessoRest() {
        if (stLeitura == null) {
            final ConnectivityManager cmgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo netinfo = cmgr.getActiveNetworkInfo();

            if (netinfo != null && netinfo.isConnected()) {
                new AcessoDado(DadoActivity.this).execute(SERVICE_DADO + idLeitura.toString(), stLeitura);
            } else {
                this.setSaida("** Erro de conexao **");
            }
        }
    }

    public void mostraAviso() {
        //final Button botao = (Button) this.findViewById(R.id.btnVai);
        final RelativeLayout aviso = (RelativeLayout) this.findViewById(R.id.avisoWait);
        //botao.setEnabled(false);
        aviso.setVisibility(View.VISIBLE);
    }

    public void escondeAviso() {
        //final Button botao = (Button) this.findViewById(R.id.btnVai);
        final RelativeLayout aviso = (RelativeLayout) this.findViewById(R.id.avisoWait);
        //botao.setEnabled(true);
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
