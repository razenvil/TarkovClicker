package com.twocrown.tarkovclicker.Models;

import android.util.Log;
import android.widget.ImageView;

import com.twocrown.tarkovclicker.Fragments.SettingsFragment;
import com.twocrown.tarkovclicker.MainActivity;
import com.twocrown.tarkovclicker.R;

import static java.security.AccessController.getContext;

public class Enemy extends MainActivity {
    public Integer hp = 10;
    public Integer tmpHp = hp;
    public Integer moneygain = 1;
    public Integer lvl = 1;
    public Integer podLvl = 1;
    public static final double PERCENT_CLICKER = 1.75;
    public static final double PERCENT_CLICKER_MONEY = 1.3;


    public void upgradeHp(Integer i, Enemy enemy) {
        Double iTmp = 1.0 * i;
        iTmp = iTmp * PERCENT_CLICKER;
        Integer i1Tmp = iTmp.intValue();
        i = i1Tmp;
        enemy.hp = i;
        enemy.tmpHp = i;
    }
    public void upgradeMoneyGAIN(Integer i, Enemy enemy) {
        Double iTmp = 1.0 * i;
        iTmp = iTmp * PERCENT_CLICKER_MONEY;
        Integer i1Tmp = iTmp.intValue();
        i = i1Tmp;
        enemy.moneygain += i;
    }

    public Enemy() {
        this.hp = hp;
        this.tmpHp = tmpHp;
        this.moneygain = moneygain;
        this.lvl = lvl;
        this.podLvl = podLvl;
    }

    public void dmgtoEnemy(Enemy enemy, PlayerEntity playerEntity) {
        enemy.hp = enemy.hp - playerEntity.dmg;
    }

    public Integer getHp() {
        return hp;
    }

    public Integer getTmpHp() {
        return tmpHp;
    }

    public Integer getMoneygain() {
        return moneygain;
    }

    public Integer getLvl() {
        return lvl;
    }

    public Integer getPodLvl() {
        return podLvl;
    }

    public void reset(Enemy enemy1, PlayerEntity playerEntity1 , Boolean boolean1 , ImageView enemyPic) {
        if (enemy1.hp < 0 && !boolean1 || enemy1.hp == 0 && !boolean1) {
            enemyPic.setImageResource(0);
            enemy1.podLvl += 1;
            enemy1.hp = enemy1.tmpHp;
            playerEntity1.money = playerEntity1.money + enemy1.moneygain;
        }/*else if(enemy1.hp < 0 || enemy1.hp == 0 && boolean1){
                enemy1.lvl += 1;
                enemy1.upgradeHp(enemy1.tmpHp , enemy1);
                enemy1.hp = enemy1.tmpHp;
                playerEntity1.money = playerEntity1.money + enemy1.moneygain;
                enemy1.upgradeMoneyGAIN(enemy1.moneygain , enemy1);
            }*/
    }
    public void instantBossUpgrade(Enemy enemy1, PlayerEntity playerEntity1){
        enemy1.lvl += 1;
        playerEntity1.money = playerEntity1.money + enemy1.moneygain;
        enemy1.upgradeMoneyGAIN(enemy1.moneygain , enemy1);
        enemy1.upgradeHp(enemy1.tmpHp , enemy1);
        enemy1.podLvl = 1;
        Log.d("MYBUG" , "UPRADED");
    }
}