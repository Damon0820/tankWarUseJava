package com.base.tankGame1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class Tank0321 extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args) {
        Tank0321 start = new Tank0321();
    }

    public Tank0321() {
        mp = new MyPanel();
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.addKeyListener(mp);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

class MyPanel extends JPanel implements KeyListener, Runnable {

    Hero hero = null;
    // 敌人坦克的初始数量
    int enSize = 3;
    // 敌人坦克的集合
    Vector<EnemyTank> ets = new Vector<EnemyTank>();
    // 子弹
    HeroShot heroShot = null;

    public MyPanel() {
        hero = new Hero(100, 200);
        for (int i = 0; i < enSize; i++) {
            EnemyTank et = new EnemyTank((i + 1) * 50, 0);
            ets.add(et);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 400, 300);
        this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);


        for (int i = 0; i < hero.heroShots.size(); i++) {
            HeroShot heroShot = hero.heroShots.get(i);
            if (heroShot.isLive) {
                this.drawShot(heroShot.getX(), heroShot.getY(), g, heroShot.getDirect(), 1);
                // 是否击中敌人
                for (int j = 0; i < this.ets.size(); j++) {
                    EnemyTank enemyTank = this.ets.get(i);
                    Boolean isHit = this.hitTank(heroShot, enemyTank);
                    if (isHit) {
                        System.out.println(1111);
                    }
                }
            } else {
                hero.heroShots.remove(heroShot);
            }
        }

        for (int i = 0; i < ets.size(); i++) {
            EnemyTank et = ets.get(i);
            this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
        }
//        this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
//        this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
//        this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
    }

    // 判断是否击中敌人
    public Boolean hitTank(Shot shot, Tank tank) {
        int tankX = tank.getX();
        int tankY = tank.getY();
        int shotX = shot.getX();
        int shotY = shot.getY();
        int tankX2 = 0;
        int tankY2 = 0;
        switch (tank.direct) {
            case 0:
            case 1:
                tankX2 = tankX + 20;
                tankY2 = tankY + 30;
                break;
            case 2:
            case 3:
                tankX2 = tankX + 30;
                tankY2 = tankY + 20;
                break;
        }
        if (tankX <= shotX && shotX <= tankY2 && tankY <= shotY && shotY <= tankY2) {
            return true;
        } else {
            return false;
        }
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

    // 画子弹
    public void drawShot(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0 :
                g.setColor(Color.CYAN);
                break;
            case 1 :
                g.setColor(Color.yellow);
                break;
        }
        g.fillOval(x, y, 4, 4);

    }

    @Override
    public void keyTyped(KeyEvent e) {
//        System.out.println("01");
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println('press key');
//        System.out.println("11");

        // 移动坦克
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:

                hero.moveUp();
                hero.setDirect(0);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:

                hero.moveDown();
                hero.setDirect(1);

                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:

                hero.moveLeft();
                hero.setDirect(2);

                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:

                hero.moveRight();
                hero.setDirect(3);
                break;
        }

        // 发射子弹
        switch (e.getKeyCode()){
            case KeyEvent.VK_J:
                this.hero.shotEnemy();
//                heroShot = new HeroShot(hero.getX(), hero.getY());
//                hero.emitShot(heroShot);
//                heroShot.setDirect(hero.direct);
//                Thread thread = new Thread(heroShot);
//                thread.start();
                break;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println("31");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(11111);
            repaint();
        }

    }
}

