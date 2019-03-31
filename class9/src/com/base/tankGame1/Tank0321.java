package com.base.tankGame1;

import javafx.scene.layout.Pane;
import sun.awt.image.ToolkitImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
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
    // 三张图片，组成爆炸效果💥
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    // 炸弹
    Vector<Bomb> bombs = new Vector<Bomb>();


    public MyPanel() {
        hero = new Hero(100, 200);
        for (int i = 0; i < enSize; i++) {
            EnemyTank et = new EnemyTank((i + 1) * 50, 0);
            et.setDirect(1);
            Thread thread = new Thread(et);
            thread.start();
            ets.add(et);
        }
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 400, 300);
        // 画我方tank
        if (hero.isLive) {
            this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }
        // 画我方子弹
        for (int i = 0; i < hero.heroShots.size(); i++) {
            HeroShot heroShot = hero.heroShots.get(i);
            if (heroShot.isLive) {
                this.drawShot(heroShot.getX(), heroShot.getY(), g, heroShot.getDirect(), 1);
                // 是否击中敌人
                for (int j = 0; j < this.ets.size(); j++) {
                    EnemyTank enemyTank = this.ets.get(j);
                    if (!enemyTank.isLive) continue;
                    Boolean isHit = this.hitTank(heroShot, enemyTank);
                    if (isHit) {
                        System.out.println(1111);
                        enemyTank.setIsLive(false);
                        heroShot.setIsLive(false);
                        this.ets.remove(enemyTank);
                        this.bombs.add(new Bomb(enemyTank.getX(), enemyTank.getY()));
                    }
                }
            } else {
                hero.heroShots.remove(heroShot);
            }
        }

        // 画敌方tank
        for (int i = 0; i < ets.size(); i++) {
            EnemyTank et = ets.get(i);
            // 判断tank重叠
//            for (int k = 0; k < ets.size(); k++) {
//                EnemyTank et2 = ets.get(k);
//                // 重叠，则改变方向
//                if (this.tankOverlop(et, et2)) {
//                    System.out.println("cd" + et.x + et.y);
//                    System.out.println("cd2" + et2.x + et2.y);
//                    // 同一条线上
//                    if ((et.direct == 0 || et.direct == 1) && (et2.direct == 0 || et2.direct == 1)) {
//                        if (et.direct == 0) {
//                            et.setDirect(1);
//                            et2.setDirect(0);
//                        } else {
//                            et.setDirect(0);
//                            et2.setDirect(1);
//                        }
//
//                    } else if ((et.direct == 2 || et.direct == 3) && (et2.direct == 2 || et2.direct == 3)) {
//                        if (et.direct == 2) {
//                            et.setDirect(3);
//                            et2.setDirect(2);
//                        } else {
//                            et.setDirect(2);
//                            et2.setDirect(3);
//                        }
//                    // 不同线上，垂直方向
//                    } else  {
//                        switch (et.direct) {
//                            case 0:
//                                et.setDirect(1);
//                                break;
//                            case 1:
//                                et.setDirect(0);
//                                break;
//                            case 2:
//                                et.setDirect(3);
//                                break;
//                            case 3:
//                                et.setDirect(2);
//                                break;
//                        }
//                        switch (et2.direct) {
//                            case 0:
//                                et2.setDirect(1);
//                                break;
//                            case 1:
//                                et2.setDirect(0);
//                                break;
//                            case 2:
//                                et2.setDirect(3);
//                                break;
//                            case 3:
//                                et2.setDirect(2);
//                                break;
//                        }
//                    }
//                }
//            }
            this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
            // 画敌方子弹
            for (int j = 0; j < et.enemyShots.size(); j++) {
                EnemyShot enemyShot = et.enemyShots.get(j);
                if (enemyShot.isLive) {
                    this.drawShot(enemyShot.getX(), enemyShot.getY(), g, enemyShot.getDirect(), 1);
                    Boolean isHit = this.hitTank(enemyShot, hero);
                    if (isHit) {
                        hero.setIsLive(false);
                        enemyShot.setIsLive(false);
                        System.out.println("我方tank挂了");
                        this.bombs.add(new Bomb(hero.getX(), hero.getY()));
                    }
                } else {
                    et.enemyShots.remove(enemyShot);
                }
            }
        }

        // 画出炸弹
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.getX(), bomb.getY(), 30, 30, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.getX(), bomb.getY(), 30, 30, this);
            } else {
                g.drawImage(image3, bomb.getX(), bomb.getY(), 30, 30, this);
            }
            bomb.lifeDown();
            if (!bomb.isLive) {
                bombs.remove(bomb);
            }

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
//        System.out.println(tankX);
//        System.out.println(tankY);
//        System.out.println(shotX);
//        System.out.println(shotX);
//        System.out.println(shotX);
//        System.out.println(shotX);
        if (tankX <= shotX && shotX <= tankX2 && tankY <= shotY && shotY <= tankY2) {
            return true;
        } else {
            return false;
        }
    }

    // 判断tank是否重叠
    public Boolean tankOverlop(Tank tank1, Tank tank2) {
        if (tank1.x <= tank2.getX2() && tank1.getX2() >= tank2.x && tank1.y <= tank2.getY2() && tank1.getY2() >= tank2.y) {
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

