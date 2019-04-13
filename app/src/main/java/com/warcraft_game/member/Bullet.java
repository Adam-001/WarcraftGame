package com.warcraft_game.member;

import android.graphics.Bitmap;

import com.warcraft_game.activity.Game;

/**
 * 子弹
 */
public class Bullet extends Member {

    public Bullet(Integer left, Integer top, Bitmap bitmap) {
        super(left, top, bitmap);
        stepSize = -5;
    }

    @Override
    public void activity() {
        super.activity();
        if(count % 500 == 499) {
            stepSize--;
        }
        if(isActive) {
            if (rect.bottom >= Game.onScreenRect.top) {
                rect.set(rect.left, rect.top + stepSize, rect.right, rect.bottom + stepSize);
            }else {
                isActive = false;
            }
        }
    }
}
