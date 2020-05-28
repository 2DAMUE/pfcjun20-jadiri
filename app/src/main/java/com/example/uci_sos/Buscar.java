package com.example.uci_sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.uci_sos.modelo.Adaptdor;
import com.example.uci_sos.modelo.entidad.Hospital;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Muestra la lista de hospitales registrados en la aplicación para poder derivar pacientes a ellos
 *
 * @see Hospital
 * @see Adaptdor
 */
public class Buscar extends AppCompatActivity {

    /**
     * RecyclerView con los datos de los hospitales
     *
     * @see Hospital
     * @see Adaptdor
     */
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager lm;
    private RecyclerView.Adapter adapter;

    /**
     * TextView con la palabra Recomendados
     */
    private TextView lblRecomendados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        cargarVista();
    }

    /**
     * Carga los elementos de la vista
     */
    private void cargarVista() {
        //Inicializo el RecyclerView
        recyclerView = findViewById(R.id.recyclerViewBuscar);
        //Indico que el número de hospitales puede variar
        recyclerView.setHasFixedSize(false);

        //Inicializo y hago un set del LayoutManager del RecyclerView
        lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);

        //Inicializo el TextView
        lblRecomendados = findViewById(R.id.lblRecomendados);
        //Cambio la tipografía a negrita
        lblRecomendados.setTypeface(lblRecomendados.getTypeface(), Typeface.BOLD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itLogout:
                FirebaseAuth.getInstance().signOut();
                toLogin();
                break;
            case R.id.itConfig:
                toConfig();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Lleva a la ventana de configuración del hospital
     *
     * @see Datos
     */
    private void toConfig() {
        Intent inte = new Intent(this, Datos.class);
        finish();
        startActivity(inte);
    }

    /**
     * Lleva al login
     *
     * @see Login
     */
    private void toLogin() {
        Intent inte = new Intent(this, Login.class);
        inte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(inte);
    }
}
