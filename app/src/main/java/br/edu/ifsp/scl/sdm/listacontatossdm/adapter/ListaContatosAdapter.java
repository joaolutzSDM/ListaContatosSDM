package br.edu.ifsp.scl.sdm.listacontatossdm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.scl.sdm.listacontatossdm.R;
import br.edu.ifsp.scl.sdm.listacontatossdm.model.Contato;

public class ListaContatosAdapter extends ArrayAdapter<Contato> {

    private LayoutInflater layoutInflater;

    public ListaContatosAdapter(Context context, List<Contato> listaContatos) {
        super(context, R.layout.layout_view_contato_adapter, listaContatos);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holder holder;

        // Se ainda não foi inflada
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_view_contato_adapter, null);

            //cria um novo holder para celula nova
            holder = new Holder();
            holder.nomeContatoTextView = convertView.findViewById(R.id.nomeContatoTextView);
            holder.emailContatoTextView = convertView.findViewById(R.id.emailContatoTextView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Contato contato = getItem(position);
        holder.nomeContatoTextView.setText(contato.getNome());
        holder.emailContatoTextView.setText(contato.getEmail());
        return convertView;
    }

    private class Holder {
        public TextView nomeContatoTextView;
        public TextView emailContatoTextView;
    }
}
