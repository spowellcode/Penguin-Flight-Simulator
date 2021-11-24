package com.example.canvastest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;


import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;
import static android.graphics.Bitmap.createScaledBitmap;

public class MyCanvas extends View {

    boolean ThreadRun = false;
    boolean gameover = false;
    boolean menu = true;

    private Handler mHandler;

    int x = 500;
    int y = 50;
    int speed = 40;

    int medalstat = 0;

    int score = 0;
    int best = 0;

    int sprite = 0;
    int dropspeed = 150;

    int moongroundlevelfinal = getHeight() / 2;

    int moongroundlevel = 0;

    CharThread p = new CharThread();
    Up w = new Up();
    east a = new east();
    west d = new west();
    sprite Sprit = new sprite();
    cloudmov cloudstart = new cloudmov();
    planethread flytheplane = new planethread();
    gpsthread flythegps = new gpsthread();
    rockthread flytherock = new rockthread();
    planethread2 redplanethread = new planethread2();
    planethread3 greenplanethread = new planethread3();
    moonthread moonthreadfiller = new moonthread();


    boolean spritrun = false; //Enable sprite animation
    boolean cloudson = false; //Enables clouds
    boolean enablestar1 = false; //Enables stars to spawn
    boolean enablestar2 = false; //Enables more stars to spawn
    boolean flyplane = false; //For planes to spawn
    boolean win = false; //Enables game win
    boolean plane2start = false; //Enables plane 2
    boolean plane3start = false; //Enables plane 3
    boolean plane4start = false; //Enables plane 4
    boolean killsound = false; //Player dies sound
    boolean skylock = false; //Freezes sky
    boolean dirlock1 = false;
    boolean dirlock2 = false;
    boolean musicon = true;
    boolean moonlevelfinshed = false;

    boolean beentomoon = false;
    boolean aboutscreen = false;

    boolean planepushdown = false;

    boolean moonstars = false;

    boolean newhighscore = false;

    boolean fly1 = true;
    boolean fly2 = true;
    boolean fly3 = true;

    int floorcord = 20;
    int cloudpos1 = 1000, cloudpos2 = 500, cloudpos3 = 2, cloudpos4 = 6;
    int cloudlev1 = (getHeight() / 5) + 200, cloudlev2 = (getHeight() / 3), cloudlev3 = (getHeight() / 100) + 500, cloudlev4 = (getHeight() / 7) + 750;
    int ocloudlev1 = (getHeight() / 5) + 200, ocloudlev2 = (getHeight() / 3), ocloudlev3 = (getHeight() / 100) + 500, ocloudlev4 = (getHeight() / 7) + 750;
    int altitude = 0;
    int planex = getWidth() - 1200;
    int gpsx = getWidth() - 1200;
    int rockx = getWidth() - 1200;;
    int planey = getBottom() / 3;

    String skycolor = "#349beb";

    public MyCanvas(Context context) {
        super(context);
    }

    MediaPlayer player = MediaPlayer.create(getContext(), R.raw.maintheme);
    MediaPlayer hits1 = MediaPlayer.create(getContext(), R.raw.hit1);
    MediaPlayer hits2 = MediaPlayer.create(getContext(), R.raw.hit2);
    MediaPlayer hits3 = MediaPlayer.create(getContext(), R.raw.hit3);

    MediaPlayer buttonhit = MediaPlayer.create(getContext(), R.raw.buttonpress);
    MediaPlayer loose = MediaPlayer.create(getContext(), R.raw.lost);
    MediaPlayer windsound = MediaPlayer.create(getContext(), R.raw.wind);
    MediaPlayer newhigh = MediaPlayer.create(getContext(), R.raw.highscore);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        SharedPreferences bestscore = getContext().getSharedPreferences("highstorage", Activity.MODE_PRIVATE);
        SharedPreferences.Editor Ebestscore = bestscore.edit();
        SharedPreferences moonmedal = getContext().getSharedPreferences("highstorage", Activity.MODE_PRIVATE);
        SharedPreferences.Editor Emoonmedal = moonmedal.edit();

        medalstat = moonmedal.getInt("beentomoonn", 0);


