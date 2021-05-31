package com.twocrown.tarkovclicker;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.twocrown.tarkovclicker.Adapters.DPSOperativnikAdapterUpdate;
import com.twocrown.tarkovclicker.Adapters.DPSOperativnikiAdapter;
import com.twocrown.tarkovclicker.Adapters.operativnikiAdapter;
import com.twocrown.tarkovclicker.Adapters.operativnikiUpdateAdapter;
import com.twocrown.tarkovclicker.Fragments.SettingsFragment;
import com.twocrown.tarkovclicker.Models.Enemy;
import com.twocrown.tarkovclicker.Models.PlayerEntity;
import com.twocrown.tarkovclicker.Models.operativnik;
import com.twocrown.tarkovclicker.db.progressDB;

public class MainActivity extends AppCompatActivity {


    public TextView hp, lvl, money, stageOfEnemy, lvlText ,bossText,mTimer;
    public ImageView clicker , enemyPic;
    public PlayerEntity playerEntity1;
    public Enemy enemy1;
    public ProgressBar hpBar;
    public Button shopButton, upgradeShopButton , settingsButton , operShop , gunShop;
    //Boolean-переменная для того что бы открывался нужный адаптер на одну и ту же кнопку
    private Boolean perexod = false;
    //Переменная для движения по массиву
    private Integer bossStage = 1;
    //Переменная для движения по массиву
    private Integer saveLvl = 2;
    private Boolean currentBoss = false;
    private Thread bossThread;
    private CountDownTimer bossTime;
    private Boolean timerCreated = false;
    public MediaPlayer clickSound , mp;
    private com.twocrown.tarkovclicker.db.progressDB progressDB;
    public SettingsFragment settingsFragment;
    public FrameLayout cs;
    public Integer DamageAtAalll;
    public operativnik[] operativniki , characters;
    public static final String APP_PREFERENCES = "mysettings";


    operativnik[] createOperativniki(String string1 , String string2 , String string3, String string4 ,String string5) {
        operativnik[] arr = new operativnik[5];


// Названия месяцев
        String[] nameArr = {string1, string2, string3, string4, string5};
// Среднесуточная температура
        Integer[] priceArr = {10, 100, 1000, 10000, 100000};

        Integer[] dmgArr = {1, 10, 100, 1000, 10000};
// Количество дней
        int[] lvlArr = {1, 1, 1, 1, 1};

        for (int i = 0; i < arr.length; i++) {
            operativnik month = new operativnik();
            month.month = nameArr[i];
            month.price = priceArr[i];
            month.lvl = lvlArr[i];
            month.dmg = dmgArr[i];
            arr[i] = month;
        }
        return arr;
    }
    public void createBossArray(Integer[] bossArray) {
        for (int i = 0; i < bossArray.length; i = i + 1) {
            bossArray[i] = i * 10;
        }
    }

