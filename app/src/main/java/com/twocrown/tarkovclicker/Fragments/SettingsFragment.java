package com.twocrown.tarkovclicker.Fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.twocrown.tarkovclicker.MainActivity;
import com.twocrown.tarkovclicker.R;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        /*return super.onCreateView(inflater, container, savedInstanceState);*/
        View rootView =
                inflater.inflate(R.layout.setting_layout, container, false);
        FragmentManager fm = getFragmentManager();
        Button btn = (Button) rootView.findViewById(R.id.buttonQuit);
        Button btnSoundOff = (Button) rootView.findViewById(R.id.volumeDown);
        Button btnSoundUp = (Button) rootView.findViewById(R.id.volumeUp);
        SettingsFragment settingsFragment = ((MainActivity) getContext()).settingsFragment;
        Button shopButton = ((MainActivity)getContext()).shopButton;
        Button upgradeShopButton = ((MainActivity)getContext()).upgradeShopButton;
        Button settingsButton = ((MainActivity)getContext()).settingsButton;
        Button operShop = ((MainActivity)getContext()).operShop;
        Button gunShop = ((MainActivity)getContext()).gunShop;
        shopButton.setVisibility(View.INVISIBLE);
        upgradeShopButton.setVisibility(View.INVISIBLE);
        settingsButton.setVisibility(View.INVISIBLE);
        operShop.setVisibility(View.INVISIBLE);
        gunShop.setVisibility(View.INVISIBLE);
        MediaPlayer mediaPlayer = ((MainActivity)getContext()).mp;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().remove(settingsFragment).commit();
                Fragment oldFragment=getFragmentManager().findFragmentById(R.id.cs);
                shopButton.setVisibility(View.VISIBLE);
                upgradeShopButton.setVisibility(View.VISIBLE);
                settingsButton.setVisibility(View.VISIBLE);
                operShop.setVisibility(View.VISIBLE);
                gunShop.setVisibility(View.VISIBLE);
                if(oldFragment!=null)
                {
                    getFragmentManager()
                            .beginTransaction().
                            remove(oldFragment).commit();

                }
            }
        });
        btnSoundOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.setVolume(0 , 0);
            }
        });
        btnSoundUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.setVolume(1 , 1);
            }
        });
        return rootView;
    }

}

