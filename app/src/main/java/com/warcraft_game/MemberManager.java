package com.warcraft_game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.warcraft_game.activity.Game;
import com.warcraft_game.member.Bee;
import com.warcraft_game.member.Bullet;
import com.warcraft_game.member.Ember;
import com.warcraft_game.member.Enemy;
import com.warcraft_game.member.Hero;
import com.warcraft_game.member.Member;
import com.warcraft_game.member.Sky;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 游戏中的成员的管理类
 */
public class MemberManager {

    private Game game;

    private Hero hero;

    public MemberManager(Game game){
        this.game = game;
    }

    public void memberInit(){
        Bitmap bitmap = game.buildBitmap(R.mipmap.background);
        List<Member> list = new ArrayList<Member>();
        list.add(new Sky(
                Game.onScreenRect.left,
                Game.onScreenRect.top,
                bitmap
                )
        );
        list.add(new Sky(
                Game.onScreenRect.left,
                Game.onScreenRect.top - bitmap.getHeight(),
                bitmap
                )
        );
        game.getMemberMap().put("sky", list);
        list = new ArrayList<Member>();
        list.add(hero = new Hero(Game.onScreenRect.centerX() - (game.buildBitmap(R.mipmap.hero0).getWidth() / 2),
                Game.onScreenRect.top + 1000,
                game.buildBitmap(R.mipmap.hero0),
                game.buildBitmap(R.mipmap.hero1)
                )
        );
        game.getMemberMap().put("hero", list);
        list = new ArrayList<Member>();
        for (int i = 0; i < 20; i ++) {
            list.add(new Enemy(Game.onScreenRect.left,
                            Game.onScreenRect.top - game.buildBitmap(R.mipmap.airplane).getHeight(),
                            game.buildBitmap(R.mipmap.airplane),
                            new Bitmap[]{game.buildBitmap(R.mipmap.airplane_ember0),
                                    game.buildBitmap(R.mipmap.airplane_ember1),
                                    game.buildBitmap(R.mipmap.airplane_ember2),
                                    game.buildBitmap(R.mipmap.airplane_ember3) }
                    )
            );
        }
        game.getMemberMap().put("enemy", list);
        list = new ArrayList<Member>();
        for (int i = 0; i < 5; i ++) {
            list.add(new Enemy(Game.onScreenRect.centerX(),
                            Game.onScreenRect.top - game.buildBitmap(R.mipmap.bigplane).getHeight(),
                            game.buildBitmap(R.mipmap.bigplane),
                            new Bitmap[]{game.buildBitmap(R.mipmap.bigplane_ember0),
                                    game.buildBitmap(R.mipmap.bigplane_ember1),
                                    game.buildBitmap(R.mipmap.bigplane_ember2),
                                    game.buildBitmap(R.mipmap.bigplane_ember3) },
                            3
                    )
            );
        }
        game.getMemberMap().put("bigEnemy", list);
        list = new ArrayList<Member>();
        for (int i = 0; i < 3; i ++) {
            list.add(new Bee(Game.onScreenRect.centerX(),
                            Game.onScreenRect.top - game.buildBitmap(R.mipmap.bee).getHeight(),
                            game.buildBitmap(R.mipmap.bee),
                            new Bitmap[]{game.buildBitmap(R.mipmap.bee_ember0),
                                    game.buildBitmap(R.mipmap.bee_ember1),
                                    game.buildBitmap(R.mipmap.bee_ember2),
                                    game.buildBitmap(R.mipmap.bee_ember3) }
                    )
            );
        }
        game.getMemberMap().put("bee", list);
        list = new ArrayList<Member>();
        for (int i = 0; i < 50; i ++) {
            list.add(new Bullet(Game.onScreenRect.centerX(),
                            Game.onScreenRect.top - game.buildBitmap(R.mipmap.bullet).getHeight(),
                            game.buildBitmap(R.mipmap.bullet)
                    )
            );
        }
        game.getMemberMap().put("bullet", list);
        list = new ArrayList<Member>();
        list.add(new Member(Game.onScreenRect.left,
                        Game.onScreenRect.top,
                        game.buildBitmap(R.mipmap.pause)
                )
        );
        game.getMemberMap().put("pause", list);
        list = new ArrayList<Member>();
        list.add(new Member(Game.onScreenRect.left,
                        Game.onScreenRect.top,
                        game.buildBitmap(R.mipmap.gameover)
                )
        );
        game.getMemberMap().put("gameOver", list);
    }

