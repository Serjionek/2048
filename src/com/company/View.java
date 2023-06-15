package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class View {
    private ImageIcon icon = new ImageIcon("C:\\Users\\serge\\IdeaProjects\\2048\\src\\Images\\icon.jpg");
    private Controller controller;
    private JFrame frame = new JFrame("2048");
    private JLabel[][] fragments = new JLabel[4][4];
    private JLabel score = new JLabel("Score: ");
    private Map<Integer,ImageIcon> icons = new HashMap<>();

    public void setScore(int a) {
        score.setText("Score: " + a);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void create() {
        frame.setSize(415, 600);
        frame.setIconImage(icon.getImage());
        frame.setUndecorated(false);
        frame.setBackground(Color.YELLOW);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        score.setBounds(50, 450, 100, 50);
        frame.add(score);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.handleKeyDown(e.getKeyCode());
            }
        });

        createIcons();
        drawGameField();
        frame.repaint();
    }

    public int freeTiles() {
        int countTiles = 0;
        for (int i = 0; i < fragments.length; i++) {
            for (int j = 0; j < fragments[i].length; j++) {
                countTiles++;
            }
        }
        return countTiles;
    }

    public void drawGameOver() {
        for (int i = 0; i < fragments.length; i++) {
            for (int j = 0; j < fragments[i].length; j++) {
                removeFragment(i, j);
            }
        }
        JLabel gameOver = new JLabel("GAME OVER");
        gameOver.setBounds(200, 200, 100, 50);
        frame.add(gameOver);
        frame.repaint();
    }

    public void drawFragment(int x, int y, int value) {
        JLabel fragment;
        if (value == 0) {
            fragment = new JLabel(new ImageIcon("C:\\Users\\serge\\IdeaProjects\\True2048\\src\\Images\\empty.jpg"));
        } else {
            fragment = new JLabel(icons.get(value));
        }
        fragment.setBounds(x * 100, y * 100, 100, 100);
        fragments[y][x] = fragment;
        frame.add(fragment);
        frame.repaint();
    }

    public void removeFragment(int x, int y) {
        frame.remove(fragments[y][x]);
        fragments[y][x] = null;
        frame.repaint();
    }

    public void drawGameField() {
        String path = "C:\\Users\\serge\\IdeaProjects\\2048\\src\\Images\\empty.jpg";
        for (int i = 0; i < fragments.length; i++) {
            for (int j = 0; j < fragments[i].length; j++) {
                JLabel fragment = new JLabel(new ImageIcon(path));
                fragment.setBounds(j * 100, i * 100, 100, 100);
                fragments[i][j] = fragment;
                frame.add(fragment);
            }
        }
    }

    public void createIcons() {
        for (int i = 2; i < 2048; i *= 2) {
            icons.put(i, new ImageIcon("C:\\Users\\serge\\IdeaProjects\\2048\\src\\Images\\" + i + ".jpg"));
        }
    }
}