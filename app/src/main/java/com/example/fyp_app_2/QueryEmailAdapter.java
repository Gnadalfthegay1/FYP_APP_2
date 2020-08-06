package com.example.fyp_app_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class QueryEmailAdapter extends ArrayAdapter<Query> {
    Context c;
    int res;
    ArrayList<Query> q;
    TextView tvEmail;
    ImageView iv;
    public QueryEmailAdapter(Context c, int res, ArrayList<Query> q) {
        super(c, res, q);
        this.c = c;
        this.res = res;
        this.q = q;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        view = LayoutInflater.from(c).inflate(res, parent, false);
        Query q1 = q.get(position);
        tvEmail = view.findViewById(R.id.tvEmail);
        iv = view.findViewById(R.id.imageView);
        iv.setImageResource(R.drawable.mail);
        tvEmail.setText(q1.getEmail());
        return view;
    }
}
