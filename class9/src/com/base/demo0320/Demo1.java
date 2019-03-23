package com.base.demo0320;

import javax.swing.*;
import java.awt.*;

public class Demo1 extends JFrame {

    MyPanel mp = null;
    public static void main(String[] args) {
        Demo1 dm = new Demo1();
    }

    public Demo1() {
        mp = new MyPanel();
        this.add(mp);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

class MyPanel extends JPanel {

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawOval(20, 20, 100, 100);
    }
}
