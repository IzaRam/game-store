package com.izram.gamestoreapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.izram.gamestoreapp.helper.DownloadImageTask;
import com.izram.gamestoreapp.model.Game;
import com.izram.gamestoreapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RvGamesAdapter extends RecyclerView.Adapter<RvGamesAdapter.MyViewHolder> {

    private ArrayList<Game> gamesList;
    private ArrayList<Game> gamesListFull;
    private Context context;

    public RvGamesAdapter(Context context, ArrayList<Game> gamesList) {
        this.gamesList = gamesList;
        this.gamesListFull = new ArrayList<>(gamesList);
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

    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    //Filter Results on RecyclerView
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<Game> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(gamesListFull);
            } else {
                gamesList.clear();
                gamesList.addAll(gamesListFull);
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Game item : gamesList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            gamesList.clear();
            gamesList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


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


}
