package com.example.administrator.maintab.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.maintab.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/7.
 */
public class MyAdapter extends BaseAdapter{
    private final ArrayList<Bean> datas;
    private final Context context;

    public MyAdapter(Context context, ArrayList<Bean> datas) {
        this.datas=datas;
        this.context=context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder=null;
        if (view == null) {
            holder=new Holder();
            view=View.inflate(context, R.layout.item_best_listview,null);
            holder.img= (ImageView) view.findViewById(R.id.img);
            holder.img.setTag(i);
            view.setTag(holder);
        }else{
            holder= (Holder) view.getTag();
        }
        final Bean bean=datas.get(i);
        holder.img.setTag(bean);
        final ImageView iv =holder.img;

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                    iv.post(new Runnable() {
                        @Override
                        public void run() {
                            Bean bb= (Bean) iv.getTag();
                            iv.setImageResource(bb.img);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        return view;
    }

    class Holder{
        ImageView img;

    }
}
