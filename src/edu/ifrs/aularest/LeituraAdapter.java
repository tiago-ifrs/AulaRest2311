package edu.ifrs.aularest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aularest2311dama.R;

import java.util.List;

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
        TextView tvId = (TextView) convertView.findViewById(R.id.txtId);
        TextView tvData = (TextView) convertView.findViewById(R.id.txtData);
        TextView tvIp = (TextView) convertView.findViewById(R.id.txtIp);

        tvId.setText(l.getIdLeitura().toString());
        tvData.setText(l.getDataLeitura().toString());
        tvIp.setText(l.getIp());

        return convertView;
    }
}
