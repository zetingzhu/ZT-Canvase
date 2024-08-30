package com.zzt.samplecanvas.act;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zzt.samplecanvas.R;

/**
 * @author: zeting
 * @date: 2024/4/22
 * 自定义视图包含RecycleView
 */
public class CustomViewVpInRvAct extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_vp_in_rv);
    }
}
