package com.base.tankGame1;

//public class merbers {
//    public merbers() {
//
//    }
//}

import java.awt.*;
import java.util.Vector;

class Tank {

    int x = 0;
    int y = 0;
    int speed = 2;
    int direct = 0;
    int color;
    // 宽20，长30
    int sizeX = 20;
    int sizeY = 30;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void moveUp() {
        this.y = this.y - this.speed;
    }

    public void moveDown() {
        this.y = this.y + this.speed;
    }

    public void moveLeft() {
        this.x = this.x - this.speed;
    }

    public void moveRight() {
        this.x = this.x + this.speed;
    }

}

/**
 * 我的坦克
 */
class Hero extends Tank {
    HeroShot heroShot = null;
    Vector<HeroShot> heroShots = new Vector<HeroShot>();
    public Hero(int x, int y) {
        super(x, y);
        super.setSpeed(6);
    }

    // 发射子弹
    public void shotEnemy() {
        int shotX = 0;
        int shotY = 0;
        // 最多5发子弹
        if (this.heroShots.size() >= 5) return;
        switch (direct) {
            // 上
            case 0:
                shotX = x + 10;
                shotY = y;
                break;
            // 下
            case 1:
                shotX = x + 10;
                shotY = y + 30;
                break;
            // 左
            case 2:
                shotX = x;
                shotY = y + 10;
                break;
            // 右
            case 3:
                shotX = x + 30;
                shotY = y + 10;
                break;
        }
        heroShot = new HeroShot(shotX, shotY);
        heroShot.setDirect(direct);
        Thread thread = new Thread(heroShot);
        thread.start();
        heroShots.add(heroShot);
    }
}

/**
 * 敌人的坦克
 */
class EnemyTank extends Tank {
    public EnemyTank(int x, int y) {
        super(x, y);
    }
}



/**
 * 子弹
 */
class Shot implements Runnable {
    int x;
    int y;
    int direct;
    int color;
    int speed = 1;
    Boolean isLive = true;

    public Shot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(Boolean isLive) {
        this.isLive = isLive;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (direct) {
                // 上
                case 0:
                    y -= speed;
                    break;
                // 下
                case 1:
                    y += speed;
                    break;
                // 左
                case 2:
                    x -= speed;
                    break;
                // 右
                case 3:
                    x += speed;
                    break;
            }
            // 子弹消失到边界
            if (x < 0 || x > 400 || y < 0 || y > 300) {
                this.isLive = false;
                break;
            }
//            System.out.println(x);
        }
    }
}

/**
 * 我的子弹
 */
class HeroShot extends Shot {
    public HeroShot(int x, int y) {
        super(x, y);
        super.setSpeed(1);
    }
}

