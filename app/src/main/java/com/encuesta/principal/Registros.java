package com.encuesta.principal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Registros extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private TextView pantalla;
    private ListView mlistView;
    private Button recargar;
    private TextView buscarId;
    private RequestQueue requestQueue;
    private List<String> mLista = new ArrayList<>();
    private List<Integer> arrIds = new ArrayList<>();
    private ArrayAdapter<String> miAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);
        recargar = findViewById(R.id.recargar);
        buscarId = findViewById(R.id.buscarId);// inicializar variable, con buscarId que esta en actibity_registro

        pantalla = findViewById(R.id.pantalla);
        mlistView = findViewById(R.id.listaDatos);
        mlistView.setOnItemClickListener(this);

        requestQueue = Volley.newRequestQueue(this);

        //datos();
//        jsonArregloOjetos();

    }

    public void buttonRecargarDos(View v){
        jsonArregloOjetos();
    }

    public void datos() {
        String URL1 = "https://yeniuno.000webhostapp.com/fetch.php?id=29";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pantalla.setText(response.toString().trim());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);
    }

    private void jsonOjetos(String bb) {
        String url = "https://yeniuno.000webhostapp.com/fetch.php?id=".concat(bb);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //pantalla.setText("");
                        try {
                            String id = response.getString("id_encuesta");
                            String nombre = response.getString("nombre_encuesta");
                            int edad = response.getInt("edad_encuesta");
                            String telefono = response.getString("phone_encuesta");
                            String genero = response.getString("gene_encuesta");
                            String lenguaje = response.getString("lenguaje_encuesta");

                            pantalla.append("ID: " + id + "\n");
                            pantalla.append("Nombre: " + nombre + "\n");
                            pantalla.append("Edad: " + edad + "\n");
                            pantalla.append("Teléfono: " + telefono + "\n");
                            pantalla.append("Género: " + genero + "\n");
                            pantalla.append("Lenguaje: " + lenguaje + "\n");


                            mLista.add(nombre);
                            //mLista.add("nombre");
                            mlistView.setAdapter(miAdapter);


                            //pantalla.setText(response.toString());
                            Toast.makeText(Registros.this, "Entro....", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registros.this, "Error no Conectado..", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

        miAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLista);
        //mlistView.setAdapter(miAdapter);

    }

    private void jsonArregloOjetos() {
        String url = "https://yeniuno.000webhostapp.com/arro.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int size = response.length();
                        for (int i = 0; i < size; i++) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                String name = jsonObject.getString("nombre_encuesta");
                                int ids = jsonObject.getInt("id_encuesta");

                                mLista.add(name );
                                arrIds.add(ids);
                                mlistView.setAdapter(miAdapter);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        miAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLista);

        requestQueue.add(jsonArrayRequest);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent ven = new Intent(Registros.this, DatoEncuestado.class);
        ven.putExtra("idEnc",arrIds.get(i).toString());
        this.startActivity(ven);
    }
}