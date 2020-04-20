package com.example.misastreria;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Main6Activity extends AppCompatActivity {

    private Spinner spinner_tipop;
    private EditText et_fechai, et_fechas, et_color, et_marca, et_cantidad;
    private ArrayList<String> listatipoprendas;
    private String codigo="";
    private String codigo_cliente;
    String tipo_prenda="";
    private int dia, mes, año;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        listatipoprendas= new ArrayList<>();
        codigo= getIntent().getStringExtra("codigo");

        spinner_tipop=(Spinner)findViewById(R.id.tipop2);
        et_fechai=(EditText)findViewById(R.id.fechai2);
        et_fechas=(EditText)findViewById(R.id.fechas2);
        et_color=(EditText)findViewById(R.id.color2);
        et_marca=(EditText)findViewById(R.id.marca2);
        et_cantidad=(EditText)findViewById(R.id.cantidad2);

        verPrendaSeleccionada();

        final String []lista_tipo_prenda = {"Blusa", "Pantalon", "Camisa", "Short", "Jean", "Buso", "Falda", "Ropa Interior", "Sudadera","Otro"};
        listatipoprendas.add(tipo_prenda);
        for(int i=0;i<lista_tipo_prenda.length;i++){
            String tipo=lista_tipo_prenda[i];
            if(!tipo.equalsIgnoreCase(tipo_prenda)){
                listatipoprendas.add(tipo);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, listatipoprendas);
        spinner_tipop.setAdapter(adapter);
    }

    //Método para obtener la fecha de ingreso de la prenda
    public void obtenerFechaIngreso(View v) {
        final Calendar calendario = Calendar.getInstance();
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        año = calendario.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                et_fechai.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        },año,mes,dia);
        datePickerDialog.show();
    }

    //Método para obtener la fecha de salida de la prenda
    public void obtenerFechaSalida(View v) {
        final Calendar calendario = Calendar.getInstance();
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        año = calendario.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                et_fechas.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        },año,mes,dia);
        datePickerDialog.show();
    }

    //Método para mostrar la prenda seleccionada
    public void verPrendaSeleccionada(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"sastreria", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor fila = db.rawQuery("select * from prendas where codigo='" + codigo + "'", null);

        if(fila.moveToFirst()){
            codigo_cliente= fila.getString(1);
            tipo_prenda = fila.getString(2);
            et_fechai.setText(fila.getString(3));
            et_fechas.setText(fila.getString(4));
            et_color.setText(fila.getString(5));
            et_marca.setText(fila.getString(6));
            et_cantidad.setText(fila.getString(7));
            db.close();
        }


    }

    //Método para editar prenda
    public void editarPrenda(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"sastreria", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String tipop = spinner_tipop.getSelectedItem().toString();
        String fechai = et_fechai.getText().toString();
        String fechas = et_fechas.getText().toString();
        String color = et_color.getText().toString();
        String marca = et_marca.getText().toString();
        String cantidad = et_cantidad.getText().toString();

        ContentValues prenda= new ContentValues();
        prenda.put("codigo_cliente",codigo_cliente);
        prenda.put("tipo_prenda",tipop);
        prenda.put("fecha_ingreso",fechai);
        prenda.put("fecha_salida",fechas);
        prenda.put("color",color);
        prenda.put("marca",marca);
        prenda.put("cantidad",cantidad);

        int resultado = db.update("prendas", prenda,"codigo='" + codigo + "'", null);
        db.close();

        if(resultado == 1){
            Toast.makeText(this,"La prenda se modifico correctamente",Toast.LENGTH_SHORT).show();
            Intent verPrendas = new Intent(Main6Activity.this, Main4Activity.class);
            verPrendas.putExtra("codigo", codigo_cliente );
            startActivity(verPrendas);
        }

    }

    //Método para eliminar prenda
    public void eliminarPrenda(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"sastreria", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        int resultado = db.delete("prendas","codigo='" + codigo + "'", null);
        db.close();

        if(resultado == 1){
            Toast.makeText(this,"La prenda se elimino correctamente",Toast.LENGTH_SHORT).show();
            Intent verPrendas = new Intent(Main6Activity.this, Main4Activity.class);
            verPrendas.putExtra("codigo", codigo_cliente );
            startActivity(verPrendas);
        }

    }

    @Override
    public void onBackPressed(){
        Intent verLista = new Intent(Main6Activity.this, Main4Activity.class);
        verLista.putExtra("codigo", codigo_cliente);
        startActivity(verLista);
    }
}
