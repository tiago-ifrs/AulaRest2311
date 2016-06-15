package edu.ifrs.aularest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aularest2311dama.R;

import java.util.List;

import edu.ifrs.modelo.Dado;


public class DadoAdapter extends ArrayAdapter<Dado> {
    //https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
    public DadoAdapter(Context context, List<Dado> items) {
        super(context, 0, items);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Dado dado = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listdado, parent, false);
        }
        //Get the text boxes from the listitem.xml file
        TextView tvId = (TextView) convertView.findViewById(R.id.txtId);
        TextView tvSensor = (TextView) convertView.findViewById(R.id.txtSensor);
        TextView tvValor = (TextView) convertView.findViewById(R.id.txtValor);
        TextView tvData = (TextView) convertView.findViewById(R.id.txtData);

        tvId.setText(dado.getIdDado().toString());
        tvSensor.setText(dado.getSensor().toString());
        tvValor.setText(dado.getValor().toString());
        tvData.setText(dado.getTempo().toString());

        return convertView;
    }
}
