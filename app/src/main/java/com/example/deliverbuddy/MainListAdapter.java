package com.example.deliverbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.Locale;

public class MainListAdapter extends ArrayAdapter<Delivery>
{
    private Delivery[] deliv;
    private Context context;

    //Organization for child views
    private static class ViewHolder
    {
        TextView viewPhone;
        TextView viewAddress;
        TextView viewTotal;
        TextView viewNo;
    }

    //The exact way in which a custom adapter functions is something I still don't completely understand
    //I know what the end result is, but the parameters in these functions and where they come from is something I still need to learn
    //I found several templates for custom adapters and managed to create an adapter for what I needed by trial and error
    MainListAdapter(Delivery[] data, Context context)
    {
        super(context, R.layout.row_item, data);
        this.deliv = data;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Delivery deliv = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.viewPhone = convertView.findViewById(R.id.main_phone);
            viewHolder.viewAddress = convertView.findViewById(R.id.main_address);
            viewHolder.viewTotal = convertView.findViewById(R.id.main_total);
            viewHolder.viewNo = convertView.findViewById(R.id.main_no);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Adds a "$" before order total and makes it always have 2 decimal places
        String temp = "$" + String.format(Locale.ENGLISH,"%1$,.2f", deliv.getTotal());
        viewHolder.viewPhone.setText(deliv.getPhone());
        viewHolder.viewAddress.setText(deliv.getAddress1());
        viewHolder.viewTotal.setText(temp);
        viewHolder.viewNo.setText(deliv.getNo());

        return convertView;
    }
}
