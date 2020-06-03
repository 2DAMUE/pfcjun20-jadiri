package com.example.uci_sos;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.uci_sos.modelo.entidad.Camas;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Cuadro de diálogo que permite al usuario edital la cama seleccionada
 *
 * @see Camas
 * @see MisCamas
 */
public class DialogCama extends Dialog implements View.OnClickListener {

    /**
     * Activity de MisCamas
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
     */
    private Camas cama;


    public DialogCama(Activity activity, Camas cama) {
        super(activity);

        this.activity = activity;
        this.cama = cama;
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

                break;
            case R.id.btnCancelarCama:
                salir();
                break;
        }
    }

    private void salir() {
        dismiss();
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
    }

    /**
     * Carga los diferentes estados en los que puede estar una camilla
     * en el spinner
     *
     * @see DialogCama#spEstado
     */
    private void cargarSpinnerEstado() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(DialogCama.this.getContext(),
                R.array.estados, R.layout.spinner_registro_item);
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
