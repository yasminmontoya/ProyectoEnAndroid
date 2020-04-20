package com.example.misastreria;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private EditText et_cedula,et_nombre,et_apellido,et_telefono,et_direccion,et_correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        et_cedula=(EditText)findViewById(R.id.cedula);
        et_nombre=(EditText)findViewById(R.id.nombre);
        et_apellido=(EditText)findViewById(R.id.apellido);
        et_telefono=(EditText)findViewById(R.id.telefono);
        et_direccion=(EditText)findViewById(R.id.direccion);
        et_correo=(EditText)findViewById(R.id.correo);
    }

    //Método para guardar los clientes
    public void guardarCliente(View v){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"sastreria", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String cedula = et_cedula.getText().toString();
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String telefono = et_telefono.getText().toString();
        String direccion = et_direccion.getText().toString();
        String correo = et_correo.getText().toString();

        if(!nombre.isEmpty()) {
            ContentValues cliente = new ContentValues();
            cliente.put("cedula", cedula);
            cliente.put("nombre", nombre);
            cliente.put("apellido", apellido);
            cliente.put("telefono", telefono);
            cliente.put("direccion", direccion);
            cliente.put("correo", correo);

            db.insert("clientes", null, cliente);
            db.close();

            Toast.makeText(this, "El cliente se guardo correctamente", Toast.LENGTH_SHORT).show();

            Intent verLista = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(verLista);
        }else{
            Toast.makeText(this, "Primero debe escribir el nombre del cliente", Toast.LENGTH_SHORT).show();
            et_nombre.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(et_nombre,InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void onBackPressed(){
        Intent verLista = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(verLista);
    }
}
