package com.petmate.fcfm.petmate;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.petmate.fcfm.petmate.sqllite.AdminSQLiteOpenHelper;


public class MainActivity2 extends ActionBarActivity {
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(preferences!=null&&preferences.getAll().size()>0)
            initActividad();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        Button login= (Button) findViewById(R.id.login);
        Button registrarte= (Button) findViewById(R.id.registrar);
      final  EditText usuario = (EditText)findViewById(R.id.usuario);
        final  EditText contrasena = (EditText)findViewById(R.id.contrasena);

        login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                        consulta(usuario.getText().toString(),contrasena.getText().toString());
                                     }
                                 }
        );
        registrarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
alta(usuario.getText().toString(),contrasena.getText().toString());
            }
        });
    }
    public void consulta(String nombre,String contrasena) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nombre,contrasena from Usuario ", null);
        if (fila.moveToFirst()) {
            if(contrasena.toLowerCase().equals(fila.getString(0).toLowerCase())&&nombre.toLowerCase().equals(fila.getString(1).toLowerCase())){
                SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences.edit();
                editor.putString("Login",nombre);
                editor.putString("contrasena",contrasena);
                editor.commit();
                initActividad();
            }




        } else
            Toast.makeText(this, "La contraseña o el nombre puede ser incorrecto", Toast.LENGTH_SHORT).show();
        bd.close();
    }
    public void alta(String nombre,String password) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("contrasena", password);
        bd.insert("Usuario", null, registro);
        bd.close();
    }
    private void initActividad(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}
