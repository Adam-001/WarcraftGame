package com.warcraft_game.member;

import android.graphics.Bitmap;

public class Ember extends Member {

    public Ember(Integer left, Integer top, Bitmap[] ember) {
        super(left, top, ember[0]);
        this.ember = ember;
        isActive = true;
    }

    @Override
    public void activity() {
        super.activity();
        if(4 == count / 5 % 5){
            isActive = false;
            return;
        }
        bitmap = ember[count / 5 % 5];
    }
}
