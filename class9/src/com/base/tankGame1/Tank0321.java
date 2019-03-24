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
        if (heroShot != null) {
            this.drawShot(heroShot.getX(), heroShot.getY(), g, heroShot.getDirect(), 1);
        }
        for (int i = 0; i < ets.size(); i++) {
            EnemyTank et = ets.get(i);
            this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
        }
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

//        System.out.println('x' + x);
//        System.out.println('y' + x);

        switch (direct) {
            // 上
            case 0:
                g.fillOval(x + 10, y, 5, 5);
                break;
            // 下
            case 1:
                g.fillOval(x + 10, y + 30, 5, 5);
                break;
            // 左
            case 2:
                g.fillOval(x, y + 10, 1, 1);

                break;
            // 右
            case 3:
                g.fillOval(x + 30, y + 10, 1, 1);

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
                heroShot = new HeroShot(hero.getX(), hero.getY());
//                hero.emitShot(heroShot);
                heroShot.setDirect(hero.direct);
                Thread thread = new Thread(heroShot);
                thread.start();
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
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(11111);
            repaint();
        }

    }
}

