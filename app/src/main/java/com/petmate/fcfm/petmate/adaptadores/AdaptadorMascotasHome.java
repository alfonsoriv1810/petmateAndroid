package com.petmate.fcfm.petmate.adaptadores;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.petmate.fcfm.petmate.R;
import com.petmate.fcfm.petmate.modelos.FCFMMascota;

import java.util.LinkedList;

/**
 * Created by Luis Mares on 05/05/2015.
 */
public class AdaptadorMascotasHome extends BaseAdapter {
    private LinkedList<FCFMMascota>_lista;
    public AdaptadorMascotasHome( ){

    }
    public void set_lista(LinkedList<FCFMMascota>lista){
        _lista=lista;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
       if(_lista!=null){
           return _lista.size();
       }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = View.inflate(parent.getContext(), R.layout.fcfm_item_listado_mascotas,null);
        }
        ImageView imagenMascotaListado = (ImageView) convertView.findViewById(R.id.imagenMascotaListado);
        TextView textViewNombreMascotaListado =(TextView)convertView.findViewById(R.id.textViewNombreMascotaListado);
        ImageLoader.getInstance().displayImage(_lista.get(position).getUrlImagenMascota(),imagenMascotaListado);
        textViewNombreMascotaListado.setText(_lista.get(position).getNombreMascota());
        return convertView;
    }
}
