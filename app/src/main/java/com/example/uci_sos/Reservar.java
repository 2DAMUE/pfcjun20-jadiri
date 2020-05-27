package com.example.uci_sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Reservar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);
        Button btnBuscar = findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setTitle("Reservar");
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