package com.uem.uci_sos;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.uem.uci_sos.modelo.entidad.Hospital;
import com.uem.uci_sos.modelo.entidad.Camas;
import com.uem.uci_sos.modelo.entidad.Planta;
import com.uem.uci_sos.modelo.entidad.Referencias;
import com.uem.uci_sos.modelo.entidad.Urgencias;
import com.uem.uci_sos.modelo.entidad.UCI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Muestra al usuario a que tipo de camas puede derivar a un paciente
 *
 * @see Hospital
 * @see Camas
 */
public class DialogBuscar extends Dialog implements View.OnClickListener {

    /**
     * Activity de Buscar
     *
     * @see Buscar
     */
    private Activity activity;

    /**
     * Botón para derivar a un paciente a una cama de Planta
     *
     * @see Camas
     * @see Planta
     */
    private Button btnPlanta;
    /**
     * Botón para derivar a un paciente a una cama de Urgencias
     *
     * @see Camas
     * @see Urgencias
     */
    private Button btnUrgencias;
    /**
     * Botón para derivar a un paciente a una cama de UCI
     *
     * @see Camas
     * @see UCI
     */
    private Button btnUCI;
    /**
     * Botón para cancelar la opción de derivar al hospital seleccionado
     */
    private Button btnCancelar;

    /**
     * Hospital seleccionado para la derivación
     *
     * @see Buscar
     */
    private Hospital hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_buscar);
        cargarVista();
    }

    public DialogBuscar(Activity activity, Hospital hospital) {
        super(activity);
        this.activity = activity;
        this.hospital = hospital;
    }

    /**
     * Carga los elementos de la ventana
     */
    private void cargarVista() {
        btnPlanta = findViewById(R.id.btnDerivarPlanta);
        btnUrgencias = findViewById(R.id.btnDerivarUrgencias);
        btnUCI = findViewById(R.id.btnDerivarUCI);

        btnCancelar = findViewById(R.id.btnCancelarBuscar);

        if (hospital.getCamasPlantaLibres() == 0) {
            btnPlanta.setEnabled(false);
            btnPlanta.setBackground(activity.getResources().getDrawable(R.drawable.boton_disabled));
            btnPlanta.setTextColor(Color.GRAY);
        }
        if (hospital.getCamasUrgenciasLibres() == 0) {
            btnUrgencias.setEnabled(false);
            btnUrgencias.setBackground(activity.getResources().getDrawable(R.drawable.boton_disabled));
            btnUrgencias.setTextColor(Color.GRAY);
        }
        if (hospital.getCamasUciLibres() == 0) {
            btnUCI.setEnabled(false);
            btnUCI.setBackground(activity.getResources().getDrawable(R.drawable.boton_disabled));
            btnUCI.setTextColor(Color.GRAY);
        }
        cargarListeners();
    }

    /**
     * Carga los listerens de los elementos de la ventana
     */
    private void cargarListeners() {
        btnPlanta.setOnClickListener(this);
        btnUrgencias.setOnClickListener(this);
        btnUCI.setOnClickListener(this);

        btnCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDerivarPlanta:
                reservarCama(0);
                break;
            case R.id.btnDerivarUrgencias:
                reservarCama(1);
                break;
            case R.id.btnDerivarUCI:
                reservarCama(2);
                break;
            case R.id.btnCancelarBuscar:
                dismiss();
                break;
        }
    }

    /**
     * Ocupa una cama libre del tipo indicado del hospital
     *
     * @param tipoCama 0 camas de Planta
     *                 1 camas de Urgencias
     *                 2 camas de UCI
     * @see Camas
     * @see DialogBuscar#getCamaLibre(int)
     */
    private void reservarCama(int tipoCama) {
        getCamaLibre(tipoCama).setEstado("ocupado");
        guardarCambios();
    }

    /**
     * Guarda los cambios del hospital en la base de datos y cierra el dialog
     */
    private void guardarCambios() {
        LinearLayout progressDialog = activity.findViewById(R.id.progressDialogBuscar);
        progressDialog.setVisibility(View.VISIBLE);
        View opacityPane = activity.findViewById(R.id.opBuscar);
        opacityPane.setVisibility(View.VISIBLE);
        dismiss();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference hospitales = db.getReference(Referencias.HOSPITALES);
        hospitales.child(String.valueOf(hospital.getCodHospital())).setValue(hospital)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("GUARDAR_DERIVACION", "ÉXITO");
                            Intent intent = new Intent(activity, MiHospital.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            showToast("Paciente derivado al hospital:\n" + hospital.getNombre());
                            activity.startActivity(intent);
                        } else {
                            Log.w("GUARDAR_DERIVACION", task.getException().toString());
                            showToast("Error al guardar los cambios\nCompruebe su conexión a Internet e inténtelo de nuevo más tarde");
                        }
                    }
                });
    }

    /**
     * Muestra un toast dando un mensaje al usuario
     *
     * @param mensaje mensaje a mostrar
     */
    private void showToast(String mensaje) {
        Toast.makeText(activity, mensaje, Toast.LENGTH_LONG).show();
    }

    /**
     * Devuelve la primera cama libre del tipo indicado del hospital
     *
     * @param tipoCama 0 camas de Planta
     *                 1 camas de Urgencias
     *                 2 camas de UCI
     * @return primera cama libre del tipo indicado del hospital.
     * null si el tipo de cama es inválido
     * @see Camas
     */
    private Camas getCamaLibre(int tipoCama) {
        switch (tipoCama) {
            case 0:
                for (Camas cama : hospital.getListaCamasPlanta()) {
                    if (cama.getEstado().equals("libre"))
                        return cama;
                }
                break;
            case 1:
                for (Camas cama : hospital.getListaCamasUrgencias()) {
                    if (cama.getEstado().equals("libre"))
                        return cama;
                }
                break;
            case 2:
                for (Camas cama : hospital.getListaCamasUCI()) {
                    if (cama.getEstado().equals("libre"))
                        return cama;
                }
                break;
        }
        return null;
    }
}
