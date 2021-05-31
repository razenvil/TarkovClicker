package com.twocrown.tarkovclicker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.twocrown.tarkovclicker.MainActivity;
import com.twocrown.tarkovclicker.Models.PlayerEntity;
import com.twocrown.tarkovclicker.Models.operativnik;
import com.twocrown.tarkovclicker.R;

public class operativnikiUpdateAdapter extends ArrayAdapter<operativnik> {


    public operativnikiUpdateAdapter(Context context, operativnik[] arr , PlayerEntity playerEntity) {
        super(context, R.layout.opertaivniki_adapter, arr);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {

        final operativnik month = getItem(position);

        Intent getPerson = new Intent();

        PlayerEntity playerEntity1 = ((MainActivity)getContext()).playerEntity1;

        TextView money = ((MainActivity) getContext()).money;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.opertaivniki_adapter, null);
        }

        ((TextView) convertView.findViewById(R.id.textView)).setText(month.name);
        ((TextView) convertView.findViewById(R.id.priceTextView)).setText("Цена:" + month.price);
        Button buy = convertView.findViewById(R.id.button);

        if(month.name == "Glock-18"){
            ((ImageView) convertView.findViewById(R.id.imageView2)).setBackgroundResource(R.drawable.glock18);
        }
        if(month.name == "P228"){
            ((ImageView) convertView.findViewById(R.id.imageView2)).setBackgroundResource(R.drawable.p228);
        }
        if(month.name == "MP9"){
            ((ImageView) convertView.findViewById(R.id.imageView2)).setBackgroundResource(R.drawable.mp9);
        }
        if(month.name == "MP5K"){
            ((ImageView) convertView.findViewById(R.id.imageView2)).setBackgroundResource(R.drawable.mp5k);
        }
        if(month.name == "AK74"){
            ((ImageView) convertView.findViewById(R.id.imageView2)).setBackgroundResource(R.drawable.ak74new);
        }

        View finalConvertView = convertView;
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(month.alreadybuyed){
                    if(playerEntity1.getMoney() > month.price || playerEntity1.getMoney() == month.price){
                        playerEntity1.setMoney(playerEntity1.getMoney() - month.price);
                        playerEntity1.setDmg(playerEntity1.getDmg() + month.dmg);
                        ((TextView) finalConvertView.findViewById(R.id.priceTextView)).setText("Цена:" + month.price);
                        month.upgrade(month.price , month);
                    }
                    /*money.setText(playerEntity1.getMoney());*/
                }
            }
        });

        /*((ImageView) convertView.findViewById(R.id.imageView2)).setImageResource(R.drawable.ramkaandroid);*/
        return convertView;



    }
}
