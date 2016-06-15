package edu.ifrs.aularest;

import android.view.View;
import android.widget.Toast;

/**
 * Created by ti on 14/06/16.
 */
public class Erro {
    public Erro(View view, String texto){
        Toast toast = Toast.makeText(view.getContext(), texto,
                Toast.LENGTH_LONG);
        toast.show();
    }
}
