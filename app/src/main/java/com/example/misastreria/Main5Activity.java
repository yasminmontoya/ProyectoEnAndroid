package com.example.misastreria;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Main5Activity extends AppCompatActivity {

    private Spinner spinner_tipop;
    private EditText et_fechai, et_fechas, et_color, et_marca, et_cantidad, et_otro;
    private int dia, mes, año;
    private String codigo="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        codigo= getIntent().getStringExtra("codigo");

        spinner_tipop=(Spinner)findViewById(R.id.tipop);
        et_fechai=(EditText)findViewById(R.id.fechai);
        et_fechas=(EditText)findViewById(R.id.fechas);
        et_color=(EditText)findViewById(R.id.color);
        et_marca=(EditText)findViewById(R.id.marca);
        et_cantidad=(EditText)findViewById(R.id.cantidad);
        et_otro=(EditText)findViewById(R.id.otro);

        final String []tipo_prenda = {"Selecciona el Tipo de Prenda","Blusa", "Pantalon", "Camisa", "Short", "Jean", "Buso", "Falda", "Ropa Interior", "Sudadera","Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, tipo_prenda);
        spinner_tipop.setAdapter(adapter);

        spinner_tipop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tipo_prenda = parent.getItemAtPosition(position).toString();
                if(tipo_prenda=="Otro"){
                    et_otro.setVisibility(View.VISIBLE);
                }else{
                    et_otro.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    //Método para guardar la prenda del cliente
    public void guardarPrendaCliente(View v){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"sastreria", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String tipop = spinner_tipop.getSelectedItem().toString();
        String fechai = et_fechai.getText().toString();
        String fechas = et_fechas.getText().toString();
        String color = et_color.getText().toString();
        String marca = et_marca.getText().toString();
        String cantidad = et_cantidad.getText().toString();
        String otro = et_otro.getText().toString();
        String tipoprenda="";

        if(!tipop.equalsIgnoreCase("Selecciona el Tipo de Prenda")) {
            if(!cantidad.equalsIgnoreCase("")) {
                if (tipop == "Otro") {
                    tipoprenda = otro;
                } else {
                    tipoprenda = tipop;
                }

                ContentValues prenda = new ContentValues();
                prenda.put("codigo_cliente", codigo);
                prenda.put("tipo_prenda", tipoprenda);
                prenda.put("fecha_ingreso", fechai);
                prenda.put("fecha_salida", fechas);
                prenda.put("color", color);
                prenda.put("marca", marca);
                prenda.put("cantidad", cantidad);

                db.insert("prendas", null, prenda);
                db.close();

                Toast.makeText(this, "La prenda del cliente se guardo correctamente", Toast.LENGTH_SHORT).show();

                Intent verLista = new Intent(this, Main4Activity.class);
                verLista.putExtra("codigo", codigo);
                startActivity(verLista);
            }else{
                Toast.makeText(this, "Falta agregar la cantidad", Toast.LENGTH_SHORT).show();
                et_cantidad.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(et_cantidad,InputMethodManager.SHOW_IMPLICIT);
            }
        }else{
            Toast.makeText(this, "Primero debe seleccionar el tipo de prenda", Toast.LENGTH_SHORT).show();
            spinner_tipop.requestFocus();
        }
    }

    @Override
    public void onBackPressed(){
        Intent verLista = new Intent(Main5Activity.this, Main4Activity.class);
        verLista.putExtra("codigo", codigo);
        startActivity(verLista);
    }
}
