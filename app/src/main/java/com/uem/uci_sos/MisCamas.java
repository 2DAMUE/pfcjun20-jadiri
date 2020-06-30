package com.uem.uci_sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.uem.uci_sos.modelo.entidad.Referencias;
import com.uem.uci_sos.modelo.entidad.Usuario;
import com.google.firebase.auth.FirebaseAuth;

import com.uem.uci_sos.modelo.entidad.Hospital;

import com.uem.uci_sos.modelo.entidad.Camas;
import com.uem.uci_sos.modelo.entidad.UCI;
import com.uem.uci_sos.modelo.entidad.Planta;
import com.uem.uci_sos.modelo.entidad.Urgencias;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Muestra de forma visual el estado actual de las camas del hospital
 *
 * @see Hospital
 * @see Camas
 * @see UCI
 * @see Planta
 * @see Urgencias
 */
public class MisCamas extends AppCompatActivity implements View.OnClickListener {

    /**
     * Botón que lleva a la ventana de Reservar
     *
     * @see Reservar
     */
    private LinearLayout reservar;
    /**
     * Botón que lleva a la ventana de Mi Hospital
     *
     * @see MiHospital
     */
    private LinearLayout mihospital;

    /**
     * Elemento raiz en el que se añaden las distintas camas de UCI del hospital
     *
     * @see Camas
     * @see UCI
     * @see Hospital
     */
    private LinearLayout rootUCI;
    /**
     * Elemento raiz en el que se añaden las distintas camas de Urgencias del hospital
     *
     * @see Camas
     * @see Urgencias
     * @see Hospital
     */
    private LinearLayout rootUrgencias;
    /**
     * Elemento raiz en el que se añaden las distintas camas de Planta del hospital
     *
     * @see Camas
     * @see Planta
     * @see Hospital
     */
    private LinearLayout rootPlanta;

    /**
     * OpacityPane que se muestra para indicar al usuario que la ventana se está cargando
     */
    private View opacityPane;

    /**
     * ProgressBar que se muestra para indicar al usuario que la ventana se está cargando
     */
    private ProgressBar proggresBar;

    /**
     * TextView con el texto CAMAS UCI
     */
    private TextView lblUCI;
    /**
     * TextView con el texto CAMAS URGENCIAS
     */
    private TextView lblUrgencias;
    /**
     * TextView con el texto CAMAS PLANTA
     */
    private TextView lblPlanta;

    /**
     * SwipeRefreshLayout que actualiza la ventana
     *
     * @see MisCamas
     */
    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Hospital en el que trabaja el usuario
     *
     * @see Hospital
     */
    private Hospital h;

