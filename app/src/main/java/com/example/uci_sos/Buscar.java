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

import com.example.uci_sos.modelo.Adaptador;
import com.example.uci_sos.modelo.entidad.Hospital;
import com.example.uci_sos.modelo.entidad.Referencias;
import com.example.uci_sos.modelo.entidad.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Muestra la lista de hospitales registrados en la aplicación para poder derivar pacientes a ellos
 *
 * @see Hospital
 * @see Adaptador
 */
public class Buscar extends AppCompatActivity implements Adaptador.OnClickCustom {

    /**
     * RecyclerView con los datos de los hospitales
     *
     * @see Hospital
     * @see Adaptador
     */
    private RecyclerView recyclerView;
    private Adaptador adapter;

    /**
     * OpacityPane que se muestra mientras se cargan los datos
     */
    private View opacityPane;

    /**
     * ProgressBar que se muestra mientras se cargan los datos
     */
    private ProgressBar pb;

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
        new DialogBuscar(this, h).show();
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
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);

        TextView lblRecomendados = findViewById(R.id.lblRecomendados);
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
                listaHospitales = new LinkedList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    listaHospitales.add(snapshot.getValue(Hospital.class));
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
        adapter = new Adaptador(listaHospitales, this);
        recyclerView.setAdapter(adapter);

        opacityPane = findViewById(R.id.opBuscar);
        pb = findViewById(R.id.pbBuscar);

        eliminarHospitalUsuario();
    }

    /**
     * Elimina el hospital en el que trabaja el usuario de la lista de hospitales
     * disponibles para derivar pacientes
     *
     * @see Hospital
     */
    private void eliminarHospitalUsuario() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference userRef = db.getReference(Referencias.USERS);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("CARGAR_USER_BUSCAR", "ÉXITO");
                assert user != null;
                final Usuario usuario = dataSnapshot.child(user.getUid()).getValue(Usuario.class);
                DatabaseReference hospitalRef = db.getReference(Referencias.HOSPITALES);
                hospitalRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("CARGAR_HOSPITAL_BUSCAR", "ÉXITO");
                        assert usuario != null;
                        Hospital h = dataSnapshot.child(String.valueOf(usuario.getCodHospital())).getValue(Hospital.class);
                        assert h != null;
                        listaHospitales.remove(h.getCodHospital());
                        Log.d("ELIMINADO", h.getNombre());
                        filtrarHospital();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("CARGAR_HOSPITAL_BUSCAR", databaseError.toString());
                        showToast("Error al cargar los hospitales\nCompruebe su conexión a Internet e inténtelo de nuevo más tarde");
                        finish();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("CARGAR_USER_BUSCAR", databaseError.toString());
                showToast("Error al cargar los hospitales\nCompruebe su conexión a Internet e inténtelo de nuevo más tarde");
                finish();
            }
        });
    }

    /**
     * Elimina de la lista aquellos hospitales sin camas disponibles y quita
     * la ProgressBar y el OpacityPane
     */
    private void filtrarHospital() {
        for (int i = 0; i < this.listaHospitales.size(); i++) {
            Hospital h = this.listaHospitales.get(i);
            if (h.getCamasPlantaLibres() + h.getCamasUrgenciasLibres() + h.getCamasUciLibres() == 0) {
                this.adapter.remove(i);
            }
        }
        this.recyclerView.setAdapter(this.adapter);
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

    /**
     * Devuelve la lista de hospitales de la base de datos
     *
     * @return List de Hospital
     * @see Hospital
     * @see Buscar#listaHospitales
     */
    public List<Hospital> getListaHospitales() {
        return this.listaHospitales;
    }
}
