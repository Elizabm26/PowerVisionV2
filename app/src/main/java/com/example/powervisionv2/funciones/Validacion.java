package com.example.powervisionv2.funciones;

import android.text.TextUtils;
import android.widget.EditText;

public class Validacion {
    public boolean validarCampos(EditText nombre, EditText email, EditText password) {
        boolean camposValidos = true;

        String nombres = nombre.getText().toString();
        String mail = email.getText().toString();
        String pass = password.getText().toString();

        if (TextUtils.isEmpty(nombres)) {
            nombre.setError("Ingresar sus nombres");
            nombre.requestFocus();
            camposValidos = false;
        } else if (TextUtils.isEmpty(mail)) {
            email.setError("Ingresar su correo electrónico");
            email.requestFocus();
            camposValidos = false;
        } else if (TextUtils.isEmpty(pass)) {
            password.setError("Ingresar su contraseña");
            password.requestFocus();
            camposValidos = false;
        }
        return camposValidos;
    }
    public boolean validarCorreoElectronico(String correo) {
        String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return correo.matches(pattern);
    }
}
