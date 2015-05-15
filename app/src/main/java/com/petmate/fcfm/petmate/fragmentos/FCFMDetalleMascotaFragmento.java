package com.petmate.fcfm.petmate.fragmentos;


import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.petmate.fcfm.petmate.R;
import com.petmate.fcfm.petmate.asynckTasks.ADLoadImageAsyncTask;
import com.petmate.fcfm.petmate.asynckTasks.ADSaveImageAsyncTask;
import com.petmate.fcfm.petmate.constantes.FCFMSingleton;
import com.petmate.fcfm.petmate.modelos.FCFMMascota;
import com.petmate.fcfm.petmate.utilidades.DescargaServicio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by alfonso on 06/05/15.
 */
public class FCFMDetalleMascotaFragmento extends Fragment implements DescargaServicio.onDowloadList {

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
    ImageButton botonAgregaFavoritos;
    ImageButton botonCompartirMascota;
    ImageButton botonUsuarioDetalle;

    DescargaServicio descargaServicio;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fcfm_detalle_mascota, container, false);
        textViewNombreUsuarioDetalle = (TextView) view.findViewById(R.id.textViewNombreUsuarioDetalle);
        textViewNombreMascotaDetalle = (TextView) view.findViewById(R.id.textNombreMascotaDetalle);
        textViewNombreRazaDetalle = (TextView) view.findViewById(R.id.textViewRazaMascotaDetalle);
        textViewNombreSexoDetalle = (TextView) view.findViewById(R.id.textViewSexoMascotaDetalle);
        imageViewSexoDetalle = (ImageView) view.findViewById(R.id.imageViewSexoMascotaDetalle);
        imageViewMascotaImagenDetalle = (ImageView) view.findViewById(R.id.imagenMascotaDetalle);
        botonAgregaFavoritos = (ImageButton) view.findViewById(R.id.botonFavoritosAgregar);
        botonCompartirMascota = (ImageButton) view.findViewById(R.id.botonShareMascota);
        botonUsuarioDetalle = (ImageButton) view.findViewById(R.id.imagenUsuarioDetalleMascota);

        descargaServicio = new DescargaServicio(this);

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

        botonAgregaFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargaServicio.execute(FCFMSingleton.baseURL + "insertar_favoritos.php?usuario_id=" + FCFMSingleton.usuario.getIdUsuario() + "&mascota_id=" + mascotaModeloDetalle.getIdMascota());
                botonAgregaFavoritos.setVisibility(View.GONE);
            }
        });

        botonCompartirMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.compartir1) + mascotaModeloDetalle.getNombreMascota() + getString(R.string.compartir2) + "\n" + FCFMSingleton.baseURL + mascotaModeloDetalle.getUrlImagenMascota());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getString(R.string.textoCompartir)));
            }
        });

        botonUsuarioDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewContact(getActivity(), FCFMSingleton.usuario.getNombre(), "", FCFMSingleton.usuario.getTelefono(),"",FCFMSingleton.usuario.getUsername(),"","",null);
            }
        });

        return view;
    }

    public void addNewContact(Context context, String nombre,String telTrabajo, String telMovil,String empresa, String email, String website, String direccion, Bitmap bitmap2){
        ContentProviderResult[] res;
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = ops.size();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        if(nombre.length() != 0){
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, nombre)
                    .build());
        }
        if(!telTrabajo.equals("")){
            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,telTrabajo)
                    .build());
        }
        if(!telMovil.equals("")){
            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,telMovil)
                    .build());
        }
        if(!empresa.equals("")){
            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY ,empresa)
                    .build());
        }
        if(!email.equals("")){
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
                    .build());
        }
        if(!website.equals("")){
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Website.TYPE, ContactsContract.CommonDataKinds.Website.TYPE_WORK)
                    .withValue(ContactsContract.CommonDataKinds.Website.URL, website)
                    .build());
        }

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE, ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.STREET, direccion)
                .build());

        if (bitmap2 != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO,bitmapdata )
                    .build());
        }
        try {
            res = context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            if (res!=null && res[0]!=null) {
                Toast.makeText(context, R.string.contactoAgregado, Toast.LENGTH_LONG).show();
            }
        } catch (RemoteException e) {
            Log.e("Error 1 al agregar contacto----------->",""+e);
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            Log.e("Error 2 al agregar contacto----------->",""+e);
            e.printStackTrace();
        }
    }

    @Override
    public void estaDescarga(String string) {

    }
    /*
    Esto va en el listener del boton
    Intent cam = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cam, CAMERA_PIC_REQUEST);
    */
}
