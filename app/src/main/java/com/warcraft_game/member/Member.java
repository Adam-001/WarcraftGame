package com.warcraft_game.member;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.warcraft_game.DrawAble;

/**
 * 游戏中的成员
 */
public class Member implements DrawAble {

    protected Rect rect;//成员矩形位置

    protected Bitmap bitmap;//图片对象

    protected boolean isActive = false;//活动状态

    protected int stepSize = 0;//步长

    protected int count = 0;//计数器

    protected int life = 1;//成员生命值

    protected Bitmap[] ember;//灰烬图片

    public Member(Integer left, Integer top, Bitmap bitmap){
        rect = new Rect(left, top, left + bitmap.getWidth(), top + bitmap.getHeight());
        this.bitmap = bitmap;
    }

    public Member(Integer left, Integer top, Bitmap bitmap, Bitmap[] ember){
        rect = new Rect(left, top, left + bitmap.getWidth(), top + bitmap.getHeight());
        this.bitmap = bitmap;
        this.ember = ember;
    }

    @Override
    public Rect getRect() {
        return rect;
    }

    @Override
    public Bitmap getBitMap() {
        return bitmap;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    /**
     * 设置活动状态
     * @param active
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    public void addLife() {
        life++;
    }

    public void subLife() {
        life--;
        if (life <= 0){
            isActive = false;
        }
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLife() {
        return this.life;
    }

    public Bitmap[] getEmber() {
        return ember;
    }

    /**
     * 成员动作
     */
    public void activity(){
            count ++;
    }
}
