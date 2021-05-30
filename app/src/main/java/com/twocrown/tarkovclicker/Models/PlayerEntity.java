package com.twocrown.tarkovclicker.Models;

import java.io.Serializable;

public class PlayerEntity implements Serializable {
    public Integer money = 0;
    public Integer dmg = 1;
    public Integer dps = 0;

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void setDmg(Integer dmg) {
        this.dmg = dmg;
    }

    public void setDps(Integer dps) {
        this.dps = dps;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getDmg() {
        return dmg;
    }

    public Integer getDps() {
        return dps;
    }

    public PlayerEntity() {
        this.money = money;
        this.dmg = dmg;
        this.dps = dps;
    }
}
