package edu.ifrs.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

import edu.ifrs.Atividades.Atividade;

public class AcessoRestTask extends AsyncTask<String, Void, String> {
    Context context;
    Atividade atividade;
    public AcessoRestTask(Atividade atividade)    {
        this.atividade = atividade;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        context = atividade.getApplicationContext();
        atividade.mostraAviso();
    }

    @Override
    protected String doInBackground(String... params) {
        final String servico = params[0];
        Log.e("serviço:",servico);

        InputStream is = null;
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
}