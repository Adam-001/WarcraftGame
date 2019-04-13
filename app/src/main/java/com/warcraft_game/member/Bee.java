package com.warcraft_game.member;

import android.graphics.Bitmap;

import com.warcraft_game.activity.Game;

/**
 * 蜜蜂
 */
public class Bee extends Member {

    public Bee(Integer left, Integer top, Bitmap bitmap) {
        super(left, top, bitmap);
        stepSize = 5;
        life = 2;
    }

    public Bee(Integer left, Integer top, Bitmap bitmap, Bitmap[] ember){
        super(left, top, bitmap, ember);
        stepSize = 5;
    }

    @Override
    public void activity() {
        super.activity();
        if(isActive) {
            if (rect.top <= Game.onScreenRect.bottom) {
                if (rect.left <= Game.onScreenRect.left || rect.right >= Game.onScreenRect.right) {
                    stepSize = -stepSize;
                }
                rect.set(rect.left + stepSize, rect.top + Math.abs(stepSize), rect.right + stepSize, rect.bottom + Math.abs(stepSize));
            }else {
                isActive = false;
            }
        }
    }
}