    public void createSavePersonArray(Integer[] bossArray) {
        for (int i = 1; i < bossArray.length; i = i + 1) {
            bossArray[i] = (i * 10) - 1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pixel4xl_layout);

        playerEntity1 = new PlayerEntity();
        enemy1 = new Enemy();
        Enemy perviousEnemy = new Enemy();
        operativniki = createOperativniki("Glock-18", "P228", "MP9", "MP5K", "AK74");
        characters = createOperativniki("Прапор" , "Дилер" , "Лыжник" ,"Миротворец" , "Охотник");


        operativnikiAdapter operativnikiAdapter = new operativnikiAdapter(this, operativniki, playerEntity1);
        operativnikiUpdateAdapter operativnikiUpdateAdapter = new operativnikiUpdateAdapter(this, operativniki, playerEntity1);
        DPSOperativnikiAdapter dpsOperativnikiAdapter = new DPSOperativnikiAdapter(this , characters , playerEntity1);
        DPSOperativnikAdapterUpdate dpsOperativnikAdapterUpdate = new DPSOperativnikAdapterUpdate(this , characters ,playerEntity1);
        ListView mainListView = (ListView) findViewById(R.id.listView);
        mainListView.setAdapter(operativnikiAdapter);


        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Integer[] bossLvl = new Integer[1000];
        createBossArray(bossLvl);
        Integer[] saveLvlPerson = new Integer[1000];
        createSavePersonArray(saveLvlPerson);


        //Инициализация
        hpBar = findViewById(R.id.progressBar);
        hp = findViewById(R.id.textView4);
        lvl = findViewById(R.id.textView3);
        money = findViewById(R.id.textView2);
        clicker = findViewById(R.id.imageView);
        shopButton = findViewById(R.id.shopButton);
        upgradeShopButton = findViewById(R.id.upgradeShopButton);
        lvlText = (TextView) findViewById(R.id.lvlText);
        enemyPic = findViewById(R.id.imageView3);
        settingsButton = findViewById(R.id.settingsButton);
        bossText = findViewById(R.id.bossText);
        mTimer = findViewById(R.id.mTimer);
        operShop = findViewById(R.id.operShop);
        gunShop = findViewById(R.id.gunsShop);

        //Инициализация

        progressDB = new progressDB(this);
        SQLiteDatabase db = progressDB.getWritableDatabase();
        Cursor cursor = db.query("progressDB" , null , null , null ,null ,null , null);
        if(cursor.moveToFirst()){
           enemy1.hp = cursor.getInt(cursor.getColumnIndex("EnemyHP"));
            enemy1.podLvl = cursor.getInt(cursor.getColumnIndex("EnemyPODLVL"));
            enemy1.lvl = cursor.getInt(cursor.getColumnIndex("EnemyLVL"));
            enemy1.moneygain = cursor.getInt(cursor.getColumnIndex("EnemyMG"));
            playerEntity1.dps = cursor.getInt(cursor.getColumnIndex("PEDPS"));
            playerEntity1.dmg = cursor.getInt(cursor.getColumnIndex("PEDPC"));
            playerEntity1.money = cursor.getInt(cursor.getColumnIndex("PEMONEY"));
/*            operativniki[0].setAlreadybuyed(cursor.getInt(cursor.getColumnIndex("ПрапорBUYED")) > 0);
            operativniki[1].setAlreadybuyed(cursor.getInt(cursor.getColumnIndex("ДилерBUYED")) > 0);
            operativniki[2].setAlreadybuyed(cursor.getInt(cursor.getColumnIndex("ЛыжникBUYED")) > 0);
            operativniki[3].setAlreadybuyed(cursor.getInt(cursor.getColumnIndex("МиротворецBUYED")) > 0);
            operativniki[4].setAlreadybuyed(cursor.getInt(cursor.getColumnIndex("ОхотникBUYED")) > 0);
            operativniki[0].dmg = cursor.getInt(cursor.getColumnIndex("PraporDMG"));
            operativniki[1].setDmg(cursor.getInt(cursor.getColumnIndex("ДилерDMG")));
            operativniki[2].setDmg(cursor.getInt(cursor.getColumnIndex("ЛыжникDMG")));
            operativniki[3].setDmg(cursor.getInt(cursor.getColumnIndex("МиротворецDMG")));
            operativniki[4].setDmg(cursor.getInt(cursor.getColumnIndex("ОхотникDMG")));
            operativniki[0].setPrice(cursor.getInt(cursor.getColumnIndex("ПрапорPRICE")));
            operativniki[1].setPrice(cursor.getInt(cursor.getColumnIndex("ДилерPRICE")));
            operativniki[2].setPrice(cursor.getInt(cursor.getColumnIndex("ЛыжникPRICE")));
            operativniki[3].setPrice(cursor.getInt(cursor.getColumnIndex("МиротворецPRICE")));
            operativniki[4].setPrice(cursor.getInt(cursor.getColumnIndex("Охотникprice")));
            characters[0].setAlreadybuyed(cursor.getInt(cursor.getColumnIndex("Glock-18BUYED")) > 0);
            characters[1].setAlreadybuyed(cursor.getInt(cursor.getColumnIndex("P228BUYED")) > 0);
            characters[2].setAlreadybuyed(cursor.getInt(cursor.getColumnIndex("MP9BUYED")) > 0);
            characters[3].setAlreadybuyed(cursor.getInt(cursor.getColumnIndex("MP5KBUYED")) > 0);
            characters[4].setAlreadybuyed(cursor.getInt(cursor.getColumnIndex("AK74BUYED")) > 0);
            characters[0].setDmg(cursor.getInt(cursor.getColumnIndex("Glock-18DMG")));
            characters[1].setDmg(cursor.getInt(cursor.getColumnIndex("P228DMG")));
            characters[2].setDmg(cursor.getInt(cursor.getColumnIndex("MP9DMG")));
            characters[3].setDmg(cursor.getInt(cursor.getColumnIndex("MP5KDMG")));
            characters[4].setDmg(cursor.getInt(cursor.getColumnIndex("AK74DMG")));
            characters[0].setPrice(cursor.getInt(cursor.getColumnIndex("Glock-18PRICE")));
            characters[1].setPrice(cursor.getInt(cursor.getColumnIndex("P228PRICE")));
            characters[2].setPrice(cursor.getInt(cursor.getColumnIndex("MP9PRICE")));
            characters[3].setPrice(cursor.getInt(cursor.getColumnIndex("MP5KPRICE")));
            characters[4].setPrice(cursor.getInt(cursor.getColumnIndex("AK74price")));*/
        }else{
            Log.d("MYBUG" , "0ROWS");
        }
        hp.setText(enemy1.hp.toString());
        /*lvl.setText(enemy1.lvl.toString());*/
        money.setText(playerEntity1.money.toString());

        mp = MediaPlayer.create(this, R.raw.songmain);


        clickSound = MediaPlayer.create(this, R.raw.clicksound);
        //Background loop music

        new CountDownTimer(Long.MAX_VALUE , 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                if(enemy1.getLvl() == bossLvl[bossStage]){
                    enemy1.reset(enemy1 , playerEntity1 , currentBoss , enemyPic);
                    Log.d("FATALERROR" , enemy1.getHp().toString());
                    new bossTimer(30000 , 1000).start();
                    currentBoss = true;
                    timerCreated = true;

                }
                if(enemy1.getLvl() > 10){
                    timerCreated = false;
                    mTimer.setText(null);
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

        new CountDownTimer(Long.MAX_VALUE, 100) {
            public void onTick(long millisUntilFinished) {
                if(enemy1.getHp() < 0){
                    hp.setText("0");
                }
                DamageAtAalll = playerEntity1.dmg + playerEntity1.dps;
                lvl.setText(DamageAtAalll.toString());
                lvlText.setText("Уровень : " +enemy1.getLvl().toString() + "\n" + "Cтадия : " + enemy1.getPodLvl());
                enemy1.reset(enemy1, playerEntity1, currentBoss , enemyPic);
                hpBar.setMax(enemy1.getTmpHp());
                hp.setText(enemy1.getHp().toString());
                hpBar.setProgress(enemy1.getHp());
                /*lvl.setText(enemy1.getLvl().toString());*/

                if (enemy1.podLvl == 10) {
                    enemy1.lvl += 1;
                    playerEntity1.money = playerEntity1.money + enemy1.moneygain;
                    enemy1.podLvl = 0;
                    /*lvl.setText(enemy1.lvl.toString());*/
                    enemy1.upgradeHp(enemy1.tmpHp, enemy1);
                    enemy1.hp = enemy1.tmpHp;
                    hpBar.setMax(enemy1.getHp());
                    enemy1.upgradeMoneyGAIN(enemy1.moneygain, enemy1);
                }
                money.setText(playerEntity1.money.toString());
            }

            public void onFinish() {
                // finish off when we're all dead !
            }
        }.start();



        new CountDownTimer(Long.MAX_VALUE, 500) {
            public void onTick(long millisUntilFinished) {
                if (playerEntity1.dps == 1 || playerEntity1.dps > 1) {
                    hpBar.setProgress(enemy1.getHp());
                    enemy1.hp -= playerEntity1.getDps();
                }
            }

            public void onFinish() {
                // finish off when we're all dead !
            }
        }.start();
        new CountDownTimer(Long.MAX_VALUE , 650){

            @Override
            public void onTick(long millisUntilFinished) {
                enemyPic.setImageResource(R.drawable.frame1);
            }

            @Override
            public void onFinish() {

            }
        }.start();
        new CountDownTimer(Long.MAX_VALUE , 1100){

            @Override
            public void onTick(long millisUntilFinished) {
                enemyPic.setImageResource(R.drawable.frame2);
            }

            @Override
            public void onFinish() {

            }
        }.start();
        hpBar.setMax(enemy1.getHp());
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perexod = false;
                mainListView.setAdapter(dpsOperativnikiAdapter);
                mainListView.deferNotifyDataSetChanged();
            }
        });
      /*  bossTime = new CountDownTimer(30000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(enemy1.hp < 0 || enemy1.hp == 0){
                    currentBoss = false;
                    enemy1.instantBossUpgrade(enemy1 , playerEntity1);
                    onFinish();

                }
            }

            @Override
            public void onFinish() {
                cancel();
            }
        };*/
        upgradeShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perexod = true;
                mainListView.setAdapter(operativnikiUpdateAdapter);
                mainListView.deferNotifyDataSetChanged();
            }
        });
        clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*hp.setText(enemy1.hp.toString());*/
                enemy1.dmgtoEnemy(enemy1, playerEntity1);
                clickSound.start();
                /*enemy1.reset(enemy1, playerEntity1);*/
            }
        });
        operShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(perexod == true){
                    mainListView.setAdapter(dpsOperativnikAdapterUpdate);
                    mainListView.deferNotifyDataSetChanged();
                }else{
                    mainListView.setAdapter(dpsOperativnikiAdapter);
                    mainListView.deferNotifyDataSetChanged();
                }
            }
        });
        gunShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(perexod == true){
                    mainListView.setAdapter(operativnikiUpdateAdapter);
                    mainListView.deferNotifyDataSetChanged();
                }else{
                    mainListView.setAdapter(operativnikiAdapter);
                    mainListView.deferNotifyDataSetChanged();
                }
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                settingsFragment = new SettingsFragment();
                fragmentManager.beginTransaction().replace(R.id.cs , settingsFragment).commit();
            }
        });
        enemy1.lvl = 9;
    }

    @Override
    protected void onStart() {

        super.onStart();
        new BackgroundMucis().start();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = progressDB.getWritableDatabase();
        /*db.execSQL("DELETE FROM progressDB");*/
        cv.put("EnemyHP",enemy1.getHp());
        cv.put("EnemyMG",enemy1.getMoneygain());
        cv.put("EnemyLVL",enemy1.getLvl());
        cv.put("EnemyPODLVL",enemy1.getPodLvl());
        cv.put("PEDPS",playerEntity1.getDps());
        cv.put("PEDPC",playerEntity1.getDmg());
        cv.put("PEMONEY",playerEntity1.getMoney());
        if(operativniki[0].alreadybuyed){
            cv.put("ПрапорBUYED" , 1);
        }else{
            cv.put("ПрапорBUYED" , 0);
        }
        if(operativniki[1].alreadybuyed){
            cv.put("ДилерBUYED" , 1);
        }else{
            cv.put("ДилерBUYED" , 0);
        }
        if(operativniki[2].alreadybuyed){
            cv.put("ЛыжникBUYED" , 1);
        }else{
            cv.put("ЛыжникBUYED" , 0);
        }
        if(operativniki[3].alreadybuyed){
            cv.put("МиротворецBUYED" , 1);
        }else{
            cv.put("МиротворецBUYED" , 0);
        }
        if(operativniki[4].alreadybuyed){
            cv.put("ОхотникBUYED" , 1);
        }else{
            cv.put("ОхотникBUYED" , 0);
        }
        cv.put("ПрапорDMG", operativniki[0].dmg);
        cv.put("ДилерDMG", operativniki[1].dmg);
        cv.put("ЛыжникDMG" , operativniki[2].dmg);
        cv.put("МиротворецDMG", operativniki[3].dmg);
        cv.put("ОхотникDMG", operativniki[4].dmg);
        cv.put("ПрапорPRICE", operativniki[0].price);
        cv.put("ДилерPRICE", operativniki[1].price);
        cv.put("ЛыжникPRICE" , operativniki[2].price);
        cv.put("МиротворецPRICE", operativniki[3].price);
        cv.put("Охотникprice" , operativniki[4].price);
        if(characters[0].alreadybuyed){
            cv.put("Glock-18BUYED" , 1);
        }else{
            cv.put("Glock-18BUYED" , 0);
        }
        if(characters[1].alreadybuyed){
            cv.put("P228BUYED" , 1);
        }else{
            cv.put("P228BUYED" , 0);
        }
        if(characters[2].alreadybuyed){
            cv.put("MP9BUYED" , 1);
        }else{
            cv.put("MP9BUYED" , 0);
        }
        if(characters[3].alreadybuyed){
            cv.put("MP5KBUYED" , 1);
        }else{
            cv.put("MP5KBUYED" , 0);
        }
        if(characters[4].alreadybuyed){
            cv.put("AK74BUYED" , 1);
        }else{
            cv.put("AK74BUYED" , 0);
        }
        cv.put("Glock-18DMG", characters[0].dmg);
        cv.put("P228DMG", characters[1].dmg);
        cv.put("MP9DMG", characters[2].dmg);
        cv.put("MP5KDMG", characters[3].dmg );
        cv.put("AK74DMG" , characters[4].dmg);
        cv.put("Glock-18PRICE" , characters[0].price);
        cv.put("P228PRICE", characters[1].price );
        cv.put("MP9PRICE", characters[2].price);
        cv.put("MP5KPRICE", characters[3].price);
        cv.put("AK74price" , characters[4].price);

        long rowid = db.insert("progressDB", null ,cv);

    }

    public Integer getMyMoney(PlayerEntity playerEntity) {
        return playerEntity.getMoney();
    }
    public class bossTimer extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public bossTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        Long currentTime;

        @Override
        public void onTick(long millisUntilFinished) {

            currentTime = millisUntilFinished;
            Log.d("MYBUG" , currentBoss.toString());
            Log.d("MYBUG" , currentBoss.toString());
            if (enemy1.getHp() == 0 && currentBoss|| enemy1.getHp() < 0 && currentBoss) {
                enemy1.instantBossUpgrade(enemy1 , playerEntity1);
                Log.d("MYBUGS" , "something");
                currentBoss = false;
                bossStage++;
                saveLvl++;
                timerCreated = false;
                this.cancel();
                onFinish();
            }
/*            if (millisUntilFinished/1000 > 28) {
                bossText.setText(null);
            }*/
            if(timerCreated){
                bossText.setText("БОСС!");
                mTimer.setText("Осталось : " + "\n"
                        + millisUntilFinished / 1000);
            }if(!timerCreated){
                mTimer.setText(null);
                bossText.setText(null);
            }
        }

        @Override
        public void onFinish() {

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int actionPeformed = event.getAction();
        switch(actionPeformed){
            case MotionEvent.ACTION_DOWN:{
                enemy1.dmgtoEnemy(enemy1, playerEntity1);
                clickSound.start();
            }

            case MotionEvent.ACTION_POINTER_DOWN:{
                enemy1.dmgtoEnemy(enemy1, playerEntity1);
                clickSound.start();
            }
        }
        return super.onTouchEvent(event);
    }
    public class BackgroundMucis extends Thread{
        @Override
        public void run() {
            mp.start();
            mp.setLooping(true);
        }
    }
}