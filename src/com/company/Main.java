package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main extends JFrame {

    static final int w = 1366;
    static final int h = 768;

    public static void draw(Graphics2D g) {
//Создаем буффер в который рисуем кадр.
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//Рисуем кадр.

// for (int i = 0; i < 16; i++) {
// Render.renderLine(img, 400, 400, (int) (200 * Math.cos(Math.PI * i / (16 / 2)) + 400), (int) (200 * Math.sin(Math.PI * i / (16 / 2)) + 400));
// }
        Render.renderTrevugolnik(img, 100, 500, 300, 100, 500, 500);
        Render.renderTrevugolnik(img, 100, 200, 300, 600, 500, 200);

        g.drawImage(img, 0, 0, null);
    }

    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException {
        Main jf = new Main();
        jf.setSize(w, h);//размер экрана
        jf.setUndecorated(false);//показать заголовок окна
        jf.setTitle("ГнегГнег");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.createBufferStrategy(2);
//в бесконечном цикле рисуем новый кадр
        while (true) {
            long frameLength = 1000 / 60; //пытаемся работать из рассчета 60 кадров в секунду
            long start = System.currentTimeMillis();
            BufferStrategy bs = jf.getBufferStrategy();
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.clearRect(0, 0, jf.getWidth(), jf.getHeight());
            draw(g);

            bs.show();
            g.dispose();

            long end = System.currentTimeMillis();
            long len = end - start;
            if (len < frameLength) {
                Thread.sleep(frameLength - len);
            }
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    //Вызывается когда клавиша отпущена пользователем, обработка события аналогична keyPressed
    public void keyReleased(KeyEvent e) {

    }
}

class Render {

    public static void renderLine(BufferedImage img, int x1, int y1, int x2, int y2) {
        if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
            if (x1 < x2) {
                for (int x = x1; x <= x2; x++) {
                    int y = ((x - x1) * (y2 - y1) / (x2 - x1)) + y1;
                    img.setRGB(x, y, new Color(0, 0, 0).getRGB());
                }
            }
            if (x1 >= x2) {
                for (int x = x2; x <= x1; x++) {
                    int y = ((x - x1) * (y2 - y1) / (x2 - x1)) + y1;
                    img.setRGB(x, y, new Color(0, 0, 0).getRGB());
                }
            }

        }
        if (Math.abs(y1 - y2) >= Math.abs(x1 - x2)) {
            if (y1 < y2) {
                for (int y = y1; y <= y2; y++) {
                    int x = (((y - y1) * (x2 - x1) / (y2 - y1)) + x1);
                    img.setRGB(x, y, new Color(0, 0, 0).getRGB());

                }
            }
            if (y1 >= y2) {
                for (int y = y2; y <= y1; y++) {
                    int x = ((y - y1) * (x2 - x1) / (y2 - y1)) + x1;
                    img.setRGB(x, y, new Color(0, 0, 0).getRGB());
                }
            }

        }
    }
    public static void renderTrevugolnik(BufferedImage img, int x1, int y1, int x2, int y2, int x3, int y3){
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                double A = (x1-i)*(y2-y1)-(x2-x1)*(y1-j);
                double B = (x2-i)*(y3-y2)-(x3-x2)*(y2-j);
                double C = (x3-i)*(y1-y3)-(x1-x3)*(y3-j);
                if(Math.signum(A)==Math.signum(B) && Math.signum(B)==Math.signum(C)){
                    img.setRGB(i, j, new Color(3, 1, 218).getRGB());
                }
                if((A==0)&&(B==0)&&(C==0)){
                    img.setRGB(i, j, new Color(3, 1, 218).getRGB());
                }
            }
        }
    }
}