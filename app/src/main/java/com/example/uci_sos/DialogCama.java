package com.example.uci_sos;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uci_sos.modelo.entidad.Camas;
import com.example.uci_sos.modelo.entidad.Hospital;
import com.example.uci_sos.modelo.entidad.Referencias;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

/**
 * Cuadro de diálogo que permite al usuario edital la cama seleccionada
 *
 * @see Camas
 * @see MisCamas
 */
public class DialogCama extends Dialog implements View.OnClickListener {

    /**
     * Activity de MisCamas
     *
     * @see MisCamas
     */
    private Activity activity;

    /**
     * Botón para guardar los datos en una cama
     */
    private Button btnGuardar;
    /**
     * Botón para eliminar el Dialog si guardar la cama
     */
    private Button btnCancelar;

    /**
     * Campo que contiene el estado de la cama
     */
    private Spinner spEstado;
    /**
     * Campo que contiene la planta de la cama
     */
    private Spinner spPlanta;

    /**
     * Campo con el ID de la cama
     */
    private EditText txtId;

    /**
     * ChechBox que indica si la cama seleccionada es o no contagiosa
     */
    private CheckBox chContagio;

    /**
     * Cama a editar
     *
     * @see Camas
     */
    private Camas cama;

    /**
     * Hospital al que pertenece la cama
     *
     * @see Hospital
     */
    private Hospital h;

    /**
     * Objeto de MisCamas para poder acceder al método cargarVista
     *
     * @see MisCamas
     * @see MisCamas#cargarVista()
     */
    private MisCamas misCamas;


    public DialogCama(MisCamas misCamas, Activity activity, Camas cama, Hospital h) {
        super(activity);

        this.misCamas = misCamas;
        this.activity = activity;
        this.cama = cama;
        Log.d("CAMA", cama.toString());
        this.h = h;
        if (this.h.getListaPlantas().get(0) == null)
            this.h.getListaPlantas().remove(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_mis_camas);
        cargarVista();
        cargarListeners();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGuardarCama:
                actualizarCama();
                break;
            case R.id.btnCancelarCama:
                dismiss();
                break;
        }
    }

    /**
     * Actualiza la cama con los cambios producidos por el usuario
     *
     * @see Camas
     * @see Hospital
     */
    private void actualizarCama() {
        switch ((String) spEstado.getSelectedItem()) {
            case "Libre":
                cama.setEstado("libre");
                break;
            case "Ocupada":
                cama.setEstado("ocupado");
                break;
            case "No Disponible":
                cama.setEstado("noDisponible");
                break;
        }
        cama.setPlanta((String) spPlanta.getSelectedItem());
        cama.setContagio(chContagio.isChecked());
        cama.setId(Integer.parseInt(txtId.getText().toString()));

        guardar();
    }

    /**
     * Almacena el hospital en la base de datos y cierra el Dialog
     *
     * @see Hospital
     */
    private void guardar() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference hospitales = db.getReference(Referencias.HOSPITALES);
        hospitales.child(String.valueOf(h.getCodHospital())).setValue(h).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("ACTUALIZAR_HOSPITAL", "ÉXITO");
                    showToast("Cambios guardados");
                    misCamas.cargarVista();
                    dismiss();
                } else {
                    Log.w("ACTUALIZAR_HOSPITAL", Objects.requireNonNull(task.getException()).toString());
                    showToast("Error al guardar los cambios\nCompruebe su conexión a Internet e inténtelo de nuevo más tarde");
                }
            }
        });
    }

    /**
     * Muestra un mensaje el usuario mediante un Toast
     *
     * @param mensaje mensaje a mostrar
     */
    private void showToast(String mensaje) {
        Toast.makeText(activity.getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    /**
     * Carga los listeners de los elementos de la ventana
     */
    private void cargarListeners() {
        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
    }

    /**
     * Carga los elementos de la ventana
     */
    private void cargarVista() {
        btnGuardar = findViewById(R.id.btnGuardarCama);
        btnCancelar = findViewById(R.id.btnCancelarCama);

        spEstado = findViewById(R.id.spEstado);
        spPlanta = findViewById(R.id.spPlanta);

        txtId = findViewById(R.id.txtIdCama);

        chContagio = findViewById(R.id.chContagio);

        cargarDatos();
    }

    /**
     * Carga los datos de la cama seleccionada en los campos correspondientes
     */
    private void cargarDatos() {
        txtId.setText(String.valueOf(cama.getId()));

        chContagio.setChecked(cama.isContagio());

        spEstado = findViewById(R.id.spEstado);
        spPlanta = findViewById(R.id.spPlanta);

        cargarSpinnerEstado();
        cargarSpinnerPlanta();
    }

    /**
     * Carga los nombres de las plantas del hospital en el spinner
     *
     * @see DialogCama#spPlanta
     */
    private void cargarSpinnerPlanta() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DialogCama.this.getContext(), R.layout.spinner_mis_camas, h.getListaPlantas()) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                TextView item = (TextView) v;
                item.setTextColor(Color.BLACK);
                return v;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPlanta.setAdapter(adapter);

        int index = h.getListaPlantas().indexOf(cama.getPlanta());
        spPlanta.setSelection(index);
    }

    /**
     * Carga los diferentes estados en los que puede estar una camilla
     * en el spinner
     *
     * @see DialogCama#spEstado
     */
    private void cargarSpinnerEstado() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DialogCama.this.getContext(), R.layout.spinner_mis_camas,
                activity.getResources().getStringArray(R.array.estados)) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                TextView item = (TextView) v;
                item.setTextColor(Color.BLACK);
                return v;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(adapter);
        switch (cama.getEstado()) {
            case "libre":
                spEstado.setSelection(0);
                break;
            case "ocupado":
                spEstado.setSelection(1);
                break;
            case "noDisponible":
                spEstado.setSelection(2);
                break;
        }
    }
}