    /**
     * Cama seleccionada por el usuario
     *
     * @see Camas
     */
    private Camas camaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarVista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnReservarCamas:
                toReservar();
                break;
            case R.id.btnHospitalCamas:
                toHospital();
                break;
        }
    }

    /**
     * Carga los listeners de los elementos de la ventana
     */
    private void cargarListeners() {
        reservar.setOnClickListener(this);
        mihospital.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarVista();
            }
        });
    }

    /**
     * Carga los elementos de la vista y pide el hospital a la base de datos
     */
    public void cargarVista() {
        setContentView(R.layout.activity_mis_camas);

        reservar = findViewById(R.id.btnReservarCamas);
        mihospital = findViewById(R.id.btnHospitalCamas);

        rootUCI = findViewById(R.id.rootUCI);
        rootUrgencias = findViewById(R.id.rootUrgencias);
        rootPlanta = findViewById(R.id.rootPlanta);

        opacityPane = findViewById(R.id.opacityPaneMisCamas);

        proggresBar = findViewById(R.id.pbMisCamas);

        lblPlanta = findViewById(R.id.lblCamasPlanta);
        lblUCI = findViewById(R.id.lblCamasUci);
        lblUrgencias = findViewById(R.id.lblCamasUrgencias);

        swipeRefreshLayout = findViewById(R.id.swipeMisCamas);

        cargarListeners();

        getHospital();
    }

    /**
     * Intenta recoger el hospital en el que trabaja el usuario para cargar sus datos en la vista
     * Si lo consiguie carga los datos en la vista. Si no, informa al usuario mediante un AlertDialog
     *
     * @see Hospital
     */
    private void getHospital() {
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference userReference = db.getReference(Referencias.USERS);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                assert currentUser != null;
                final Usuario usuario = dataSnapshot.child(currentUser.getUid()).getValue(Usuario.class);
                DatabaseReference hospitales = db.getReference(Referencias.HOSPITALES);
                hospitales.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        assert usuario != null;
                        h = dataSnapshot.child(String.valueOf(usuario.getCodHospital())).getValue(Hospital.class);
                        assert h != null;
                        Log.d("HOSPITAL", h.toString());
                        setTitle(h.getNombre());
                        cargarCamas();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("HOSPITAL", databaseError.toString());
                        showAlert();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Muestra un AlertDialog en indicando al usuario que ha habido un error y
     * le pide que lo vuelva a intentar o que salga de la aplicación
     *
     * @see MisCamas#salir()
     */
    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error el cargar los datos.\nCompruebe su conexión a Internet e inténtelo de nuevo más tarde");
        builder.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getHospital();
            }
        });
        builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                salir();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                salir();
            }
        });
        builder.create().show();
    }

    /**
     * Hace logout y lleva al login
     */
    private void salir() {
        FirebaseAuth.getInstance().signOut();
        toLogin();
    }

    /**
     * Carga las distintas secciones de la vista con los datos del hospital en el
     * que trabaja el usuario y oculta el OpacityPane y la ProgressBar
     *
     * @see Hospital
     * @see Camas
     * @see MisCamas#opacityPane
     * @see MisCamas#proggresBar
     */
    private void cargarCamas() {
        cargarSeccionUCI(h.getListaCamasUCI());
        cargarSeccionUrgencias(h.getListaCamasUrgencias());
        cargarSeccionPlanta(h.getListaCamasPlanta());

        opacityPane.setVisibility(View.GONE);

        proggresBar.setVisibility(View.GONE);

        lblUCI.setVisibility(View.VISIBLE);
        lblUrgencias.setVisibility(View.VISIBLE);
        lblPlanta.setVisibility(View.VISIBLE);

        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Carga la sección de las camas de UCI del hospital en el que trabaja el usuario
     *
     * @param camas camas de UCI del hospital en el que trabaja el usuario
     * @see Hospital
     * @see Camas
     * @see UCI
     */
    private void cargarSeccionUCI(List<UCI> camas) {
        int totalCamas = camas.size();
        Log.d("TOTAL CAMAS UCI", String.valueOf(totalCamas));
        int numFilas = (totalCamas / 7) + 1;
        Log.d("TOTAL FILAS UCI", String.valueOf(numFilas));
        int index = 0;

        for (int i = 0; i <= numFilas && index < totalCamas; i++) {
            Log.d("BUCLE FUERA: ", String.valueOf(i));
            LinearLayout lhor = new LinearLayout(this);
            lhor.setOrientation(LinearLayout.HORIZONTAL);
            lhor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < 7 && index < totalCamas; j++) {
                ImageView img = new ImageView(this);
                final UCI cama = camas.get(index);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
                switch (cama.getEstado()) {
                    case "libre":
                        img.setImageResource(R.drawable.cama_verde);
                        img.setTag(R.drawable.cama_verde);
                        break;
                    case "ocupado":
                        img.setImageResource(R.drawable.cama_roja);
                        img.setTag(R.drawable.cama_roja);
                        break;
                    case "noDisponible":
                        img.setImageResource(R.drawable.cama_amarilla);
                        img.setTag(R.drawable.cama_amarilla);
                        break;
                }
                if (j != 6)
                    params.setMargins(0, 0, 32, 32);
                img.setLayoutParams(params);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cargarVentanaCama(cama);
                    }
                });
                if (cama.isContagio())
                    img.setBackground(getResources().getDrawable(R.drawable.fondo_contagio));
                lhor.addView(img);
                index++;
            }
            rootUCI.addView(lhor);
            rootUCI.invalidate();
        }
    }

    /**
     * Carga la sección de las camas de Urgencias del hospital en el que trabaja el usuario
     *
     * @param camas camas de Urgencias del hospital en el que trabaja el usuario
     * @see Hospital
     * @see Camas
     * @see Urgencias
     */
    private void cargarSeccionUrgencias(List<Urgencias> camas) {
        int totalCamas = camas.size();
        Log.d("TOTAL CAMAS URGENCIAS", String.valueOf(totalCamas));
        int numFilas = (totalCamas / 7) + 1;
        Log.d("TOTAL FILAS URGENCIAS", String.valueOf(numFilas));
        int index = 0;

        for (int i = 0; i <= numFilas && index < totalCamas; i++) {
            Log.d("BUCLE FUERA: ", String.valueOf(i));
            LinearLayout lhor = new LinearLayout(this);
            lhor.setOrientation(LinearLayout.HORIZONTAL);
            lhor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < 7 && index < totalCamas; j++) {
                ImageView img = new ImageView(this);
                final Urgencias cama = camas.get(index);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
                switch (cama.getEstado()) {
                    case "libre":
                        img.setImageResource(R.drawable.cama_verde);
                        img.setTag(R.drawable.cama_verde);
                        break;
                    case "ocupado":
                        img.setImageResource(R.drawable.cama_roja);
                        img.setTag(R.drawable.cama_roja);
                        break;
                    case "noDisponible":
                        img.setImageResource(R.drawable.cama_amarilla);
                        img.setTag(R.drawable.cama_amarilla);
                        break;
                }
                if (j != 6)
                    params.setMargins(0, 0, 32, 32);
                img.setLayoutParams(params);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cargarVentanaCama(cama);
                    }
                });
                if (cama.isContagio())
                    img.setBackground(getResources().getDrawable(R.drawable.fondo_contagio));
                lhor.addView(img);
                index++;
            }
            System.out.println("INDECE: " + index);
            rootUrgencias.addView(lhor);
            rootUrgencias.invalidate();
        }
    }

    /**
     * Carga la sección de las camas de Planta del hospital en el que trabaja el usuario
     *
     * @param camas camas de Planta del hospital en el que trabaja el usuario
     * @see Hospital
     * @see Camas
     * @see Planta
     */
    private void cargarSeccionPlanta(List<Planta> camas) {
        int totalCamas = camas.size();
        Log.d("TOTAL CAMAS PLANTA", String.valueOf(totalCamas));
        int numFilas = (totalCamas / 7) + 1;
        Log.d("TOTAL FILAS PLANTA", String.valueOf(numFilas));
        int index = 0;

        for (int i = 0; i <= numFilas && index < totalCamas; i++) {
            Log.d("BUCLE FUERA: ", String.valueOf(i));
            LinearLayout lhor = new LinearLayout(this);
            lhor.setOrientation(LinearLayout.HORIZONTAL);
            lhor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < 7 && index < totalCamas; j++) {
                ImageView img = new ImageView(this);
                final Planta cama = camas.get(index);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
                switch (cama.getEstado()) {
                    case "libre":
                        img.setImageResource(R.drawable.cama_verde);
                        img.setTag(R.drawable.cama_verde);
                        break;
                    case "ocupado":
                        img.setImageResource(R.drawable.cama_roja);
                        img.setTag(R.drawable.cama_roja);
                        break;
                    case "noDisponible":
                        img.setImageResource(R.drawable.cama_amarilla);
                        img.setTag(R.drawable.cama_amarilla);
                        break;
                }
                if (j != 6)
                    params.setMargins(0, 0, 32, 32);
                img.setLayoutParams(params);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cargarVentanaCama(cama);
                    }
                });
                if (cama.isContagio())
                    img.setBackground(getResources().getDrawable(R.drawable.fondo_contagio));
                lhor.addView(img);
                index++;
            }
            rootPlanta.addView(lhor);
            rootPlanta.invalidate();
        }
    }

    /**
     * Abre el Dialog para editar las camas
     *
     * @param cama cama a editar
     * @see Camas
     * @see Hospital
     * @see DialogCama
     */
    private void cargarVentanaCama(Camas cama) {
        this.camaSeleccionada = cama;
        new DialogCama(this, this, cama, h).show();
    }

    /**
     * Lleva a la ventana de Reservar
     *
     * @see Reservar
     */
    private void toReservar() {
        Intent inte = new Intent(this.getApplicationContext(), Reservar.class);
        finish();
        inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(inte);
    }

    /**
     * Lleva a la ventana de Mi Hospital
     */
    private void toHospital() {
        Intent inte = new Intent(this.getApplicationContext(), MiHospital.class);
        inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(inte);
    }

    /**
     * Lleva a la ventana de configuración del hospital
     *
     * @see Datos
     */
    private void toConfig() {
        Intent inte = new Intent(this, Datos.class);
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
     * Devuelve la cama seleccionada por el usuario
     *
     * @return cama seleccionada
     * @see Camas
     */
    public Camas getCamaSeleccionada() {
        return this.camaSeleccionada;
    }
}