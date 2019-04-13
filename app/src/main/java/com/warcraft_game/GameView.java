package com.warcraft_game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.warcraft_game.activity.Game;

import java.util.List;
import java.util.Map;

/**
 * 游戏视图
 */
public class GameView<T extends DrawAble> extends View {

    private Map<String, List<T>> drawAbles;//可绘制的对象列表
    private Paint paint;//画笔
    private Game game;

    public GameView(Game game, Map<String, List<T>> drawAbles) {
        super(game);
        this.game = game;
        this.drawAbles =  drawAbles;
        paint = new Paint();
        paint.setTextSize(50);
        setFocusable(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(game.gameStatus == GameStatus.RUNING) {
            for (List<T> list : drawAbles.values()) {
                for (T drawAble : list) {
                    if (drawAble.isActive()) {
                        canvas.drawBitmap(drawAble.getBitMap(), drawAble.getRect().left, drawAble.getRect().top, paint);
                    }
                }
            }
            canvas.drawText("分数：" + game.getScore(), Game.onScreenRect.left + 10, Game.onScreenRect.top + 60, paint);
//            canvas.drawText("生命：" + game.getHeroLife(), Game.onScreenRect.left + 10, Game.onScreenRect.top + 110, paint);
        }
        if (game.gameStatus == GameStatus.PAUSE){
            T pause = drawAbles.get("pause").get(0);
            canvas.drawBitmap(pause.getBitMap(), Game.onScreenRect.width() / 2 - pause.getRect().centerX(), pause.getRect().top, paint);
        }
        if (game.gameStatus == GameStatus.GAME_OVER){
            T gameOver = drawAbles.get("gameOver").get(0);
            canvas.drawBitmap(gameOver.getBitMap(), Game.onScreenRect.width() / 2 - gameOver.getRect().centerX(), gameOver.getRect().top, paint);
        }
    }

    public void setDrawAbles(Map<String, List<T>> drawAbles) {
        this.drawAbles = drawAbles;
    }
}