        mHandler = new Handler();
        Rect ourRect = new Rect();
        ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight() - floorcord);

        Paint blue = new Paint();
        Paint green = new Paint();

        green.setColor(Color.GREEN);
        green.setStyle(Paint.Style.FILL);
        blue.setColor(Color.parseColor(skycolor));
        blue.setStyle(Paint.Style.FILL);
        Paint textpaint = new Paint();
        textpaint.setColor(Color.WHITE);
        textpaint.setTextSize(50);
        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"font/atomic_age.ttf");
        //textpaint.setTypeface(tf);
        canvas.drawRect(ourRect, blue);
        Bitmap sprite1;
        Bitmap sprite2;
        Bitmap sprite3;
        Bitmap cloudI;
        Bitmap cloudII;
        Bitmap cloudIII;
        Bitmap cloudIV;
        Bitmap starI;
        Bitmap starII;
        Bitmap starIII;
        Bitmap planes;
        Bitmap gpsimg;
        Bitmap rockimg;
        Bitmap redplane;
        Bitmap greenplane;
        Bitmap about;

        Bitmap nullmedal;
        Bitmap bmedal;
        Bitmap smedal;
        Bitmap gmedal;
        Bitmap dmedal;

        Bitmap playpressed;
        Bitmap infropressed;

        Bitmap earth;

        Bitmap menuscreen;
        //Sets textures
        sprite1 = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        sprite2 = BitmapFactory.decodeResource(getResources(), R.drawable.player2);
        sprite3 = BitmapFactory.decodeResource(getResources(), R.drawable.player3);
        cloudI = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1);
        cloudII = BitmapFactory.decodeResource(getResources(), R.drawable.cloud2);
        cloudIII = BitmapFactory.decodeResource(getResources(), R.drawable.cloud3);
        cloudIV = BitmapFactory.decodeResource(getResources(), R.drawable.cloud4);
        starI = BitmapFactory.decodeResource(getResources(), R.drawable.star1);
        starII = BitmapFactory.decodeResource(getResources(), R.drawable.star2);
        starIII = BitmapFactory.decodeResource(getResources(), R.drawable.star3);
        planes = BitmapFactory.decodeResource(getResources(), R.drawable.plane);
        gpsimg = BitmapFactory.decodeResource(getResources(), R.drawable.gps);
        rockimg = BitmapFactory.decodeResource(getResources(), R.drawable.rock);
        redplane = BitmapFactory.decodeResource(getResources(), R.drawable.planered);
        greenplane = BitmapFactory.decodeResource(getResources(), R.drawable.planegreen);
        about = BitmapFactory.decodeResource(getResources(), R.drawable.info);

        infropressed = BitmapFactory.decodeResource(getResources(), R.drawable.infopressed);
        playpressed = BitmapFactory.decodeResource(getResources(), R.drawable.playbuttonpressed);

        earth = BitmapFactory.decodeResource(getResources(), R.drawable.earth);
        Bitmap earthres = Bitmap.createScaledBitmap(earth,200,200, false);

        nullmedal = BitmapFactory.decodeResource(getResources(), R.drawable.nomedal);
        bmedal = BitmapFactory.decodeResource(getResources(), R.drawable.bronze);
        smedal = BitmapFactory.decodeResource(getResources(), R.drawable.silver);
        gmedal = BitmapFactory.decodeResource(getResources(), R.drawable.gold);
        dmedal = BitmapFactory.decodeResource(getResources(), R.drawable.diamond);

        menuscreen = BitmapFactory.decodeResource(getResources(), R.drawable.menu);

        Bitmap nullmdealres = Bitmap.createScaledBitmap(nullmedal,200,200, false);
        Bitmap bmedalres = Bitmap.createScaledBitmap(bmedal,200,200, false);
        Bitmap smedalres = Bitmap.createScaledBitmap(smedal,200,200, false);
        Bitmap gmedalres = Bitmap.createScaledBitmap(gmedal,200,200, false);
        Bitmap dmedalres = Bitmap.createScaledBitmap(dmedal,200,200, false);

        Bitmap menuscreenres = Bitmap.createScaledBitmap(menuscreen,625,313, false);
        //Sets textures for clouds
        Bitmap cloudIres = Bitmap.createScaledBitmap(cloudI,800,400, false);
        Bitmap cloudIIres = Bitmap.createScaledBitmap(cloudII,800,400, false);
        Bitmap cloudIIIres = Bitmap.createScaledBitmap(cloudIII,800,400, false);
        Bitmap cloudIVres = Bitmap.createScaledBitmap(cloudIV,800,400, false);

        if (altitude < 30) {
            canvas.drawBitmap(cloudIres, getWidth() - cloudpos1, cloudlev1, null);
            canvas.drawBitmap(cloudIIres, getWidth() - cloudpos2, cloudlev2, null);
            canvas.drawBitmap(cloudIIIres, getWidth() + cloudpos3, cloudlev3, null);
            canvas.drawBitmap(cloudIVres, getWidth() + cloudpos4, cloudlev4, null);
            invalidate();
        }
        best = bestscore.getInt("bestkey", 0);

        if (score > best){
            best = score;
            Ebestscore.putInt("bestkey", best);
            Ebestscore.commit();

            if(!newhighscore) {
                newhigh.start();
                newhighscore = true;
            }
        }


        if (!cloudson){
            new Thread(cloudstart).start();
            cloudson = true;

        }
        if (altitude < 30 && musicon && !menu){
            windsound.start();
        }
        if (!musicon){
            windsound.pause();
        }
        if (altitude > 30){
            windsound.stop();
        }
        if (y < 1 && !skylock) {
            floorcord = 0;
            cloudlev1 += 500;
            cloudlev2 += 500;
            cloudlev3 += 500;
            cloudlev4 += 500;
            altitude++;
            invalidate();
            skylock = true;
            }
        if (y>1 && skylock){
            skylock = false;
        }
        if (altitude==0){
            skycolor = "#349beb";
        }
        if (altitude == 25){
            skycolor = "#0330fc";
            flyplane = true;
            plane2start = true;
        }
        if (altitude == 26){
                medalstat = 1;
        }
        if (altitude == 50){
            medalstat = 2;
        }
        if (altitude == 76){
            medalstat = 3;
        }
        if (altitude == 10){
            flyplane = true;
            plane3start = true;
        }
        if (altitude == 17){
            flyplane = true;
            plane4start = true;
        }
        if(flyplane){
            if (plane2start){
                canvas.drawBitmap(planes, planex , planey, null);
                if (fly1){
                new Thread(flytheplane).start();
                fly1 = false;
                }
            }
            if (plane3start){
                canvas.drawBitmap(redplane, planex , planey, null);
                if (fly2){
                    new Thread(redplanethread).start();
                    fly2 = false;
                }
            }
            if (plane4start){
                canvas.drawBitmap(greenplane, planex , planey, null);
                if (fly3){
                    new Thread(greenplanethread).start();
                    fly3 = false;
                }
            }
        }
        if (flyplane && plane3start || flyplane && plane2start || flyplane && plane4start ){
            planepushdown = true;
        }else planepushdown = false;
        if (altitude == 50){
            skycolor = "#1800c9";
            canvas.drawBitmap(gpsimg, gpsx , getBottom() / 3, null);
            new Thread(flythegps).start();
        }
        if (altitude == 75) {
            skycolor = "#0b005c";
            enablestar1 = true;
            canvas.drawBitmap(rockimg, rockx , getBottom() / 3, null);
            new Thread(flytherock).start();
        }
        if (altitude > 100){
            skycolor = "#000242";
            enablestar2 = false;
            enablestar1 = false;
            if(score > 998){
                //moon code
                win = true;
                beentomoon = true;
                medalstat = 4;

                Emoonmedal.putInt("beentomoonn", medalstat);
                Emoonmedal.commit();

                canvas.drawText("You Won!!", getWidth() / 3, getHeight() / 2, textpaint);
                canvas.drawBitmap(earthres, getWidth() / 4, getHeight() / 12, null);
                gameover = true;
                y = getHeight() / 2;
                x = getWidth() / 5;
                sprite = 2;
                Paint moonpaint = new Paint();
                moonpaint.setStyle(Paint.Style.FILL);
                moonpaint.setColor(Color.GRAY);
                canvas.drawRect(getLeft(), moongroundlevel, getRight(), getBottom(), moonpaint);
                if (!moonlevelfinshed){
                    new Thread(moonthreadfiller).start();
                    moonlevelfinshed = true;
                }

            }

        }
        if (enablestar1) {
            canvas.drawBitmap(starI, getWidth() - 500, getHeight() / 12, null);
            canvas.drawBitmap(starI, getWidth()  - 1000, getHeight() / 11, null);
            canvas.drawBitmap(starI, getWidth() - 123, getHeight() / 10, null);
            canvas.drawBitmap(starII, getWidth() - 236, getHeight() / 9, null);
            canvas.drawBitmap(starII, getWidth() - 8457, getHeight() / 8, null);
            canvas.drawBitmap(starII, getWidth() - 1345, getHeight() / 7, null);
            canvas.drawBitmap(starIII, getWidth() - 300, getHeight() / 6, null);
            canvas.drawBitmap(starIII, getWidth() - 785, getHeight() / 5, null);
            canvas.drawBitmap(starIII, getWidth() - 200, getWidth() / 4, null);
            canvas.drawBitmap(starIII, getWidth() / 3, getWidth() /3, null);
            invalidate();
        }
        if (enablestar2){
            canvas.drawBitmap(starII, getWidth() - 1231, getBottom() - 260, null);
            canvas.drawBitmap(starI, getWidth()  - 123, getBottom() - 1150, null);
            canvas.drawBitmap(starIII, getWidth() - 936, getBottom() - 1050, null);
            canvas.drawBitmap(starI, getWidth() - 400, getBottom() - 950, null);
            canvas.drawBitmap(starII, getWidth() - 700, getBottom() - 850, null);
            canvas.drawBitmap(starII, getWidth() - 156, getBottom() - 750, null);
            canvas.drawBitmap(starI, getWidth() - 856, getBottom() - 650, null);
            canvas.drawBitmap(starIII, getWidth() - 275, getBottom() - 550, null);
            canvas.drawBitmap(starII, getWidth() - 221, getBottom() - 450, null);
            canvas.drawBitmap(starIII, getWidth() - 284,  getBottom() - 350, null);
        }
        if (!menu) {
            //canvas.drawCircle(x, y, 100, blue);
            if(!spritrun) {
                new Thread(Sprit).start();
                spritrun = true;
            }
            if (sprite == 0) {
                Bitmap sprite1res = Bitmap.createScaledBitmap(sprite1, 150, 300, false);
                canvas.drawBitmap(sprite1res, x - 50, y - 150, null);
                invalidate();
            }
            if (sprite == 1) {
                Bitmap sprite2res = Bitmap.createScaledBitmap(sprite2, 150, 300, false);
                canvas.drawBitmap(sprite2res, x - 50, y - 150, null);
                invalidate();
            }
            if (sprite == 2) {
                Bitmap sprite3res = Bitmap.createScaledBitmap(sprite3, 150, 300, false);
                canvas.drawBitmap(sprite3res, x - 50, y - 150, null);
                invalidate();
            }
            else {
                Bitmap sprite1res = Bitmap.createScaledBitmap(sprite1, 150, 300, false);
                canvas.drawBitmap(sprite1res, x - 50, y - 150, null);
            }

        }

        if (menu) {
            if (musicon) {
                player.start();
            }
            if (!musicon) {
                player.pause();
            }
            Paint paint = new Paint();
            Bitmap logo;
            logo = BitmapFactory.decodeResource(getResources(), R.drawable.logopenguin);
            Bitmap aboutresized = Bitmap.createScaledBitmap(about, 150, 150, false);

            if (Globals.touchx > getWidth() - 200 && Globals.touchx < (getWidth() - 200) + 100 && Globals.touchy > getBottom() - 200 && Globals.touchy < (getBottom() - 200) + 100) {
                Bitmap infropressres = Bitmap.createScaledBitmap(infropressed, 150, 150, false);
                canvas.drawBitmap(infropressed, getWidth() - 200, getBottom() - 200, null);
                aboutscreen = true;
                buttonhit.start();
                Globals.touchx = -2000;
                Globals.touchy = -2000;
            }
            if (!aboutscreen) {
                canvas.drawBitmap(aboutresized, getWidth() - 200, getBottom() - 200, null);
                Bitmap logoresized = Bitmap.createScaledBitmap(logo, getWidth() - 100, getHeight() / 3, false);
                Bitmap play;
                play = BitmapFactory.decodeResource(getResources(), R.drawable.playbutton);
                Bitmap playresized = Bitmap.createScaledBitmap(play, 150, 150, false);
                paint.setAntiAlias(true);
                paint.setFilterBitmap(true);
                paint.setDither(true);
                float playcordL = getWidth() / 3;
                float playcordT = getHeight() / 2;
                canvas.drawBitmap(logoresized, getWidth() / 25, getHeight() / 5, paint);
                canvas.drawBitmap(playresized, playcordL, playcordT, paint);
                canvas.drawText("Best:  " + best, getWidth() / 3, getBottom() / 25, textpaint);
                invalidate();
                if (Globals.touchx > playcordL && Globals.touchx < playcordL + 100 && Globals.touchy > playcordT && Globals.touchy < playcordT + 100) {
                    menu = false;
                    buttonhit.start();
                    Bitmap playpressres = Bitmap.createScaledBitmap(playpressed, 150, 150, false);
                    canvas.drawBitmap(playpressres, playcordL, playcordT, paint);

                }
            }
        }
        if (aboutscreen) {
            Paint paint = new Paint();
            Paint aboutpaint = new Paint();
            Paint moonpaint = new Paint();
            moonpaint.setStyle(Paint.Style.FILL);
            moonpaint.setColor(Color.WHITE);
            aboutpaint.setStyle(Paint.Style.FILL);
            aboutpaint.setColor(Color.BLACK);
            canvas.drawRect(getLeft() + 100, getTop() + 100, getRight() - 100, getBottom() - 100, moonpaint);
            Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logopenguin);
            Bitmap logoresized = Bitmap.createScaledBitmap(logo, 500, 316, false);
            canvas.drawBitmap(logoresized, getWidth() / 4, getHeight() / 10, paint);
            aboutpaint.setTextSize(50);
            canvas.drawText("I hope you enjoyed this game ", getWidth() / 6, (getHeight() / 10) + 500, aboutpaint);
            canvas.drawText("as much as I enjoyed developing it.", getWidth() / 6, (getHeight() / 10) + 550, aboutpaint);
            canvas.drawText("Thanks for Playing!", getWidth() / 6, (getHeight() / 10) + 600, aboutpaint);
            canvas.drawText("Sam Powell (C) 2020", getWidth() / 4, getBottom() - 40, textpaint);
            Bitmap sprite1res = Bitmap.createScaledBitmap(sprite1, 150, 300, false);
            canvas.drawBitmap(sprite1res, getWidth() / 2, (getBottom() / 2) - 200, null);

            if(Globals.touchy > 0 && Globals.touchx > 0){
                aboutscreen = false;
                buttonhit.start();
            }
        }
        if (!menu && !gameover && !aboutscreen) {
            canvas.drawText("Score:  " + score, 32, 64, textpaint);
            //canvas.drawText(String.valueOf(getBottom()), 3, getHeight() / 2, textpaint);
            //canvas.drawText(String.valueOf(altitude), 3, (getHeight() / 2) + 80, textpaint);
            player.stop();
        }

        if (y < getBottom() && y > getBottom() - 200 || x > getWidth() + 100 || x < -100 || y > getBottom() - 200 || win && moonlevelfinshed) {
            int gameovermsgw = getWidth() / 3;
            int gameovermsgh = getHeight() / 3;
            float buttondimL = gameovermsgh - 60;
            float buttondimT = (gameovermsgw + 300) + 250;
                canvas.drawBitmap(menuscreenres, gameovermsgw - 50, gameovermsgh - 140, null);
                if (!win) {
                    canvas.drawText("Game Over!", gameovermsgw, gameovermsgh, textpaint);
                }
                if (win){
                    canvas.drawText("Touch Down!", gameovermsgw, gameovermsgh, textpaint);
                }
                canvas.drawText("Score: " + score, gameovermsgw, gameovermsgh + 60, textpaint);
                canvas.drawText("Best: " + best, gameovermsgw, gameovermsgh + 120, textpaint);
                canvas.drawText("Medal", gameovermsgw + 320, gameovermsgh - 80, textpaint);

                if (medalstat == 0) {
                    canvas.drawBitmap(nullmdealres, gameovermsgw + 300, gameovermsgh - 60, null);
                }
                if (medalstat == 1) {
                    canvas.drawBitmap(bmedalres, gameovermsgw + 300, gameovermsgh - 60, null);
                }
                if (medalstat == 2) {
                    canvas.drawBitmap(smedalres, gameovermsgw + 300, gameovermsgh - 60, null);
                }
                if (medalstat == 3) {
                    canvas.drawBitmap(gmedalres, gameovermsgw + 300, gameovermsgh - 60, null);
                }
                if (medalstat == 4) {
                    canvas.drawBitmap(dmedalres, gameovermsgw + 300, gameovermsgh - 60, null);
                }

            Bitmap bball;
            bball = BitmapFactory.decodeResource(getResources(), R.drawable.playbutton);
            Bitmap playresized = Bitmap.createScaledBitmap(bball,150,150, false);
            canvas.drawBitmap(playresized, buttondimL, buttondimT, null);
            invalidate();
            gameover = true;
            //canvas.drawText(String.valueOf(buttondimT),  3, getHeight() / 2, textpaint);
           // canvas.drawText(String.valueOf(buttondimL),  3, (getHeight() / 2) + 80, textpaint);
           // canvas.drawText(String.valueOf(Globals.touchx),  e.printStackTrace();3, (getHeight() / 2) + 160, textpaint);
           //canvas.drawText(String.valueOf(getHeight()),  3, (getHeight() / 2) + 240, textpaint);
            if(!killsound){
                if(!win) {
                    loose.start();
                }
                killsound = true;
            }
            invalidate();
            if (Globals.touchx > buttondimL && Globals.touchx < buttondimL + 150 && Globals.touchy > buttondimT &&  Globals.touchy < buttondimT + 150)
            {
                Bitmap playpressres = Bitmap.createScaledBitmap(playpressed,150,150, false);
                canvas.drawBitmap(playpressres, buttondimL, buttondimT, null);
                x = getWidth() / 2;
                buttonhit.start();
                y = 10;
                score = 0;
                Globals.touchy = -2000;
                Globals.touchx = -2000;
                invalidate();
                gameover = false;
                spritrun = false;
                win = false;
                cloudlev1 = ocloudlev1;
                cloudlev2 = ocloudlev2;
                cloudlev3 = ocloudlev3;
                cloudlev4 = ocloudlev4;
                floorcord = 20;
                killsound = false;
                altitude = 0;
                newhighscore = false;
                plane2start = false;
                plane3start = false;
                plane4start = false;
                fly1 = true;
                fly2 = true;
                fly3 = true;
                moonstars = false;
                moonlevelfinshed = false;
                invalidate();
            }
        }

        // canvas.drawText(String.valueOf(getBottom()), 100, 150, textpaint);

        if (!ThreadRun && y <= getHeight() - 130 && !menu) {
            new Thread(p).start();
        }

        if (Globals.touchx > x && Globals.touchx < x + 100 && Globals.touchy < y + 50 && Globals.touchx < y + 100 && !gameover) {
            Globals.touchx = -2000;
            Globals.touchy = -2000;
            score = score + 1;
            new Thread(w).start();
            hits3.start();
            if (planepushdown){
                planey =+ 500;
            }
            invalidate();
        }
        if (Globals.touchx > x - 50 && Globals.touchx < x + 50 && Globals.touchy > y && Globals.touchx < y + 100 && !gameover){
            new Thread(d).start();
            Globals.touchx = -2000;
            Globals.touchy = -2000;
            hits2.start();
        }
        if (Globals.touchx > x + 51 && Globals.touchx < x + 150 && Globals.touchy > y && Globals.touchx < y + 100 && !gameover){
            new Thread(a).start();
            Globals.touchx = -2000;
            Globals.touchy = -2000;
            hits1.start();
        }

    }

    class CharThread extends Thread {

        public void run() {
            do {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        y = y + speed;
                        invalidate();
                        if (y == getHeight() - 70) {
                            ThreadRun = false;
                            gameover = true;
                        }
                    }
                });

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (ThreadRun);

        }
    }

    class Up extends Thread {

        public void run() {
            for (int i = 0; i < 50; ) {
                i++;
                y = y - 20;
                if (sprite == 2 && !dirlock1) {
                    x = x - 4;
                    dirlock2 = true;
                }
                if (sprite == 1 && !dirlock2){
                    x = x - 4;
                    dirlock1 = true;
                }
                speed = 10;
                try {
                    Thread.sleep(10);
                    speed = dropspeed;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Globals.touchx = -2000;
            Globals.touchy = -2000;
            dirlock2 = false;
            dirlock1 = false;
        }
    }

    class east extends Thread {

        public void run() {
            for (int i = 0; i < 15; ) {
                i++;
                x = x - 20;
                y = y - 20;
                speed = 10;
                try {
                    Thread.sleep(10);
                    speed = dropspeed;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Globals.touchx = -2000;
            Globals.touchy = -2000;
        }
    }

    class west extends Thread {

        public void run() {
            for (int i = 0; i < 15; ) {
                i++;
                x = x + 20;
                y = y - 20;
                speed = 10;
                try {
                    Thread.sleep(10);
                    speed = dropspeed;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Globals.touchx = -2000;
            Globals.touchy = -2000;

        }
    }

    class sprite extends Thread{
        public void run(){
            do {
                sprite++;
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (sprite > 2){
                    sprite = 0;
                }
            }while (!gameover);
        }
    }
    class cloudmov extends Thread{
        public void run(){
            do{
                cloudpos1++;
                cloudpos2++;
                cloudpos3--;
                cloudpos4--;
                if (cloudpos1 > getWidth() + 800 ) {
                cloudpos1 = (getWidth() - getWidth()) - 500;
                }
                if (cloudpos2 > getWidth() + 800) {
                    cloudpos2 = (getWidth() - getWidth()) - 500;
                }
                if (cloudpos2 > getWidth() + 800) {
                    cloudpos2 = (getWidth() - getWidth()) - 500;
                }
                if (cloudpos3 > getWidth() + 800) {
                    cloudpos3 = (getWidth() - getWidth()) - 500;
                }
                if (cloudpos4 > getWidth() + 800) {
                    cloudpos4 = (getWidth() - getWidth()) - 500;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while(cloudson);
        }
    }
    class planethread extends Thread{
        public void run(){
            do {
                planex++;
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (planex<getWidth()+600);
            planex = getWidth() - 5000;
            plane2start = false;
            flyplane = false;
            planey = getBottom() / 3;
        }
    }
    class planethread2 extends Thread{
        public void run(){
            do {
                planex++;
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (planex<getWidth()+600);
            planex = getWidth() - 5000;
            plane3start = false;
            flyplane = false;
            planey = getBottom() / 3;

        }
    }
    class planethread3 extends Thread{
        public void run(){
            do {
                planex++;
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (planex<getWidth()+600);
            planex = getWidth() - 5000;
            plane4start = false;
            flyplane = false;
            planey = getBottom() / 3;
        }
    }
    class gpsthread extends Thread{
        public void run(){
            do {
                gpsx += 2;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (gpsx<getWidth()+600);
            this.interrupt();
        }
    }
    class rockthread extends Thread{
        public void run(){
            do {
                rockx++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (rockx<getWidth()+600);
            this.interrupt();

        }
    }
    class moonthread extends Thread{
        public void run(){
            do {
                moongroundlevel++;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (moongroundlevel != getHeight() / 2);
            moonstars = true;
        }
    }
}



