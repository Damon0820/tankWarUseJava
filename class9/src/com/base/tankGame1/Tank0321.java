package com.base.tankGame1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tank0321 extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args) {
        Tank0321 start = new Tank0321();
    }

    public Tank0321() {
        mp = new MyPanel();
        this.add(mp);
        this.addKeyListener(mp);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

class MyPanel extends JPanel implements KeyListener {

    Hero hero = null;

    public MyPanel() {
        hero = new Hero(10, 10);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 400, 300);
        this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
//        this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
//        this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
//        this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
    }

    // 画坦克
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0 :
                g.setColor(Color.CYAN);
                break;
            case 1 :
                g.setColor(Color.yellow);
                break;
        }

        switch (direct) {
            // 上
            case 0:
                g.fill3DRect(x, y, 5, 30, false);
                g.fill3DRect(x + 5, y + 5, 10, 20, false);
                g.fill3DRect(x + 15, y, 5, 30, false);
                g.fillOval(x + 5, y + 10, 10, 10);
                g.drawLine(x + 10, y + 10, x + 10, y);
                break;
            // 下
            case 1:
//                x = x + 50;
//                y = y + 50;
                g.fill3DRect(x, y, 5, 30, false);
                g.fill3DRect(x + 5, y + 5, 10, 20, false);
                g.fill3DRect(x + 15, y, 5, 30, false);
                g.fillOval(x + 5, y + 10, 10, 10);
                g.drawLine(x + 10, y + 10, x + 10, y + 30);
                break;
            // 左
            case 2:
//                x = x + 100;
//                y = y + 100;
                g.fill3DRect(x, y, 30, 5, false);
                g.fill3DRect(x + 5, y + 5, 20, 10, false);
                g.fill3DRect(x, y + 15, 30, 5, false);
                g.fillOval(x + 10, y + 5, 10, 10);
                g.drawLine(x + 10, y + 10, x, y + 10);
                break;
            // 右
            case 3:
//                x = x + 150;
//                y = y + 150;
                g.fill3DRect(x, y, 30, 5, false);
                g.fill3DRect(x + 5, y + 5, 20, 10, false);
                g.fill3DRect(x, y + 15, 30, 5, false);
                g.fillOval(x + 10, y + 5, 10, 10);
                g.drawLine(x + 10, y + 10, x + 30, y + 10);
                break;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
//        System.out.println("01");
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println('press key');
        System.out.println("11");
        int offset = 5;

        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                hero.moveUp();
                hero.setDirect(0);
                break;
            case KeyEvent.VK_DOWN:
                hero.moveDown();
                hero.setDirect(1);

                break;
            case KeyEvent.VK_LEFT:
                hero.moveLeft();
                hero.setDirect(2);

                break;
            case KeyEvent.VK_RIGHT:
                hero.moveRight();
                hero.setDirect(3);

                break;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println("31");
    }
}

class Tank {

    int x = 0;
    int y = 0;
    int speed = 2;
    int direct = 0;
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

class Hero extends Tank {
    public Hero(int x, int y) {
        super(x, y);
    }
}
