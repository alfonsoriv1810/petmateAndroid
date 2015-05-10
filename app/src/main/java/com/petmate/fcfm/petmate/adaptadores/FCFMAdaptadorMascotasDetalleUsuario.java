package com.petmate.fcfm.petmate.adaptadores;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.petmate.fcfm.petmate.R;
import com.petmate.fcfm.petmate.constantes.FCFMSingleton;
import com.petmate.fcfm.petmate.modelos.FCFMMascota;

import java.util.LinkedList;

/**
 * Created by alfonso on 06/05/15.
 */
public class FCFMAdaptadorMascotasDetalleUsuario extends BaseAdapter {
    private LinkedList<FCFMMascota> _lista;

    public FCFMAdaptadorMascotasDetalleUsuario( ) {

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
            convertView = View.inflate(parent.getContext(), R.layout.fcfm_item_reducido_listado_mascotas,null);
        }
        ImageView imagenMascotaListado = (ImageView) convertView.findViewById(R.id.imagenMascotaListadoReducido);
        ImageView imagenSexoMascota = (ImageView) convertView.findViewById(R.id.imagenSexoMascotaListadoReducido);
        TextView textViewNombreMascotaListado = (TextView)convertView.findViewById(R.id.textViewNombreMascotaListadoReducido);
        TextView textViewNombreRazaMascotaReducido = (TextView)convertView.findViewById(R.id.textViewNombreRazaListadoReducido);
        TextView textViewSexoMascotaReducido =  (TextView) convertView.findViewById(R.id.textViewSexoListadoReducido);

        ImageLoader.getInstance().displayImage(FCFMSingleton.baseURL + _lista.get(position).getUrlImagenMascota(), imagenMascotaListado);
        textViewNombreMascotaListado.setText(_lista.get(position).getNombreMascota());
        textViewNombreRazaMascotaReducido.setText(_lista.get(position).getRazaMascota());

        Boolean sexoMascotaUsuario = _lista.get(position).getSexoMascota();

        if (sexoMascotaUsuario == true){
            textViewSexoMascotaReducido.setText("Masculino");
            imagenSexoMascota.setImageResource(R.drawable.icn_sexo_masculino_mascota);
        } else {
            textViewSexoMascotaReducido.setText("Femenino");
            imagenSexoMascota.setImageResource(R.drawable.icn_sexo_femenino_mascota);
        }
        convertView.setTag(_lista.get(position));
        return convertView;
    }
}
