package com.warcraft_game.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;

import com.warcraft_game.GameStatus;
import com.warcraft_game.GameView;
import com.warcraft_game.member.Member;
import com.warcraft_game.MemberManager;
import com.warcraft_game.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 游戏主体
 */
public class Game extends Activity implements Runnable{

    public static Rect onScreenRect; //游戏窗口矩形位置
    public GameStatus gameStatus = GameStatus.READY; //游戏运行状态
    private boolean isInited = false; //游戏初始化状态
    private GameView gameView; //游戏视图
    private MemberManager memberManager; //成员管理器
    private Map<String, List<Member>> memberMap; //成员集合
    private int heroLife = 0; //英雄机生命值
    private int score = 0; //游戏得分
    private long gameStartTime = 0; //游戏开始时间
    private long gamePauseStartTime = 0; //游戏暂停的起始时间
    private long gamePauseTime = 0; //游戏暂停的时间
    private MediaPlayer mMediaPlayer; //音频播放器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memberMap = new HashMap<String, List<Member>>();
        gameView = new GameView(this, memberMap);
        setContentView(gameView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameStatus = GameStatus.RUNING;
        //启动一个线程用于游戏主循环
        new Thread(this).start();
        gameStartTime = System.currentTimeMillis();
        //播放音频
        mMediaPlayer=MediaPlayer.create(this, R.raw.audio);
        mMediaPlayer.start();
        // 监听音频播放完的代码，实现音频的自动循环播放
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {
                mMediaPlayer.start();
                mMediaPlayer.setLooping(true);
            }
        });
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        Toast.makeText(this.getApplicationContext(), "onPause",Toast.LENGTH_SHORT).show();
//        Log.d("onPause","onPause");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(this.getApplicationContext(), "onResume",Toast.LENGTH_SHORT).show();
//        Log.d("onResume","onResume");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Toast.makeText(this.getApplicationContext(), "onStop",Toast.LENGTH_SHORT).show();
//        Log.d("onStop","onStop");
//    }
//
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Toast.makeText(this.getApplicationContext(), "onRestart",Toast.LENGTH_SHORT).show();
//        Log.d("onRestart","onRestart");
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放播放器资源
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void run() {
        boolean isStoped = false;
        try {
            while (!isStoped) {
                Thread.sleep(20);
                if(isInited) {
                    switch (gameStatus){
                        case READY:
                            break;
                        case RUNING:
                            heroLife = memberManager.getHero().getLife();
                            if(heroLife <= 0){
                                gameStatus = GameStatus.GAME_OVER;
                                record();
//                                mMediaPlayer.stop();
                                mMediaPlayer.pause();
                                isStoped = true;
                                break;
                            }
                            memberManager.membersActivity();//成员动作
                            memberManager.enemyAttack();//敌人发动攻击
                            memberManager.heroShoot();//英雄机发射子弹
                            memberManager.checkCrash();//处理英雄机战斗状态、数据
                            break;
                        case PAUSE:
                            break;
                        case GAME_OVER:
                            break;
                        case STOP:
                            isInited = false;
                            isStoped = true;
                            break;
                    }
                    gameView.invalidate();//游戏视图重绘
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录游戏成绩信息
     */
    private void record() {
        long time = System.currentTimeMillis() - gameStartTime - gamePauseTime;
        String useTime = (time / 1000 / 60) + "分" + ((time / 1000) % 60) + "秒";

        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            File file = new File(getCacheDir(), "record");
            FileOutputStream fo = new FileOutputStream(file, true);
            fo.write(("score=" + score + "&useTime=" + useTime + "&date=" + sft.format(new Date()) + "\n").getBytes("utf-8"));
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus){//游戏视图初始化结束
            int width = gameView.getMeasuredWidth();
            int height = gameView.getMeasuredHeight();
//            int[] postion = new int[2];
//            gameView.getLocationInWindow(postion);
//            onScreenRect = new Rect(postion[0], postion[1], postion[0] + width, postion[1] + height);
            onScreenRect = new Rect(0, 0, width, height);

            if (!isInited) {
                memberManager = new MemberManager(this);
                memberManager.memberInit();
                isInited = true;
            }
        }
    }


    /**
     * 鼠标事件监听
     * @param event
     * @return
     */
    long lastDown = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN :
                lastDown = System.currentTimeMillis(); //记录按下时间
                break;
            case MotionEvent.ACTION_UP :
                if(System.currentTimeMillis() - lastDown < 100) { //抬起时间和按下时间间隔100毫秒以内，视为单击事件
                    if (gameStatus == GameStatus.RUNING) {
                        gameStatus = GameStatus.PAUSE;
                        gamePauseStartTime = System.currentTimeMillis();
                        mMediaPlayer.pause();
                    } else if (gameStatus == GameStatus.PAUSE) {
                        gameStatus = GameStatus.RUNING;
                        mMediaPlayer.start();
                        gamePauseTime = System.currentTimeMillis() - gamePauseStartTime;
                    } else if (gameStatus == GameStatus.GAME_OVER) {
                        score = 0;
                        memberMap = new HashMap<>();
                        gameView.setDrawAbles(memberMap);
                        memberManager = new MemberManager(this);
                        memberManager.memberInit();
                        gameStatus = GameStatus.RUNING;
                        new Thread(this).start();
                        mMediaPlayer.seekTo(0);
                        mMediaPlayer.start();
                        gameStartTime = System.currentTimeMillis();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE :
                if (gameStatus == GameStatus.RUNING) {
                    memberManager.getHero().moveTo(Math.round(event.getX()), Math.round(event.getY()));
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        gameStatus = GameStatus.STOP;
        record();
        super.onBackPressed();
    }

    public Bitmap buildBitmap(int resId){
        return BitmapFactory.decodeResource(
                gameView.getContext().getResources(),
                resId
        );
    }

    public int getHeroLife() {
        return heroLife;
    }

    public Map<String, List<Member>> getMemberMap() {
        return memberMap;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
