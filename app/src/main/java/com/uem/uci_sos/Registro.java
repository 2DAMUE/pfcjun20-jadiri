package com.uem.uci_sos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.uem.uci_sos.modelo.entidad.Hospital;
import com.uem.uci_sos.modelo.entidad.Referencias;
import com.uem.uci_sos.modelo.entidad.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Registro de la aplicación. En él creas tu usuario para la aplicación
 */
public class Registro extends Activity {


    private FirebaseAuth auth;

    /**
     * Referencia al child de users
     */
    private DatabaseReference users;

    /**
     * Spinner con la lista de hospitales registrados en la aplicación
     */
    private Spinner spinHospital;

    /**
     * EditText que contiene el e-mail del usuario
     */
    private EditText txtEmail;
    /**
     * EditText que contiene el nombre del usuario
     */
    private EditText txtNombre;
    /**
     * EditText que contiene el apellido del usuario
     */
    private EditText txtApellido;
    /**
     * EditText que contiene la contraseña del usuario
     */
    private EditText txtPwd;
    /**
     * EditText que contiene la confirmación de la contraseña del usuario
     */
    private EditText txtConfirmPwd;

    /**
     * Botón que realiza el registro en la aplicación y hace el login.
     */
    private Button btnRegistrar;

    /**
     * Lista de hospitales registrados en la aplicación
     *
     * @see Hospital
     */
    private List<Hospital> lista;

