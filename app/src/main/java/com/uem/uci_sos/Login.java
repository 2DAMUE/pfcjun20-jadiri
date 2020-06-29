package com.uem.uci_sos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * Login de la aplicación. Desde él puedes navegar al registro y a la página principal de la aplicación haciendo login
 * correctamente.
 *
 * @see Registro
 */
public class Login extends Activity implements View.OnClickListener {

    private FirebaseAuth auth;

    /**
     * EditText que contiene el e-mail del usuario
     */
    private EditText txtEmail;
    /**
     * EditText que contiene la contraseña del usuario
     */
    private EditText txtPwd;

    /**
     * Label que lleva al registro
     *
     * @see Registro
     */
    private TextView lblRegistro;

    /**
     * Botón encargado del login
     */
    private Button btnLogin;

    /**
     * E-mail del usuario
     */
    private String email;
    /**
     * Contraseña del usuario
     */
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        cargarVista();
        cargarListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null)
            toHospital();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.lblRegistro:
                toRegistro();
                break;
            case R.id.btnLogin:
                leerCampos();
                if (comprobar())
                    login();
                break;
        }
    }

    /**
     * Verifica si los campos han sido rellenados
     *
     * @return true si todos los campos están rellenos. false en caso contrario
     */
    private boolean comprobar() {
        if (email.equals("") || pwd.equals("")) {
            showToast("Por favor, rellene todos los campos");
            return false;
        }
        return true;
    }

    /**
     * Intenta hacer login. Si lo consigue envía al usuario a la ventana principal de la aplicación.
     * Si no, avisa al usuario
     */
    private void login() {
        auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("LOGIN", "ÉXITO");
                    toHospital();
                } else {
                    Log.w("LOGIN", Objects.requireNonNull(task.getException()).toString());
                    showToast("E-mail o contraseña incorrectas");
                }
            }
        });
    }

    /**
     * Almacena la información de los campos en sus variables correcpondientes
     *
     * @see Login#email
     * @see Login#pwd
     */
    private void leerCampos() {
        email = txtEmail.getText().toString().trim();
        pwd = txtPwd.getText().toString().trim();
    }

    /**
     * Muestra un toast dando un mensaje al usuario
     *
     * @param mensaje mensaje a mostrar
     */
    private void showToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
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

        txtEmail = findViewById(R.id.txtEmailLogin);
        txtPwd = findViewById(R.id.txtPwdLogin);
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

    /**
     * Lleva a la ventana de Mi Hospital
     *
     * @see MiHospital
     */
    private void toHospital() {
        Intent inte = new Intent(this.getApplicationContext(), MiHospital.class);
        finish();
        startActivity(inte);
    }
}
