package com.example.sahin.learnenglishwords;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sahin on 21.10.2016.
 */

public class KelimelerBaseAdapter extends BaseAdapter {
    ArrayList<Kelime> myList = new ArrayList();
    LayoutInflater inflater;
    Context context;


    public KelimelerBaseAdapter(Context context, ArrayList<Kelime> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Kelime getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.kelimeler_item_layout, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        Kelime currentListData = getItem(position);

        mViewHolder.tvIng.setText(currentListData.getIngilizce());
        mViewHolder.tvTr.setText(currentListData.getTurkce());
        mViewHolder.tvSayi.setText("Doğru: " +currentListData.getDogru() + "\nYanlış: " + currentListData.getYanlis());

        return convertView;
    }

    private class MyViewHolder {
        TextView tvIng, tvTr, tvSayi;

        public MyViewHolder(View item) {
            tvIng = (TextView) item.findViewById(R.id.tvIng);
            tvTr = (TextView) item.findViewById(R.id.tvTr);
            tvSayi = (TextView) item.findViewById(R.id.tvSayi);
        }
    }
}
