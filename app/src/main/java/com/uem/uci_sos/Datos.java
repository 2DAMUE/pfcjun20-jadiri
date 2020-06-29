package com.uem.uci_sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.uem.uci_sos.modelo.entidad.Hospital;
import com.uem.uci_sos.modelo.entidad.Referencias;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Crea un hospital nuevo, con un nombre y un ID único. Lleva a la ventana de configurar planta
 *
 * @see Hospital
 * @see ConfigPlanta
 */
public class Datos extends AppCompatActivity {

    /**
     * EditText que contiene el nombre del hospital
     */
    private EditText txtNombre;
    /**
     * EditText que contiene el número de plantas que posee un hospital
     */
    private EditText txtNumPlantas;

    /**
     * Botón que lleva a la ventana para configurar las plantas
     *
     * @see ConfigPlanta
     */
    private Button btnSiguiente;

    /**
     * Botón que lleva a la ventana de Mi Hospital
     *
     * @see MiHospital
     */
    private LinearLayout btnHosp;
    /**
     * Botón que lleva a la ventana de Mis Camas
     *
     * @see MisCamas
     */
    private LinearLayout btnCamas;

    /**
     * Botón que lleva a la ventana de Reservar
     *
     * @see Reservar
     */
    private LinearLayout btnReserva;

    /**
     * Nombre del hospital
     */
    private String nombreHospital;

    /**
     * Número de plantas edl hospital
     */
    private int numPlantas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        setTitle("Crear Hospital");
        cargarVista();
        cargarListeners();
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
     * Carga los listeners de los elementos de la ventana
     */
    private void cargarListeners() {
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comprobar())
                    mostrarAdvertencia();
            }
        });

        btnReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toReserva();
            }
        });

        btnCamas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMisCamas();
            }
        });

        btnHosp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHospital();
            }
        });
    }

    /**
     * Lleva a la ventana de Reserva
     *
     * @see Reservar
     */
    private void toReserva() {
        Intent intent = new Intent(this, Reservar.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

    /**
     * Lleva a la ventana de Mi Hospital
     *
     * @see MiHospital
     */
    private void toHospital() {
        Intent intent = new Intent(this, MiHospital.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

    /**
     * Lleva a la ventana de Mis Camas
     *
     * @see MisCamas
     */
    private void toMisCamas() {
        Intent intent = new Intent(this, MisCamas.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

    /**
     * Advierte al usuario de que no se podrán realizar cambios una vez creado el hospital. Si acepta las
     * condiciones creará el hospital y seguirá adelante con la creación de cada planta del hospital.
     * Si no las acepta desaparece la advertencia.
     */
    private void mostrarAdvertencia() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Advertencia");
        builder.setMessage("Una vez creado el hospital, no se podrán cambiar los datos. ¿Desea continuar?");
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                crearHospital();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    /**
     * Comprueba si se han rellenado todos los campos y si se han rellendao correctamente.
     *
     * @return tre si los datos son correctos. false en caso contrario
     */
    private boolean comprobar() {
        nombreHospital = txtNombre.getText().toString().trim();
        if (nombreHospital.equals("")) {
            showToast("Por favor, rellene todos los campos");
            return false;
        }
        try {
            numPlantas = Integer.parseInt(txtNumPlantas.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showToast("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de plantas");
            return false;
        }
        if (numPlantas <= 0) {
            showToast("Debe haber al menos una planta");
            return false;
        }
        return true;
    }

    /**
     * Crea un hospital con su nombre e ID único. Lleva a la ventana de configurar planta
     *
     * @see Hospital
     * @see ConfigPlanta
     */
    private void crearHospital() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference hos = db.getReference(Referencias.HOSPITALES);
        hos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int id = (int) dataSnapshot.getChildrenCount();
                Log.d("CARGAR_ID", "ID: " + id);
                Log.d("CARGAR_NOMBRE", "Nombre: " + nombreHospital);
                Hospital h = new Hospital();
                h.setCodHospital(id);
                h.setNombre(nombreHospital);
                toConfigPlanta(h);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("CARGAR_ID", databaseError.toString());
                showToast("Error al crear el hospital\nPor favor, compruebe su conexión a Internet e inténtelo de nuevo más tarde");
            }
        });
    }

    private void toConfigPlanta(Hospital h) {
        Intent intent = new Intent(this, ConfigPlanta.class);
        intent.putExtra("hospital", h);
        intent.putExtra("numPlantas", numPlantas);
        finish();
        startActivity(intent);
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
     * Carga los elemento de la ventana
     */
    private void cargarVista() {
        txtNombre = findViewById(R.id.txtNombreHospital);
        txtNumPlantas = findViewById(R.id.txtNumPlantas);

        btnSiguiente = findViewById(R.id.btnToConfigPlantas);

        btnHosp = findViewById(R.id.btnHospitalDatos);
        btnCamas = findViewById(R.id.btnMisCamasDatos);
        btnReserva = findViewById(R.id.btnReservarDatos);
    }
}
