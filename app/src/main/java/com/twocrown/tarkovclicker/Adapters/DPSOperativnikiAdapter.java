package com.twocrown.tarkovclicker.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.twocrown.tarkovclicker.MainActivity;
import com.twocrown.tarkovclicker.Models.PlayerEntity;
import com.twocrown.tarkovclicker.Models.operativnik;
import com.twocrown.tarkovclicker.R;

public class DPSOperativnikiAdapter extends ArrayAdapter<operativnik> {

    public DPSOperativnikiAdapter(Activity context, operativnik[] arr , PlayerEntity playerEntity) {
        super(context, R.layout.dps_layout, arr);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {

        final operativnik month = getItem(position);

        PlayerEntity playerEntity11 = ((MainActivity) getContext()).playerEntity1;

        TextView money = ((MainActivity) getContext()).money;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dps_layout, null);
        }

// Заполняем адаптер
        ((TextView) convertView.findViewById(R.id.textView11)).setText(month.name);
        ((TextView) convertView.findViewById(R.id.priceTextView1)).setText("Цена:" + month.price);
        Button buy = convertView.findViewById(R.id.button11);

        if(month.name == "Прапор"){
            ((ImageView) convertView.findViewById(R.id.imageView211)).setBackgroundResource(R.drawable.prapor);
        }
        if(month.name == "Дилер"){
            ((ImageView) convertView.findViewById(R.id.imageView211)).setBackgroundResource(R.drawable.dealer);
        }
        if(month.name == "Лыжник"){
            ((ImageView) convertView.findViewById(R.id.imageView211)).setBackgroundResource(R.drawable.lizhnikmain);
        }
        if(month.name == "Миротворец"){
            ((ImageView) convertView.findViewById(R.id.imageView211)).setBackgroundResource(R.drawable.peacekeeper);
        }
        if(month.name == "Охотник"){
            ((ImageView) convertView.findViewById(R.id.imageView211)).setBackgroundResource(R.drawable.hunter);
        }
        View finalConvertView = convertView;
        if(month.alreadybuyed){
            ((TextView) finalConvertView.findViewById(R.id.priceTextView1)).setText("Куплено");
        }

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!month.alreadybuyed) {
                    if (playerEntity11.getMoney() > month.price || playerEntity11.money == month.price) {
                        playerEntity11.setMoney(playerEntity11.getMoney() - month.price);
                        playerEntity11.setDps(playerEntity11.getDps() + month.dmg);
                        money.setText(playerEntity11.getMoney().toString());
                        month.alreadybuyed = true;
                        ((TextView) finalConvertView.findViewById(R.id.priceTextView1)).setText("Куплено");
                    }
                }else{
                    Toast.makeText(getContext(), "You already buy this one" , Toast.LENGTH_LONG).show();
                }
            }
        });
// Выбираем картинку для месяца
        /*((ImageView) convertView.findViewById(R.id.imageView2)).setImageResource(R.drawable.ramkaandroid);*/
        return convertView;



    }
}
