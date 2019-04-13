package com.warcraft_game;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * 可绘制的对象
 */
public interface DrawAble {
    /**
     * 获取对象的矩形位置
     * @return
     */
    Rect getRect();

    /**
     * 获取对象的图片资源
     * @return
     */
    Bitmap getBitMap();

    /**
     * 获取对象的存活状态
     * @return
     */
    boolean isActive();
}
