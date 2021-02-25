package com.izram.gamestoreapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class AddGameActivity extends AppCompatActivity {

    TextInputEditText name, year, description, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);


        Button buttonAdd = findViewById(R.id.btn_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarGame();
            }
        });


    }

    private void adicionarGame() {

        name = findViewById(R.id.ti_name);
        year = findViewById(R.id.ti_year);
        description = findViewById(R.id.ti_description);
        imageUrl = findViewById(R.id.ti_imageUrl);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name.getText().toString());
            jsonObject.put("year", Integer.parseInt(year.getText().toString()));
            jsonObject.put("description", description.getText().toString());
            jsonObject.put("image_url", imageUrl.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.104:8080/api/add";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);

    }
}