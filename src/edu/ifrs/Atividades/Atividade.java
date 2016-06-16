package edu.ifrs.Atividades;

import android.app.Activity;
import android.content.Context;

/**
 * Created by ti on 15/06/16.
 */
public abstract class Atividade extends Activity {
    public abstract void mostraAviso();
    public abstract void escondeAviso();
    public abstract void setSaida(String texto);
}
