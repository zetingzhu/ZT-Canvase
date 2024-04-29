package com.zzt.samplecanvas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author: zeting
 * @date: 2024/4/22
 */
public class DrawViewAct extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, DrawViewAct.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
