package com.warcraft_game.member;

import android.graphics.Bitmap;

import com.warcraft_game.activity.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * 英雄机
 */
public class Hero extends Member {

    private Bitmap bitmap2;

    private int shootSpeed = 50;

    public Hero(Integer left, Integer top, Bitmap bitmap, Bitmap bitmap2) {
        super(left, top, bitmap);
        this.bitmap2 = bitmap2;
        isActive = true;
//        life = 3;
    }

    @Override
    public void activity() {super.activity();}

    /**
     * 切换图片
     * @return
     */
    @Override
    public Bitmap getBitMap() {
        if((count / 20) % 2 == 1){
            return bitmap2;
        }
        return super.getBitMap();
    }

    /**
     * 发射子弹
     * @param game
     * @return 返回子弹坐标
     */
    public List<int[]> shoot(Game game){
        if(count % 1500 == 1499) {
            shootSpeed--;
        }
        if (isActive && 0 == count % shootSpeed) {
            List<int[]> list = new ArrayList<int[]>();
            list.add(new int[]{rect.left + rect.width() / 4, rect.top});
            list.add(new int[]{rect.right - rect.width()  / 4, rect.top});
            return list;
        }
        return null;
    }

    /**
     * 移动到坐标 x，y
     * @param x
     * @param y
     */
    public void moveTo(int x, int y){
        if(isActive) {
            rect.set(x - Game.onScreenRect.left - (bitmap.getWidth() / 2),
                    y - Game.onScreenRect.top - (bitmap.getHeight() / 2),
                    x - Game.onScreenRect.left + (bitmap.getWidth() / 2),
                    y - Game.onScreenRect.top + (bitmap.getHeight() / 2)
            );
        }
    }
}
