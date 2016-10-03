package com.example.administrator.maintab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.administrator.maintab.adapter.Bean;
import com.example.administrator.maintab.adapter.MyAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/7.
 */

public class BestListViewActivity extends Activity {
    private ListView lv;
    private ArrayList<Bean> datas;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_best_listview);
        initData();
        setViews();
        setListeners();
    }

    private void initData() {
        datas=new ArrayList<Bean>();
        for (int i = 0; i < 100; i++) {
            Bean b=new Bean();
            b.img=R.mipmap.ic_launcher;
            b.tag=""+i;
            datas.add(b);
        }
    }

    private void setListeners() {

    }

    private void setViews() {
        lv=(ListView)findViewById(R.id.listView);
        adapter=new MyAdapter(this,datas);
        lv.setAdapter(adapter);
    }

}
