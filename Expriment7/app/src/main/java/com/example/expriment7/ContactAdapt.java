package com.example.expriment7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactAdapt extends ArrayAdapter<Contacts> {

    private int resourceId;
    public ContactAdapt(@NonNull Context context, int resource, @NonNull List<Contacts> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contacts contacts =getItem(position);

        View view;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);

        }else {
            view = convertView;
        }

        TextView displayname =view.findViewById(R.id.displayname);
        TextView number = view.findViewById(R.id.number);

        displayname.setText(contacts.getDisplayname());
        number.setText(contacts.getNumber());

        return view;
    }
}
