package com.izram.gamestoreapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.izram.gamestoreapp.R;
import com.izram.gamestoreapp.helper.DownloadImageTask;
import com.izram.gamestoreapp.model.Game;

public class ViewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game);

        Game game = (Game) getIntent().getSerializableExtra("GAME");

        ImageView imageViewGame = findViewById(R.id.iv_game);
        TextView textViewTitle = findViewById(R.id.tv_game_title);
        TextView textViewYear = findViewById(R.id.tv_game_year);
        TextView textViewDescription = findViewById(R.id.tv_game_description);

        new DownloadImageTask(imageViewGame)
                .execute(game.getImage_Url());

        textViewTitle.setText(game.getName());
        textViewYear.setText(game.getYear());
        textViewDescription.setText(game.getDescription());




    }
}