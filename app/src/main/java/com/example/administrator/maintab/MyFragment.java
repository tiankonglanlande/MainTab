package com.example.administrator.maintab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.maintab.adapter.Bean;
import com.example.administrator.maintab.adapter.MyAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/17.
 * 每个子界面
 */
public class MyFragment extends Fragment {
    private ListView lv;
    private ArrayList<Bean> datas;
    private MyAdapter adapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inner, container, false);
        initData();
        setViews();
        setListeners();
        return view;
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
        lv=(ListView)view.findViewById(R.id.listView);
        adapter=new MyAdapter(getActivity(),datas);
        lv.setAdapter(adapter);
    }
}
