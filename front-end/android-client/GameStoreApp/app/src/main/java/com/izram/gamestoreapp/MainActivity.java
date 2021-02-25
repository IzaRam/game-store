package com.izram.gamestoreapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Game> gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvGames = findViewById(R.id.rv_games);
        gameList = new ArrayList<>();


        //List all games
        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://10.0.2.2:8080/api/games";
        String url = "http://192.168.0.104:8080/api/games";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i=0; i<response.length(); i++) {
                        try {

                            JSONObject objGame = (JSONObject) response.get(i);

                            Game game = new Game(String.valueOf(objGame.getInt("id")),
                                    objGame.getString("name"), String.valueOf(objGame.getInt("year")),
                                    objGame.getString("description"), objGame.getString("image_url"));

                            gameList.add(game);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    rvGames.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rvGames.setAdapter(new RvGamesAdapter(getApplicationContext(), gameList));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error: ", error.toString());
                }
        });

        queue.add(jsonArrayRequest);


        //Add Game
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddGameActivity.class));
                finish();
            }
        });


        //Delete Game




    }
}