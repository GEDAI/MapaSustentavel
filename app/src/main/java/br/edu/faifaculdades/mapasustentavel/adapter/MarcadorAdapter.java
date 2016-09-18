package br.edu.faifaculdades.mapasustentavel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import br.edu.faifaculdades.mapasustentavel.model.Marcador;

public class MarcadorAdapter extends BaseAdapter {

    private Context context;
    private List<Marcador> marcadores;

    public MarcadorAdapter(Context context, List<Marcador> marcadores) {
        this.context = context;
        this.marcadores = marcadores;
    }

    @Override
    public int getCount() {
        return marcadores.size();
    }

    @Override
    public Object getItem(int position) {
        return marcadores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//
//        final ImagemHelper imagemHelper = new ImagemHelper();
//
//        Marcador marcador = marcadores.get(position);
//
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.fragment_colecao, null);
//
//        TextView txttitulo = (TextView) view.findViewById(R.id.txtTituloLivro);
//        txttitulo.setText(livro.getTitulo());
//
//        ImageView imagemLivro = (ImageView) view.findViewById(R.id.imagemLivro);
//
//        imagemLivro.setImageBitmap(
//                imagemHelper.decodeSampledBitmapFromResource(livro.getPathImagem(), 60, 42));

        return null; //view
    }



}

