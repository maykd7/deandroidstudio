package com.encuesta.principal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button formulario, registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formulario = findViewById(R.id.buttonFormulario);
        registro = findViewById(R.id.buttonRegistro);
    }

    public void clicFormulario(View v){
        Intent i = new Intent(MainActivity.this, Formulario.class);
        startActivity(i);
    }
    public void clicRegistro(View v){
        Intent i = new Intent(MainActivity.this, Registros.class);
        startActivity(i);
    }
}