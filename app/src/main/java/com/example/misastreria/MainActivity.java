package com.example.misastreria;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.misastreria.entidades.Cliente;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvclientes;
    private ArrayList<String> listaclientes;
    private ArrayList<Cliente> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent crearCliente = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(crearCliente);
            }
        });

        lvclientes = (ListView) findViewById(R.id.clientes);

        listarClientes();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaclientes);
        lvclientes.setAdapter(adapter);

        lvclientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent verCliente = new Intent(MainActivity.this, Main3Activity.class);
                verCliente.putExtra("codigo", clientes.get(position).getCodigo().toString());
                startActivity(verCliente);
            }
        });
    }

    //Metodo para ver todos los clientes
    public void listarClientes(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"sastreria", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cliente cliente = null;
        clientes= new ArrayList<Cliente>();

        Cursor fila = db.rawQuery("select * from clientes", null);

        while(fila.moveToNext()){
            Integer codigo = fila.getInt(0);
            String cedula = fila.getString(1);
            String nombre = fila.getString(2);
            String apellido = fila.getString(3);
            String telefono = fila.getString(4);
            String direccion = fila.getString(5);
            String correo = fila.getString(6);

            cliente = new Cliente(codigo, cedula, nombre, apellido, telefono, direccion, correo);
            clientes.add(cliente);
            db.close();
        }
        
        obtenerLista();
    }

    private void obtenerLista() {
        listaclientes= new ArrayList<>();

        for (int i=0; i<clientes.size();i++){
            listaclientes.add(clientes.get(i).getNombre() + " " + clientes.get(i).getApellido() );
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
        Intent salir = new Intent(Intent.ACTION_MAIN);
        salir.addCategory(Intent.CATEGORY_HOME);
        salir.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(salir);
    }
}
