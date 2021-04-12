package com.izram.gamestoreapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.izram.gamestoreapp.R;
import com.izram.gamestoreapp.model.Game;

import org.json.JSONException;
import org.json.JSONObject;

public class AddGameActivity extends AppCompatActivity {

    TextInputEditText name, year, description, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        Button buttonAdd = findViewById(R.id.btn_add);

        if(getIntent().getIntExtra("ACTION", -1) == 0){

            TextView textViewTitle = findViewById(R.id.tv_add_game_title);
            textViewTitle.setText("Update Game");
            buttonAdd.setText("Update Game");

            Game game = (Game) getIntent().getSerializableExtra("GAME");

            name = findViewById(R.id.ti_name);
            year = findViewById(R.id.ti_year);
            description = findViewById(R.id.ti_description);
            imageUrl = findViewById(R.id.ti_imageUrl);

            name.setText(game.getName());
            year.setText(game.getYear());
            description.setText(game.getDescription());
            imageUrl.setText(game.getImage_Url());
        }


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int action = getIntent().getIntExtra("ACTION", -1);
                if (action == 1) {
                    adicionarGame();
                }else if (action == 0) {
                    editarGame();
                }else {
                    Toast.makeText(getApplicationContext(), "Ocorreu um erro em sua solicitação!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonCancel = findViewById(R.id.btn_back);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });


    }

    private void editarGame() {

        name = findViewById(R.id.ti_name);
        year = findViewById(R.id.ti_year);
        description = findViewById(R.id.ti_description);
        imageUrl = findViewById(R.id.ti_imageUrl);

        Game game = (Game) getIntent().getSerializableExtra("GAME");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", Integer.parseInt(game.getId()));
            jsonObject.put("name", name.getText().toString());
            jsonObject.put("year", Integer.parseInt(year.getText().toString()));
            jsonObject.put("description", description.getText().toString());
            jsonObject.put("image_url", imageUrl.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.104:8080/api/v1/games/edit/";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url+game.getId(), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
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
        String url = "http://192.168.0.104:8080/api/v1/games/add";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
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