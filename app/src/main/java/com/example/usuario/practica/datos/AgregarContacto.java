package com.example.usuario.practica.datos;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.usuario.practica.Principal;
import com.example.usuario.practica.R;

import java.util.ArrayList;
import java.util.List;

public class AgregarContacto extends AppCompatActivity {

    private EditText etNom, etNum, etNum2;
    private List<String> telfns;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_contacto);
        etNom= (EditText)findViewById(R.id.etNombre);
        etNum=(EditText)findViewById(R.id.etNumero);
        etNum2=(EditText)findViewById(R.id.etNumero2);
        telfns= new ArrayList<>();
        telfns.add(etNum.getText().toString());
        telfns.add(etNum2.getText().toString());

;    }
    public void guardarContacto(View v){
        Contacto nuevo = new Contacto(etNom.getText().toString(),0,telfns);

        Intent intent = new Intent(this, Principal.class);
        intent.putExtra("contacto",nuevo);
        startActivity(intent);

        /* Prueba : String nombre= (String)etNom.getText().toString();
        Principal.metodo(nombre,telfns);*/
    }
}
