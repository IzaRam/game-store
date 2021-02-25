package com.izram.gamestoreapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

public class RvGamesAdapter extends RecyclerView.Adapter<RvGamesAdapter.MyViewHolder> {

    private ArrayList<Game> gamesList;
    private Context context;

    public RvGamesAdapter(Context context, ArrayList<Game> gamesList) {
        this.gamesList = gamesList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        new DownloadImageTask(holder.ivGame)
                .execute(gamesList.get(position).getImage_Url());
        holder.tvGameTitle.setText(gamesList.get(position).getName());
        holder.tvGameYear.setText(gamesList.get(position).getYear());

        holder.cvGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Delete games by id
                RequestQueue queue = Volley.newRequestQueue(context);
                String url = "http://192.168.0.104:8080/api/";

                StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                        url+gamesList.get(position).getId(), new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

                                gamesList.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                queue.add(stringRequest);

            }
        });
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivGame;
        TextView tvGameTitle;
        TextView tvGameYear;
        CardView cvGame;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivGame = itemView.findViewById(R.id.iv_cd_game);
            tvGameTitle = itemView.findViewById(R.id.tv_cd_title);
            tvGameYear = itemView.findViewById(R.id.tv_cd_year);
            cvGame = itemView.findViewById(R.id.cv_game);
        }
    }


    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
