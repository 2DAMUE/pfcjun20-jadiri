package com.example.uci_sos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Login de la aplicación. Desde el puedes navegar al registro y a la página principal de la aplicación.
 *
 * @see Registro
 */
public class Login extends Activity implements View.OnClickListener {

    /**
     * Label que lleva al registro
     *
     * @see Registro
     */
    TextView lblRegistro;

    /**
     * Botón encargado del login
     */
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cargarVista();
        cargarListeners();
    }

    /**
     * Carga los listeners de los elementos de la ventana
     */
    private void cargarListeners() {
        lblRegistro.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    /**
     * Carga los elemento de la ventana
     */
    private void cargarVista() {
        lblRegistro = findViewById(R.id.lblRegistro);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.lblRegistro:
                toRegistro();
                break;
            case R.id.btnLogin:
                toReservar();
                break;
        }
    }

    /**
     * Lleva al registro
     *
     * @see Registro
     */
    private void toRegistro() {
        Intent inte = new Intent(this.getApplicationContext(), Registro.class);
        startActivity(inte);
    }

    private void toReservar() {
        Intent inte = new Intent(this.getApplicationContext(), Reservar.class);
        finish();
        startActivity(inte);
    }
}
