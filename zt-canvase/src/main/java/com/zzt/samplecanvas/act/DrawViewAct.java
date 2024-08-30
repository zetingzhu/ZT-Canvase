package com.zzt.samplecanvas.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zzt.samplecanvas.R;

/**
 * @author: zeting
 * @date: 2024/4/22
 */
public class DrawViewAct extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, DrawViewAct.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
