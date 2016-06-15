package edu.ifrs.aularest;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aularest2311dama.R;

import edu.ifrs.modelo.Leitura;


public class LeituraAdapter extends ArrayAdapter<Leitura> {
    //https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
    public LeituraAdapter(Context context, List<Leitura> items) {
        super(context, 0, items);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Leitura l = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitems, parent, false);
        }

        //Get the text boxes from the listitem.xml file
        TextView alertText = (TextView) convertView.findViewById(R.id.txtAlertText);
        TextView alertDate = (TextView) convertView.findViewById(R.id.txtAlertDate);

        alertText.setText(l.getIdLeitura().toString());
        alertDate.setText(l.getDataLeitura().toString());

        Log.e("Leitura Adapter", "id " + l.getIdLeitura());
        return convertView;
    }
}
