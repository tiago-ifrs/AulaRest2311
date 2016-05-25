package edu.ifrs.aularest;

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

import com.example.aularest2311dama.R;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	// http://developer.android.com/tools/devices/emulator.html#networkaddresses
	private static String SERVICO = "http://10.0.2.2:8080/AulaRest2311servico/rest/teste/nome"; 
	
	private String nome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
	}
	
	public void fazAcessoRest(View v) {
		
		final EditText campo = (EditText) this.findViewById(R.id.edtNome);
		if(campo != null) {
			
			this.nome = campo.getText().toString();
		}
		
		if(this.nome != null) {
			
			final ConnectivityManager cmgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
			final NetworkInfo netinfo = cmgr.getActiveNetworkInfo();
			
			if(netinfo != null && netinfo.isConnected()) {
				
				new AcessoRestTask().execute(SERVICO, this.nome);
				
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
		
		final TextView saida = (TextView) this.findViewById(R.id.txtSaida);
		saida.setText(texto);
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
				if(this.isCancelled()) return "";
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				final URL url = new URL(servico);
				final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//				conn.setReadTimeout(10000 /* milliseconds */);
//		        conn.setConnectTimeout(15000 /* milliseconds */);
//		        conn.setRequestMethod("POST");
//		        conn.setDoInput(true);
		        conn.setDoOutput(true);
		        conn.setChunkedStreamingMode(0);

		        conn.connect();
		        
		        os = conn.getOutputStream();
		        this.writeOut(os, "user=" + nome);
		        
		        is = conn.getInputStream();
		        resposta = this.readIn(is, 200);
		        
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			} finally {
				if(is != null)
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
		
		private String readIn(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
			
		    final Reader reader = new InputStreamReader(stream, "UTF-8");        
		    char[] buffer = new char[len];
		    reader.read(buffer);
		    
		    return new String(buffer);
		}
		
		@Override
		protected void onPostExecute(String result) { // Recebe a saida do doInBackground()
			
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
