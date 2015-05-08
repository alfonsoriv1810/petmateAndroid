package com.petmate.fcfm.petmate.fragmentos;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.petmate.fcfm.petmate.R;
import com.petmate.fcfm.petmate.constantes.FCFMSingleton;
import com.petmate.fcfm.petmate.modelos.FCFMMascota;

/**
 * Created by alfonso on 06/05/15.
 */
public class FCFMDetalleMascotaFragmento extends Fragment {

    private FCFMMascota mascotaModeloDetalle;

    public FCFMDetalleMascotaFragmento() {
    }

    public FCFMDetalleMascotaFragmento(FCFMMascota mascotaModelo) {
        this.mascotaModeloDetalle = mascotaModelo;
    }

    TextView textViewNombreUsuarioDetalle;
    TextView textViewNombreMascotaDetalle;
    TextView textViewNombreRazaDetalle;
    TextView textViewNombreSexoDetalle;
    ImageView imageViewSexoDetalle;
    ImageView imageViewMascotaImagenDetalle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fcfm_detalle_mascota, container, false);
        textViewNombreUsuarioDetalle = (TextView) view.findViewById(R.id.textViewNombreUsuarioDetalle);
        textViewNombreMascotaDetalle = (TextView) view.findViewById(R.id.textNombreMascotaDetalle);
        textViewNombreRazaDetalle = (TextView) view.findViewById(R.id.textViewRazaMascotaDetalle);
        textViewNombreSexoDetalle = (TextView) view.findViewById(R.id.textViewSexoMascotaDetalle);
        imageViewSexoDetalle = (ImageView) view.findViewById(R.id.imageViewSexoMascotaDetalle);
        imageViewMascotaImagenDetalle = (ImageView) view.findViewById(R.id.imagenMascotaDetalle);

        ImageLoader.getInstance().displayImage(FCFMSingleton.baseURL + mascotaModeloDetalle.getUrlImagenMascota(), imageViewMascotaImagenDetalle);
        textViewNombreUsuarioDetalle.setText(mascotaModeloDetalle.getUsuarioNombre());
        textViewNombreMascotaDetalle.setText(mascotaModeloDetalle.getNombreMascota());
        textViewNombreRazaDetalle.setText(mascotaModeloDetalle.getRazaMascota());

        if (mascotaModeloDetalle.getSexoMascota()) {
            //Macho
            textViewNombreSexoDetalle.setText(R.string.sexoMachoMascotaDetalle);
            imageViewSexoDetalle.setImageResource(R.drawable.icn_sexo_masculino_mascota);
        } else {
            //Hembra
            textViewNombreSexoDetalle.setText(R.string.sexoHembraMascotaDetalle);
            imageViewSexoDetalle.setImageResource(R.drawable.icn_sexo_femenino_mascota);
        }

        return view;
    }
}
