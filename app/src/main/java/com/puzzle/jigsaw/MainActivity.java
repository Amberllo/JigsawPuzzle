package com.puzzle.jigsaw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;

public class MainActivity extends AppCompatActivity {

    int REQUEST_CODE_PICK_IMAGE = 1024;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_select)
    protected void getImageFromAlbum() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");//相片类型
//        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

        // Multi image selector form an Activity
        MultiImageSelector.create(this)
                .single()
                .start(this, REQUEST_CODE_PICK_IMAGE);
    }

    /**
     * 设置游戏难度
     * */
    @OnClick(R.id.btn_mode)
    protected void setMode() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);
        Preference<String> username = rxPreferences.getString("username");
        Preference<Boolean> showWhatsNew = rxPreferences.getBoolean("show-whats-new", true);
    }

}
