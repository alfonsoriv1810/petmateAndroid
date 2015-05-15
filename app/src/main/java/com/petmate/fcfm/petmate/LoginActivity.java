package com.petmate.fcfm.petmate;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;
import com.petmate.fcfm.petmate.constantes.FCFMSingleton;
import com.petmate.fcfm.petmate.modelos.FCFMUsuario;
import com.petmate.fcfm.petmate.sqllite.AdminSQLiteOpenHelper;
import com.petmate.fcfm.petmate.utilidades.DescargaServicio;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LoginActivity extends ActionBarActivity implements DescargaServicio.onDowloadList {

    Boolean seRegistro = false;
    SharedPreferences preferences;
    LocationManager locationManager;
    LatLng currentLocation;
    String ciudadUsuarioRegistro;
    String estadoUsuarioRegistro;

    @Override
    protected void onResume() {
        super.onResume();
        preferences = this.getSharedPreferences("usuario", Context.MODE_PRIVATE);
        if (preferences != null && preferences.getAll().size() > 0) {
            FCFMSingleton.usuario = new FCFMUsuario(preferences.getString("correoUsuario", ""), preferences.getString("contrasenaUsuario", ""), preferences.getString("nombreUsuario", ""), preferences.getInt("idUsuario", 0), preferences.getString("telefonoUsuario", ""), preferences.getString("estadoUsuario", ""));
            initActividad();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        final Button login = (Button) findViewById(R.id.login);
        final Button registrarte = (Button) findViewById(R.id.registrar);
        final EditText usuario = (EditText) findViewById(R.id.usuario);
        final EditText contrasena = (EditText) findViewById(R.id.contrasena);

        //Servicios de Localizacion
        int playServicesStatus= GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(playServicesStatus== ConnectionResult.SUCCESS){
            currentLocation = getLocacion();
            if(currentLocation == null){
                currentLocation = new LatLng(25.65, -100.29);
            }
        }else{
            //FCFMSingleton.showMessage("No puede usar mapas", this);
        }

        //Vistas para registrar
        final Button botonAceptarRegisto = (Button) findViewById(R.id.aceptarRegistro);
        final Button botonCancelarRegistro = (Button) findViewById(R.id.cancelarRegistro);
        final EditText editTextNombreRegistro = (EditText) findViewById(R.id.editTextNombreUsuarioRegistro);
        final EditText editTextCorreoRegistro = (EditText) findViewById(R.id.editTextCorreoUsuarioRegistro);
        final EditText editTextContraRegistro = (EditText) findViewById(R.id.editTextContrasenaUsuarioRegistro);
        final EditText editTextTelefonoRegistro = (EditText) findViewById(R.id.editTextTelefonoUsuarioRegistro);

        final DescargaServicio descargaServicio = new DescargaServicio(this);

        login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         seRegistro = false;
                                         if (!usuario.getText().toString().equals("") && !contrasena.getText().toString().equals("")) {
                                             FCFMSingleton.muestraPrograsDialogConTexto(v.getContext());
                                             consulta(usuario.getText().toString(), contrasena.getText().toString());
                                         } else {
                                             Toast.makeText(v.getContext(), "" + R.string.loginEditText, Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                 }
        );
        registrarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.GONE);
                registrarte.setVisibility(View.GONE);
                usuario.setVisibility(View.GONE);
                contrasena.setVisibility(View.GONE);

                editTextNombreRegistro.setVisibility(View.VISIBLE);
                editTextCorreoRegistro.setVisibility(View.VISIBLE);
                editTextContraRegistro.setVisibility(View.VISIBLE);
                editTextTelefonoRegistro.setVisibility(View.VISIBLE);
                botonAceptarRegisto.setVisibility(View.VISIBLE);
                botonCancelarRegistro.setVisibility(View.VISIBLE);
            }
        });

        botonCancelarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seRegistro = false;

                login.setVisibility(View.VISIBLE);
                registrarte.setVisibility(View.VISIBLE);
                usuario.setVisibility(View.VISIBLE);
                contrasena.setVisibility(View.VISIBLE);

                editTextNombreRegistro.setVisibility(View.GONE);
                editTextCorreoRegistro.setVisibility(View.GONE);
                editTextContraRegistro.setVisibility(View.GONE);
                editTextTelefonoRegistro.setVisibility(View.GONE);
                botonAceptarRegisto.setVisibility(View.GONE);
                botonCancelarRegistro.setVisibility(View.GONE);
            }
        });

        botonAceptarRegisto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seRegistro = true;

                login.setVisibility(View.VISIBLE);
                registrarte.setVisibility(View.VISIBLE);
                usuario.setVisibility(View.VISIBLE);
                contrasena.setVisibility(View.VISIBLE);

                editTextNombreRegistro.setVisibility(View.GONE);
                editTextCorreoRegistro.setVisibility(View.GONE);
                editTextContraRegistro.setVisibility(View.GONE);
                editTextTelefonoRegistro.setVisibility(View.GONE);
                botonAceptarRegisto.setVisibility(View.GONE);
                botonCancelarRegistro.setVisibility(View.GONE);

                if (!editTextNombreRegistro.getText().toString().equals("") && !editTextCorreoRegistro.getText().toString().equals("") && !editTextContraRegistro.getText().toString().equals("") && !editTextTelefonoRegistro.getText().toString().equals("")) {
                    FCFMSingleton.muestraPrograsDialogConTexto(v.getContext());
                    String nombreUSuario = editTextNombreRegistro.getText().toString();

                    descargaServicio.execute(FCFMSingleton.baseURL + "insertar_usuario.php?nombre=" + editTextNombreRegistro.getText().toString() + "&correo=" + editTextCorreoRegistro.getText().toString() + "&contra=" + editTextContraRegistro.getText().toString() + "&telefono=" + editTextTelefonoRegistro.getText().toString() + "&estado=Monterrey" + "&foto_path=foto");
                } else {
                    Toast.makeText(v.getContext(), "" + R.string.loginEditText, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void consulta(String usuario, String password) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 4);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nombre,contrasena from Usuario ", null);
        if (fila.moveToFirst()) {
            if (usuario.toLowerCase().equals(fila.getString(0).toLowerCase()) && password.toLowerCase().equals(fila.getString(1).toLowerCase())) {
                FCFMSingleton.escondeProgressDialog();
            }
        } else {
            DescargaServicio descargaServicio = new DescargaServicio(this);
            descargaServicio.execute(FCFMSingleton.baseURL + "existe_usuario.php?usuario_correo=" + usuario + "&usuario_contra=" + password);
        }
        bd.close();
    }

    public void alta(FCFMUsuario usuario) {

        //Aqui hacer google maps
        Geocoder geocoder;
        List<Address> addresses;

        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            ciudadUsuarioRegistro = addresses.get(0).getLocality();
            estadoUsuarioRegistro = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            Log.e("Obtuvo de google maps--->>>>>>", "Estado:" + estadoUsuarioRegistro + " Ciudad" + ciudadUsuarioRegistro);

            if (!ciudadUsuarioRegistro.equals("") && !estadoUsuarioRegistro.equals("")){
                FCFMSingleton.usuario.setEstado(ciudadUsuarioRegistro + ", " + estadoUsuarioRegistro);
            } else {
                FCFMSingleton.usuario.setEstado("Monterrey, Nuevo León");
            }
        } catch (IOException e) {
            FCFMSingleton.usuario.setEstado("Monterrey, Nuevo León");
            e.printStackTrace();
        }

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 4);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("correoUsuario", usuario.getUsername());
        registro.put("contrasenaUsuario", usuario.getPassword());
        registro.put("nombreUsuario", usuario.getNombre());
        registro.put("idUsuario", usuario.getIdUsuario());
        registro.put("telefonoUsuario", usuario.getTelefono());
        if (ciudadUsuarioRegistro != null && estadoUsuarioRegistro != null) {
            if (!ciudadUsuarioRegistro.equals("") && !estadoUsuarioRegistro.equals("")) {
                registro.put("estadoUsuario", ciudadUsuarioRegistro + ", " + estadoUsuarioRegistro);
            } else {
                registro.put("estadoUsuario", "Monterrey, Nuevo León");
            }
        } else {
            registro.put("estadoUsuario", "Monterrey, Nuevo León");
        }
        bd.insert("Usuario", null, registro);
        bd.close();
    }

    private LatLng getLocacion() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);

        //Devuelve un string: GPS, WIFI, PASSIVE
        String provider = locationManager.getBestProvider(criteria, true);

        //Detectar si el metodo de rastreo esta activo
        if (provider == null || !locationManager.isProviderEnabled(provider)) {

        } else {
            //Obtenemos la ultima ubicacion conocida por el proveedor seleccionado
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                return latLng;
            }
        }

        return null;
    }

    private void initActividad() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public void estaDescarga(String string) {
        if (string != null && !string.equals("")) {
            try {
                JSONObject usuarioJSON = new JSONObject(string);
                FCFMSingleton.usuario = new FCFMUsuario(usuarioJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (seRegistro) {
                alta(FCFMSingleton.usuario);
            }
            SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
            if (preferences != null && preferences.getAll().size() <= 0) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("correoUsuario", FCFMSingleton.usuario.getUsername());
                editor.putString("contrasenaUsuario", FCFMSingleton.usuario.getPassword());
                editor.putInt("idUsuario", FCFMSingleton.usuario.getIdUsuario());
                editor.putString("nombreUsuario", FCFMSingleton.usuario.getNombre());
                editor.putString("estadoUsuario", FCFMSingleton.usuario.getEstado());
                editor.putString("telefonoUsuario", FCFMSingleton.usuario.getTelefono());
                editor.commit();
            }

            if (FCFMSingleton.usuario != null) {
                FCFMSingleton.escondeProgressDialog();
                initActividad();
            }
        } else {
            Toast.makeText(this, R.string.falloLogin, Toast.LENGTH_SHORT).show();
            FCFMSingleton.escondeProgressDialog();
        }
    }
}
