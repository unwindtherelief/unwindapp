package com.depression.relief.depressionissues.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.models.AffermationDataModel;

import java.util.ArrayList;

public class AffermationDeckAdapter extends BaseAdapter {

    private ArrayList<AffermationDataModel> courseData;
    private Context context;

    // on below line we have created constructor for our variables.
    public AffermationDeckAdapter(ArrayList<AffermationDataModel> courseData, Context context) {
        this.courseData = courseData;
        this.context = context;
    }

    @Override
    public int getCount() {
        // in get count method we are returning the size of our array list.
        return courseData.size();
    }

    @Override
    public Object getItem(int position) {
        // in get item method we are returning the item from our array list.
        return courseData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // in get item id we are returning the position.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.affermation_card_item, parent, false);
        }


        ((TextView) v.findViewById(R.id.txt_quote)).setText(courseData.get(position).getAffermationThoughts());
        v.findViewById(R.id.shape_img).setBackgroundResource(courseData.get(position).getBackColor());
        return v;
    }
}
