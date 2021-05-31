package com.twocrown.tarkovclicker.Models;

public class operativnik {
    public static final double PERCENT_CLICKER = 1.35;

    public String name = ""; // Название месяца
    public Integer price = 0; // Средняя температура
    public int lvl = 0; // Количество дней
    public int dmg = 0;
    public boolean alreadybuyed = false; // Нравится месяц

    public void upgrade(Integer i, operativnik month) {
        Double iTmp = 1.0 * i;
        iTmp = iTmp * PERCENT_CLICKER;
        Integer i1Tmp = iTmp.intValue();
        i = i1Tmp;
        month.price = i;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public void setAlreadybuyed(boolean alreadybuyed) {
        this.alreadybuyed = alreadybuyed;
    }
}