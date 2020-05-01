package com.example.covid_19visualizer.info;

//created by Federico Coppo
//created to tell the adapter what type of data to display

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.covid_19visualizer.R;

import java.util.ArrayList;
import java.util.List;

public class InfoAdapter extends ArrayAdapter<xmlinfo> {

    private Context mContext;
    private List<xmlinfo> xmlinfoList = new ArrayList<>();


    public InfoAdapter(@NonNull Context context, ArrayList<xmlinfo> list)

    {
        super(context, 0, list);
        mContext = context;
        xmlinfoList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertview, @NonNull ViewGroup parent) {
        View listItem = convertview;
        if (listItem == null)

            listItem = LayoutInflater.from(mContext).inflate(R.layout.info_list_item, parent, false);


        xmlinfo currentxmlinfo = xmlinfoList.get(position);

        TextView headline = (TextView) listItem.findViewById(R.id.textView_headline);
        headline.setText(currentxmlinfo.getheadlines());

        TextView description = (TextView) listItem.findViewById(R.id.textView_description);
        description.setText(currentxmlinfo.getdescriptions());

        return listItem;
    }
}