    /**
     * E-mail con el que se registra el usuario. Debe ser único
     */
    private String email;
    /**
     * Nombre con el que se registra un nuevo usuario
     */
    private String nombre;
    /**
     * Apellido con el que se registra un nuevo usuario
     */
    private String apellido;
    /**
     * Contraseña de la cuenta de un nuevo usuario. Debe coincidir con confPwd
     *
     * @see Registro#confPwd
     */
    private String pwd;
    /**
     * Confirmación de la contraseña de la cuenta de un nuevo usuario. Debe coincidir con pwd
     *
     * @see Registro#pwd
     */
    private String confPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        users = db.getReference(Referencias.USERS);
        getHopitales();
        cargarVista();
        cargarListeners();
    }

    /**
     * Almacena la información de los campos en sus variables correspondientes.
     *
     * @see Registro#email
     * @see Registro#nombre
     * @see Registro#apellido
     * @see Registro#pwd
     * @see Registro#confPwd
     */
    private void leerCampos() {
        email = txtEmail.getText().toString().trim();
        nombre = txtNombre.getText().toString().trim();
        apellido = txtApellido.getText().toString().trim();
        pwd = txtPwd.getText().toString().trim();
        confPwd = txtConfirmPwd.getText().toString().trim();
    }

    /**
     * Comprueba si los datos introducidos por el usuario son correctos
     *
     * @return true si los datos introducidos son correctos. false en caso contrario
     * @see Registro#comprobarEmail()
     * @see Registro#comprobarPWD()
     * @see Registro#todoLleno()
     */
    private boolean comprobar() {
        return todoLleno() && comprobarPWD() && comprobarEmail();
    }

    /**
     * Verifica si se ha introducido una dirección de correo válida. Esta debe contener una única arroba y, al menos, un punto
     *
     * @return true si se ha introducido una dirección de correo válida. false en caso contrario
     */
    private boolean comprobarEmail() {
        int numPuntos = 0;
        int numArrobas = 0;
        for (int i = 0; i < email.length(); i++) {
            char letra = email.charAt(i);
            if (letra == '@')
                numArrobas++;
            if (letra == '.')
                numPuntos++;
        }
        if (numArrobas == 1 && numPuntos > 0)
            return true;
        showToast("Dirección de E-mail incorrecta.\nDebe contener una única @ y, al menos, un punto");
        return false;
    }

    /**
     * Verifica si se han rellenado todos los campos y se ha seleccionado un hospital. En caso de que haya algún campo vacío o no se haya
     * seleccionado un hospital, se avisará al usuario mediante un Toast de su error
     *
     * @return true si se han rellenado todos y seleccionado un hospital. false en caso contrario
     */
    private boolean todoLleno() {
        if (email.equals("") || nombre.equals("") || apellido.equals("") || pwd.equals("") || confPwd.equals("")) {
            showToast("Por favor, rellene todos los campos");
            return false;
        }
        if (spinHospital.getSelectedItemPosition() == 0) {
            showToast("Por favor, seleccione un hospital");
            return false;
        }
        return true;
    }

    /**
     * Comprueba si se ha introducido una contraseña válida en los campos de "Contraseña" y "Confirmar contraseña".
     * En caso de algún error se avisará al usuario con un Toast de su error
     *
     * @return false si las contraseñas introducidas no coinciden o si contienen menos de 6 caracteres.
     * true si se han introducido correctamente
     */
    private boolean comprobarPWD() {
        if (pwd.length() < 6 || confPwd.length() < 6) {
            showToast("La contraseña debe tener, al menos 6 caracteres.");
            return false;
        }
        if (!pwd.equals(confPwd)) {
            showToast("Las contraseñas no coinciden.");
            return false;
        }
        return true;
    }

    /**
     * Intenta crear un usuario nuevo. Si lo crea con éxito envía al usuario al login de la aplicación.
     * Si no, avisa al usuario
     */
    private void registro() {
        auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("CREAR USER", "ÉXITO");
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        guardarUser(user);
                        toLogin();
                    } else
                        showToast("Error al crear el usuario");
                } else {
                    Log.w("CREAR USER", Objects.requireNonNull(task.getException()).toString());
                    showToast("Error a crear el usuario");
                }
            }
        });
    }

    /**
     * Almacena los datos del usuario
     *
     * @param user usuario de Firebase
     */
    private void guardarUser(FirebaseUser user) {
        String Uid = user.getUid();
        users.child(Uid).setValue(new Usuario(nombre, apellido, lista.get(spinHospital.getSelectedItemPosition() - 1).getCodHospital())).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Log.d("GUARDAR_USER", "ÉXITO");
                else
                    Log.w("GUARDAR_USER", Objects.requireNonNull(task.getException()).toString());
            }
        });
    }

    /**
     * LLeva al Login de la aplicación
     */
    private void toLogin() {
        Intent inte = new Intent(this, Login.class);
        inte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(inte);
    }

    /**
     * Crea y carga la lista de hospitales en el spinner. Inhabilita el elemento de "Seleccionar hospital", el primero del spinner.
     *
     * @see Registro#spinHospital
     */
    private void cargarAdapter() {
        List<String> listaNombres = new ArrayList<>();
        listaNombres.add("Selecciona un hospital");
        for (Hospital h : lista) {
            listaNombres.add(h.getNombre());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_registro_item, listaNombres) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                if (position == 0) {
                    TextView item = (TextView) v;
                    item.setTextColor(Color.GRAY);
                }
                return v;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinHospital.setAdapter(adapter);

        View opacityPane = findViewById(R.id.opRegistro);
        ProgressBar pb = findViewById(R.id.pbRegistro);

        opacityPane.setVisibility(View.GONE);
        pb.setVisibility(View.GONE);
    }

    /**
     * Carga los hospitales de la base de datos para obtener sus nombres y enviarlos al spinner del registro
     *
     * @see Hospital
     * @see Registro#cargarAdapter()
     */
    private void getHopitales() {
        DatabaseReference hospitales = FirebaseDatabase.getInstance().getReference(Referencias.HOSPITALES);
        hospitales.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("HOSPITALES_SPINNER", "ÉXITO");
                lista = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    lista.add(snapshot.getValue(Hospital.class));
                }
                cargarAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("HOSPITALES_SPINNER", databaseError.toString());
                showToast("Ha habido un error al cargar los hospitales\nCompruebe su conexión a Internet e inténtelo de nuevo más tarde");
                toLogin();
            }
        });
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
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerCampos();
                if (comprobar()) {
                    registro();
                }
            }
        });
    }

    /**
     * Carga los elemento de la ventana
     */
    private void cargarVista() {
        spinHospital = findViewById(R.id.spinnerRegistro);

        txtEmail = findViewById(R.id.txtEmailRegistro);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtPwd = findViewById(R.id.txtPwdReg);
        txtConfirmPwd = findViewById(R.id.txtConfirmPwd);

        btnRegistrar = findViewById(R.id.btnRegistro);
    }
}
