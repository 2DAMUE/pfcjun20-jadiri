package com.example.uci_sos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Login extends Activity implements View.OnClickListener {

    TextView lblRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cargarVista();
        cargarListeners();
    }

    private void cargarListeners() {
        lblRegistro.setOnClickListener(this);
    }

    private void cargarVista() {
        lblRegistro = findViewById(R.id.lblRegistro);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.lblRegistro:
                toRegistro();
                break;
        }
    }

    private void toRegistro() {
        Intent inte = new Intent(this.getApplicationContext(), Registro.class);
        startActivity(inte);
    }
}
