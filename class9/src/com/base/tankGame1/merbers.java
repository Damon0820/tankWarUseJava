package com.base.tankGame1;

//public class merbers {
//    public merbers() {
//
//    }
//}

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
    public Hero(int x, int y) {
        super(x, y);
        super.setSpeed(6);
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
