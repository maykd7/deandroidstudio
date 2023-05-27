package com.encuesta.principal;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Formulario extends AppCompatActivity {

    private TextView tName, tOld, tPhone, tGenero, tLenguaje, textXid;
    private Button bGuardar, buscarXid;
    private RequestQueue requestQueue;
    private RadioButton rbMujer;
    private RadioButton rbSi;
    private static final String URL1 = "https://yeniuno.000webhostapp.com/save.php";
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        requestQueue = Volley.newRequestQueue(this);

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            id =  extra.getString("id");
            buscarXID();
        }

        intUI();
    }

    private void intUI(){
        tName = findViewById(R.id.editTextNombre);
        tOld = findViewById(R.id.editTextEdad);
        tPhone = findViewById(R.id.editTextTelefono);

        bGuardar = findViewById(R.id.buttonGuardar);

        rbMujer = findViewById(R.id.radioButtonMujer);

        rbSi= findViewById(R.id.radioButtonSi);
        textXid = findViewById(R.id.editTextXid);
        buscarXid = findViewById(R.id.busarXid);

    }

    public void guardar(View v){
        String name = tName.getText().toString().trim();
        String old = tOld.getText().toString().trim();
        String phone = tPhone.getText().toString().trim();
        String gene;
        String lengu;

        if(rbMujer.isChecked()){
            gene = "Mujer";
        }
        else gene = "Hombre";

        if(rbSi.isChecked()){
            lengu = "Si";
        }
        else lengu = "No";
        crearUsuarios(name, old, phone, gene, lengu);
    }

    private void crearUsuarios(final String name, final String old, final String phone, final String gene, final String lengu) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Formulario.this, "Guardando..", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Formulario.this, "Error no Guardado..", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(Formulario.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("old", old);
                params.put("phone", phone);
                params.put("gene" ,gene);
                params.put("lengu" ,lengu);

                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    public void buscarXDI(View v){
        Intent intent = new Intent(this, Formulario.class);
        intent.putExtra("id", textXid.getText().toString().trim());
        startActivity(intent);
    }

    private void buscarXID(){
        String url = "https://yeniuno.000webhostapp.com/fetch.php?id="+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("id_encuesta");
                            String nombre = response.getString("nombre_encuesta");
                            int edad = response.getInt("edad_encuesta");
                            String telefono = response.getString("phone_encuesta");
                            String genero = response.getString("gene_encuesta");
                            String lenguaje = response.getString("lenguaje_encuesta");

                            tName.setText(nombre);
                            tOld.setText(edad+"");
                            tPhone.setText(telefono);

                            Toast.makeText(Formulario.this, "Todo bien....", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Formulario.this, "Error no Conectado..", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

}