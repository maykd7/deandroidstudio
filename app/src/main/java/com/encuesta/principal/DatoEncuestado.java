package com.encuesta.principal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DatoEncuestado extends AppCompatActivity {
    private String idxEnc;
    private TextView datosEncuestado;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dato_encuestado);
        requestQueue = Volley.newRequestQueue(this);

        datosEncuestado = findViewById(R.id.datosEncuestado);

        //recoje el valor mandado por putExtra desde la ventana Registro
        Bundle extra = getIntent().getExtras();
        idxEnc =  extra.getString("idEnc");
//        Toast.makeText(this, ""+idxEnc, Toast.LENGTH_SHORT).show();
        cargarDatos();
    }

    public void cargarDatos(){
        String url = "https://yeniuno.000webhostapp.com/fetch.php?id="+idxEnc;
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

                            datosEncuestado.setText("Nombrea: "+nombre+" \n"+
                                                          "Edad: "+ edad+" \n"+
                                                            "Genero: "+ genero+" \n");


                            Toast.makeText(DatoEncuestado.this, "Todo bien....", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DatoEncuestado.this, "Error no Conectado..", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

}