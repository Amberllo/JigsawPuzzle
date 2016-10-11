package com.puzzle.jigsaw;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    GameView gameView;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.setBitmap(bitmap);
            }
        });
        gameView = (GameView)findViewById(R.id.gameView);
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.img);
        gameView.setBitmap(bitmap);
    }
}
