package com.warcraft_game.member;

import android.graphics.Bitmap;

import com.warcraft_game.activity.Game;

/**
 * 天空
 */
public class Sky extends Member {

    public Sky(Integer left, Integer top, Bitmap bitmap) {
        super(left, top, bitmap);
        stepSize = 2;
        isActive = true;
    }

    @Override
    public void activity() {
        if(isActive) {
            if (rect.top >= Game.onScreenRect.bottom) {
                rect.set(rect.left, Game.onScreenRect.top - bitmap.getHeight(), rect.right, Game.onScreenRect.top);
            }
            rect.set(rect.left, rect.top + stepSize, rect.right, rect.bottom + stepSize);
        }else {
            isActive = false;
        }
    }
}
