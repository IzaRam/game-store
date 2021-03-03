package com.izram.gamestoreapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.izram.gamestoreapp.R;
import com.izram.gamestoreapp.adapter.RvGamesAdapter;
import com.izram.gamestoreapp.helper.RecyclerItemClickListener;
import com.izram.gamestoreapp.model.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Game> gameList;
    private RvGamesAdapter rvGamesAdapter;
    private RecyclerView rvGames;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvGames = findViewById(R.id.rv_games);
        gameList = new ArrayList<>();


        //List all games
        listAllGames();


        //Search Game
        searchView = findViewById(R.id.searchView);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                rvGamesAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rvGamesAdapter.getFilter().filter(newText);
                return false;
            }
        });


        //Game Action (View, Edit or Delete)
        rvGames.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvGames, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                // View Game
                Intent intent = new Intent(new Intent(getApplicationContext(), ViewGameActivity.class));
                intent.putExtra("GAME", gameList.get(position));
                startActivity(intent);

            }

            @Override
            public void onLongItemClick(View view, int position) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Edit or Delete Game");
                alertBuilder.setMessage("Choose an action");

                //Edit Game
                alertBuilder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), AddGameActivity.class);
                        intent.putExtra("ACTION", 0);
                        intent.putExtra("GAME", gameList.get(position));
                        startActivity(intent);
                        finish();
                    }
                });

                //Delete game
                alertBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteGameById(position);
                    }
                });

                alertBuilder.create();
                alertBuilder.show();


            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));


        //Add Game
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(getApplicationContext(), AddGameActivity.class));
                intent.putExtra("ACTION", 1);
                startActivity(intent);
                finish();
            }
        });


    }


    private void listAllGames() {
        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://10.0.2.2:8080/api/games";
        String url = "http://192.168.0.104:8080/api/games";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
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
                        rvGamesAdapter = new RvGamesAdapter(getApplicationContext(), gameList);
                        rvGames.setAdapter(rvGamesAdapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error: ", error.toString());
                    }
                });

        queue.add(jsonArrayRequest);
    }

    private void deleteGameById(int position) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.0.104:8080/api/";
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                url + gameList.get(position).getId(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                gameList.remove(position);
                gameList.clear();
                listAllGames();
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();

                searchView.clearFocus();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }


}