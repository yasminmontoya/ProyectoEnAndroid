package com.example.misastreria;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.misastreria.entidades.Prenda;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {

    private ListView lvprendas;
    private ArrayList<String> listaprendas;
    private ArrayList<Prenda> prendas;
    private String codigo;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvprendas = (ListView) findViewById(R.id.prendas);
        codigo= getIntent().getStringExtra("codigo");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent crearPrenda = new Intent(Main4Activity.this, Main5Activity.class);
                crearPrenda.putExtra("codigo", codigo);
                startActivity(crearPrenda);
            }
        });


        listarprendas();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaprendas);
        lvprendas.setAdapter(adapter);

        lvprendas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent verPrenda = new Intent(Main4Activity.this, Main6Activity.class);
                verPrenda.putExtra("codigo", prendas.get(position).getCodigo().toString());
                startActivity(verPrenda);
            }
        });
    }

    //Metodo para ver todos los prendas
    public void listarprendas(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"sastreria", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        Prenda prenda=null;
        prendas= new ArrayList<Prenda>();

        Cursor fila = db.rawQuery("select * from prendas where codigo_cliente='" + codigo + "'", null);

        while(fila.moveToNext()){
            Integer codigo = fila.getInt(0);
            Integer codigo_cliente = fila.getInt(1);
            String tipo_prenda = fila.getString(2);
            String fecha_ingreso = fila.getString(3);
            String fecha_salida = fila.getString(4);
            String color = fila.getString(5);
            String marca = fila.getString(6);
            Integer cantidad = fila.getInt(7);

            prenda = new Prenda(codigo, codigo_cliente, tipo_prenda, fecha_ingreso, fecha_salida, color, marca, cantidad);
            prendas.add(prenda);
            db.close();
        }

        obtenerLista();
    }

    private void obtenerLista() {

        listaprendas= new ArrayList<String>();

        for (int i=0; i<prendas.size();i++){
            listaprendas.add(prendas.get(i).getCantidad() + " - " + prendas.get(i).getTipo_prenda());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent verCliente = new Intent(Main4Activity.this, Main3Activity.class);
        verCliente.putExtra("codigo", codigo);
        startActivity(verCliente);
    }

}
