package com.example.misastreria;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    private EditText et_cedula,et_nombre,et_apellido,et_telefono,et_direccion,et_correo;
    private String codigo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        codigo= getIntent().getStringExtra("codigo");

        et_cedula=(EditText)findViewById(R.id.cedula2);
        et_nombre=(EditText)findViewById(R.id.nombre2);
        et_apellido=(EditText)findViewById(R.id.apellido2);
        et_telefono=(EditText)findViewById(R.id.telefono2);
        et_direccion=(EditText)findViewById(R.id.direccion2);
        et_correo=(EditText)findViewById(R.id.correo2);


        verClienteSeleccionado();
    }

    //Método para mostrar el cliente
    public void verClienteSeleccionado(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"sastreria", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor fila = db.rawQuery("select * from clientes where codigo='" + codigo + "'", null);

        if(fila.moveToFirst()){
            et_cedula.setText(fila.getString(1));
            et_nombre.setText(fila.getString(2));
            et_apellido.setText(fila.getString(3));
            et_telefono.setText(fila.getString(4));
            et_direccion.setText(fila.getString(5));
            et_correo.setText(fila.getString(6));
            db.close();
        }
    }


    //Método para editar cliente
    public void editarCliente(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"sastreria", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String cedula = et_cedula.getText().toString();
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String telefono = et_telefono.getText().toString();
        String direccion = et_direccion.getText().toString();
        String correo = et_correo.getText().toString();

        ContentValues cliente= new ContentValues();
        cliente.put("cedula",cedula);
        cliente.put("nombre",nombre);
        cliente.put("apellido",apellido);
        cliente.put("telefono",telefono);
        cliente.put("direccion",direccion);
        cliente.put("correo",correo);

        int cantidad = db.update("clientes", cliente,"codigo='"+codigo+"'", null);
        db.close();

        if(cantidad==1){
            Toast.makeText(this,"El cliente se modifico correctamente",Toast.LENGTH_SHORT).show();
            Intent verClientes = new Intent(Main3Activity.this, MainActivity.class);
            startActivity(verClientes);
        }

    }

    //Método para eliminar cliente
    public void eliminarCliente(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"sastreria", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        int cantidad = db.delete("clientes","codigo='"+codigo+"'", null);
        db.close();

        if(cantidad==1){
            Toast.makeText(this,"El cliente se elimino correctamente",Toast.LENGTH_SHORT).show();
            Intent verClientes = new Intent(Main3Activity.this, MainActivity.class);
            startActivity(verClientes);
        }

    }

    //Método para pasar de activity
    public void verPrendas(View v){

        Intent verPrendas = new Intent(this, Main4Activity.class);
        verPrendas.putExtra("codigo", codigo);
        startActivity(verPrendas);
    }

    @Override
    public void onBackPressed(){
        Intent verLista = new Intent(Main3Activity.this, MainActivity.class);
        startActivity(verLista);
    }
}
