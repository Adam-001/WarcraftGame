package com.warcraft_game.member;

import android.graphics.Bitmap;

import com.warcraft_game.activity.Game;

/**
 * 敌机
 */
public class Enemy extends Member {

    public Enemy(Integer left, Integer top, Bitmap bitmap) {
        super(left, top, bitmap);
        stepSize = 5;
    }

    public Enemy(Integer left, Integer top, Bitmap bitmap, Bitmap[] ember){
        super(left, top, bitmap, ember);
        stepSize = 5;
    }

    public Enemy(Integer left, Integer top, Bitmap bitmap, Bitmap[] ember, int life){
        super(left, top, bitmap, ember);
        this.life = life;
        stepSize = 5;
    }

    @Override
    public void activity() {
        super.activity();
        if(count % 300 == 299) {
            stepSize++;
        }
        if(isActive){
            if (rect.top <= Game.onScreenRect.bottom){
                rect.set(rect.left, rect.top + stepSize, rect.right, rect.bottom + stepSize);
            }else{
                isActive = false;
            }
        }
    }
}
