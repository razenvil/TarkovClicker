package com.twocrown.tarkovclicker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

public class DPSOperativnikAdapterUpdate extends ArrayAdapter<operativnik> {


    public DPSOperativnikAdapterUpdate(Context context, operativnik[] arr , PlayerEntity playerEntity) {
        super(context, R.layout.dps_layout , arr);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {
        final operativnik month = getItem(position);

        PlayerEntity playerEntity1 = ((MainActivity) getContext()).playerEntity1;

        TextView money = ((MainActivity) getContext()).money;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dps_layout, null);
        }
        ((TextView) convertView.findViewById(R.id.textView11)).setText(month.month);
        ((TextView) convertView.findViewById(R.id.priceTextView1)).setText("Цена:" + month.price);
        Button buy = convertView.findViewById(R.id.button11);

        if(month.month == "Прапор"){
            ((ImageView) convertView.findViewById(R.id.imageView211)).setBackgroundResource(R.drawable.prapor);
        }
        if(month.month == "Дилер"){
            ((ImageView) convertView.findViewById(R.id.imageView211)).setBackgroundResource(R.drawable.dealer);
        }
        if(month.month == "Лыжник"){
            ((ImageView) convertView.findViewById(R.id.imageView211)).setBackgroundResource(R.drawable.lizhnikmain);
        }
        if(month.month == "Миротворец"){
            ((ImageView) convertView.findViewById(R.id.imageView211)).setBackgroundResource(R.drawable.peacekeeper);
        }
        if(month.month == "Охотник"){
            ((ImageView) convertView.findViewById(R.id.imageView211)).setBackgroundResource(R.drawable.hunter);
        }
        View finalConvertView = convertView;
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(month.alreadybuyed){
                    if(playerEntity1.getMoney() > month.price || playerEntity1.getMoney() == month.price){
                        playerEntity1.setMoney(playerEntity1.getMoney() - month.price);
                        playerEntity1.setDps(playerEntity1.getDps() + month.dmg);
                        ((TextView) finalConvertView.findViewById(R.id.priceTextView1)).setText("Цена:" + month.price);
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
