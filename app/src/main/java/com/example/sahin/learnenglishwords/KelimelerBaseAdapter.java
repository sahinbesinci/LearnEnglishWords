package com.example.sahin.learnenglishwords;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sahin on 21.10.2016.
 */

public class KelimelerBaseAdapter extends BaseAdapter implements Filterable {
    private ArrayList<Kelime> myList = new ArrayList();
    private ArrayList<Kelime> filterList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;


    public KelimelerBaseAdapter(Context context, ArrayList<Kelime> myList) {
        this.filterList = myList;
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return filterList != null ? filterList.size():0;
    }

    @Override
    public Kelime getItem(int position) {
        return filterList.get(position);
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

        Kelime currentListData = filterList.get(position);

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
    @Override
    public Filter getFilter() {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                FilterResults results = new FilterResults();

                if(charSequence == null || charSequence.length() == 0)
                {
                    results.values = myList;
                    results.count = myList.size();
                }
                else
                {
                    ArrayList<Kelime> filterResultsData = new ArrayList<>();

                    for(Kelime data : myList)
                    {
                        if (data.getIngilizce().length() >= charSequence.length())
                        {
                            if(data.getIngilizce().substring(0,charSequence.length()).equals(charSequence.toString()))
                            {
                                filterResultsData.add(data);
                            }
                        }
                    }
                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                filterList = (ArrayList<Kelime>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
