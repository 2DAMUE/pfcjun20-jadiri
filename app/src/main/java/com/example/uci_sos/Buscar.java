package com.example.uci_sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uci_sos.modelo.Adaptdor;
import com.example.uci_sos.modelo.entidad.Hospital;
import com.example.uci_sos.modelo.entidad.Referencias;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Muestra la lista de hospitales registrados en la aplicación para poder derivar pacientes a ellos
 *
 * @see Hospital
 * @see Adaptdor
 */
public class Buscar extends AppCompatActivity implements Adaptdor.OnClickCustom {

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

    /**
     * Lista de hospitales regitrados en la base de datos
     *
     * @see Hospital
     */
    private List<Hospital> listaHospitales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        cargarVista();
    }

    @Override
    public void click(int position) {
        Hospital h = listaHospitales.get(position);
        showToast("Paciente derivado al hospital: " + h.getNombre());
        toHospital();
    }

    /**
     * Lleva a la venana de Mi Hospital eliminando el Stack de Activities anteriores
     *
     * @see MiHospital
     */
    private void toHospital() {
        Intent intent = new Intent(this.getApplicationContext(), MiHospital.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
    }

    /**
     * Carga los elementos de la vista
     */
    private void cargarVista() {
        //Inicializo el RecyclerView
        recyclerView = findViewById(R.id.recyclerViewBuscar);
        //Indico que el número de hospitales puede variar
        recyclerView.setHasFixedSize(false);

        getHospitales();

        //Inicializo y hago un set del LayoutManager del RecyclerView
        lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);

        //Inicializo el TextView
        lblRecomendados = findViewById(R.id.lblRecomendados);
        //Cambio la tipografía a negrita
        lblRecomendados.setTypeface(lblRecomendados.getTypeface(), Typeface.BOLD);
    }

    /**
     * Recoge los hospitales de la base de datos para cargarlos
     * en el RecyclerView
     *
     * @see Buscar#recyclerView
     * @see Buscar#cargarAdapter(List)
     * @see Hospital
     */
    private void getHospitales() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference hospital = db.getReference(Referencias.HOSPITALES);
        hospital.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaHospitales = new ArrayList<>();
                Iterator<DataSnapshot> hospitales = dataSnapshot.getChildren().iterator();
                while (hospitales.hasNext()) {
                    listaHospitales.add(hospitales.next().getValue(Hospital.class));
                }
                Log.d("HOSPITALES_BUSCAR", "ÉXITO");
                cargarAdapter(listaHospitales);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("HOSPITALES_BUSCAR", databaseError.toString());
                showToast("Error al cargar los hospitales.\nCompruebe su conexión a Interner e inténtelo de nuevo más tarde");
            }
        });
    }

    /**
     * Muestra un toast dando un mensaje al usuario
     *
     * @param mensaje mensaje a mostrar
     */
    private void showToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    /**
     * Carga los datos de los hospitales en el RecyclerView y quita el OpacityPane y la ProgressBar
     *
     * @param listaHospitales List de Hospital con los hospitales de la base de datos
     * @see Buscar#recyclerView
     * @see Buscar#getHospitales()
     * @see Hospital
     */
    private void cargarAdapter(List<Hospital> listaHospitales) {
        adapter = new Adaptdor(listaHospitales, this);
        recyclerView.setAdapter(adapter);

        View opacityPane = findViewById(R.id.opBuscar);
        ProgressBar pb = findViewById(R.id.pbBuscar);

        opacityPane.setVisibility(View.GONE);
        pb.setVisibility(View.GONE);
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