    public void heroShoot(){
        List<int[]> bulletPoint = hero.shoot(game);
        if(null != bulletPoint){
            for (int[] point : bulletPoint) {
                for(Member enemy : game.getMemberMap().get("bullet")){
                    if(!enemy.isActive()){
                        Rect rect = enemy.getRect();
                        rect.set(point[0], point[1], point[0] + enemy.getBitMap().getWidth(), point[1] + enemy.getBitMap().getHeight());
                        enemy.setActive(true);
                        break;
                    }
                }
            }
        }
    }

    public void enemyAttack(){
        List<Member> list = null;
        int life = 0;
        if(1 == (int)(Math.random() * 30)){
            list = game.getMemberMap().get("enemy");
            life = 1;
        }else if(1 == (int)(Math.random() * 500)){
            list = game.getMemberMap().get("bigEnemy");
            life = 3;
        }else if(1 == (int)(Math.random() * 400)){
            list = game.getMemberMap().get("bee");
            life = 2;
        }
        if(null != list) {
            for (Member enemy : list) {
                if (!enemy.isActive()) {
                    Rect rect = enemy.getRect();
                    int left;
                    do {
                        left = (int) (Math.random() * (Game.onScreenRect.right - rect.width()));
                    } while (left < Game.onScreenRect.left);
                    rect.set(left, Game.onScreenRect.top - rect.height(), left + rect.width(), Game.onScreenRect.top);
                    enemy.setLife(life);
                    enemy.setActive(true);
                    break;
                }
            }
        }

        List<Member> embers = game.getMemberMap().get("ember");
        if (null != embers){
            Iterator<Member> it = embers.iterator();
            while (it.hasNext()){
                Member next = it.next();
                if (!next.isActive()){
                    it.remove();
                }
            }
        }
    }

    /**
     * 检测敌人与子弹、英雄机是否发生碰撞
     */
    public void checkCrash(){
        Map<String, List<Member>> map = new HashMap<>();
        map.put("enemy", game.getMemberMap().get("enemy"));
        map.put("bigEnemy", game.getMemberMap().get("bigEnemy"));
        map.put("bee", game.getMemberMap().get("bee"));
        for (Map.Entry<String, List<Member>> entry : map.entrySet()){ //遍历成员列表
            for (Member member : entry.getValue()) {
                if (member.isActive()) { //处理活动中的成员
                    boolean enemyIsDie = false;
                    for (Member bullet : game.getMemberMap().get("bullet")) { // 遍历子弹列表
                        if (bullet.isActive()) { // 只处理活动的子弹
                            if (isCarch(member.getRect(), bullet.getRect())) {
                                member.subLife(); //减生命
                                bullet.setActive(false);
                                if(!member.isActive()) {
                                    enemyIsDie = true;
                                    List<Member> embers = game.getMemberMap().get("ember");
                                    if (null == embers) {
                                        embers = new ArrayList<Member>();
                                    }
                                    embers.add(new Ember(member.getRect().left, member.getRect().top, member.getEmber()));
                                    game.getMemberMap().put("ember", embers);
                                    if ("enemy".equals(entry.getKey())) {
                                        game.setScore(game.getScore() + 1);
                                    }
                                    if ("bigEnemy".equals(entry.getKey())) {
                                        game.setScore(game.getScore() + 2);
                                    }
                                    if ("bigEnemy".equals(entry.getKey())) {
                                        game.setScore(game.getScore() + 2);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (enemyIsDie) continue;//如果敌人已死结束本次循环
                    if (isCarch(hero.getRect(), member.getRect())) {
                        member.setActive(false);
                        hero.subLife();
                        break;
                    }
                }
            }
        }
    }

    /**
     * 检测两个矩形是否有重叠区
     * @param r1
     * @param r2
     * @return
     */
    private boolean isCarch(Rect r1, Rect r2){
        return r1.contains(r2.left, r2.top)
                || r1.contains(r2.right, r2.top)
                || r1.contains(r2.left, r2.bottom)
                || r1.contains(r2.right, r2.bottom);
    }



    public void membersActivity(){
        for (List<Member> list : game.getMemberMap().values()) {
            for (Member member : list) {
                if(member.isActive()) {
                    member.activity();
                }
            }
        }
    }

    public Hero getHero() {
        return hero;
    }
}
