package com.zzt.samplecanvas.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.zzt.adapter.StartActivityRecyclerAdapter;
import com.zzt.entity.StartActivityDao;
import com.zzt.samplecanvas.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initView();
        initData();
    }

    private void initData() {
        List<StartActivityDao> mList = new ArrayList<>();
        mList.add(new StartActivityDao("自定义画图", "里面包含很多点，线，图形绘制", "1"));
        mList.add(new StartActivityDao("自定义画图 2", " ", "2"));

        StartActivityRecyclerAdapter.setAdapterData(rv_list, RecyclerView.VERTICAL, mList, new StartActivityRecyclerAdapter.OnItemClickListener<StartActivityDao>() {
            @Override
            public void onItemClick(View itemView, int position, StartActivityDao data) {
                switch (data.getArouter()) {
                    case "1":
                        DrawViewAct.start(getBaseContext());
                        break;
                    case "2":
                        ActDraw2.start(MainActivity.this);
                        break;
                }
            }
        });
    }

    private void initView() {
        rv_list = findViewById(R.id.rv_list);
    }
}