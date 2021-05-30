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

public class operativnikiAdapter extends ArrayAdapter<operativnik> {


    public operativnikiAdapter(Activity context, operativnik[] arr , PlayerEntity playerEntity) {
        super(context, R.layout.opertaivniki_adapter, arr);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {

        final operativnik month = getItem(position);

        PlayerEntity playerEntity11 = ((MainActivity) getContext()).playerEntity1;

        TextView money = ((MainActivity) getContext()).money;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.opertaivniki_adapter, null);
        }

// Заполняем адаптер
        ((TextView) convertView.findViewById(R.id.textView)).setText(month.month);
        ((TextView) convertView.findViewById(R.id.priceTextView)).setText("Цена:" + month.price);
        Button buy = convertView.findViewById(R.id.button);

        if(month.month == "Glock-18"){
            ((ImageView) convertView.findViewById(R.id.imageView2)).setBackgroundResource(R.drawable.glock18);
        }
        if(month.month == "P228"){
            ((ImageView) convertView.findViewById(R.id.imageView2)).setBackgroundResource(R.drawable.p228);
        }
        if(month.month == "MP9"){
            ((ImageView) convertView.findViewById(R.id.imageView2)).setBackgroundResource(R.drawable.mp9);
        }
        if(month.month == "MP5K"){
            ((ImageView) convertView.findViewById(R.id.imageView2)).setBackgroundResource(R.drawable.mp5k);
        }
        if(month.month == "AK74"){
            ((ImageView) convertView.findViewById(R.id.imageView2)).setBackgroundResource(R.drawable.ak74new);
        }
        View finalConvertView = convertView;
        if(month.alreadybuyed){
            ((TextView) finalConvertView.findViewById(R.id.priceTextView)).setText("Куплено");
        }

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!month.alreadybuyed) {
                    if (playerEntity11.getMoney() > month.price || playerEntity11.money == month.price) {
                        playerEntity11.setMoney(playerEntity11.getMoney() - month.price);
                        playerEntity11.setDmg(playerEntity11.getDmg() + month.dmg);
                        money.setText(playerEntity11.getMoney().toString());
                        month.alreadybuyed = true;
                        ((TextView) finalConvertView.findViewById(R.id.priceTextView)).setText("Куплено");
                        /*money.setText(playerEntity1.getMoney());*/
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
