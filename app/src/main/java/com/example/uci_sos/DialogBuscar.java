package com.example.uci_sos;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import com.example.uci_sos.modelo.entidad.Hospital;
import com.example.uci_sos.modelo.entidad.Camas;

/**
 * Muestra al usuario a que tipo de camas puede derivar a un paciente
 *
 * @see Hospital
 * @see Camas
 */
public class DialogBuscar extends Dialog implements View.OnClickListener {

    private Activity activity;

    private Hospital hospital;

    public DialogBuscar(Activity activity, Hospital hospital) {
        super(activity);
        this.activity = activity;
        this.hospital = hospital;
    }

    @Override
    public void onClick(View v) {

    }
}
