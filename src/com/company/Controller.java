package com.company;

import java.util.concurrent.TimeUnit;

public class Controller {
    private View view;
    private int[][] tiles = new int[4][4];
    private Direction direction;
    private int score = 0;
    private boolean isGameOver;
    private boolean noShift;
    private boolean noSum;

    public void setView(View view) {
        this.view = view;
    }

    public void startGame() {
        createTile();
        createTile();
    }

    public void step() {
        if (view.freeTiles() == 15) {
            view.drawGameOver();
            return;
        }
        switch (direction) {
            case RIGHT -> {
                sumRight();
                shiftRight();
            }
            case LEFT -> {
                sumLeft();
                shiftLeft();
            }
            case DOWN -> {
                sumDown();
                shiftDown();
            }
            case UP -> {
                sumUp();
                shiftUp();
            }
        }
        if (!noShift || !noSum) {
            createTile();
        }
    }

    public void createTile() {
        int x = 0;
        int y = 0;
        int count = 0;
        while (true) {
            x = (int) (Math.random() * 4);
            y = (int) (Math.random() * 4);
            if (tiles[y][x] != 0) {
                count++;
                continue;
            }
            break;
        }
        view.removeFragment(x, y);
        int value = Math.random() > 0.1 ? 2 : 4;
        tiles[y][x] = value;
        view.drawFragment(x, y, value);
    }

    public void handleKeyDown(int key) {
        if (key == 39) {
            direction = Direction.RIGHT;
        }
        if (key == 37) {
            direction = Direction.LEFT;
        }
        if (key == 38) {
            direction = Direction.UP;
        }
        if (key == 40) {
            direction = Direction.DOWN;
        }
        step();
    }

    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tilesSwitchHorizontal(int y, int x, int position) {
        int tmp = tiles[y][x];
        view.removeFragment(x, y);
        tiles[y][x] = tiles[y][position];
        view.removeFragment(position, y);
        view.drawFragment(x, y, tiles[y][x]);
        tiles[y][position] = tmp;
        view.drawFragment(position, y, tiles[y][position]);
    }

    private void tilesSwitchVertical(int x, int y, int position) {
        int tmp = tiles[y][x];
        view.removeFragment(x, y);
        tiles[y][x] = tiles[position][x];
        view.removeFragment(x, position);
        view.drawFragment(x, y, tiles[y][x]);
        tiles[position][x] = tmp;
        view.drawFragment(x, position, tiles[position][x]);
    }

    public void sumRight() {
        int sumTmp = score;
        for (int y = 0; y < tiles.length; y++) {
            int sum = 0;
            int index = tiles.length - 1;
            for (int i = tiles.length - 1; i >= 0; i--) {
                if (tiles[y][i] != 0) {
                    if (tiles[y][i] == tiles[y][index] && sum != 0) {
                        tiles[y][i] = 0;
                        view.removeFragment(i, y);
                        view.drawFragment(i, y, 0);
                        tiles[y][index] = tiles[y][index] * 2;
                        view.removeFragment(index, y);
                        view.drawFragment(index, y, tiles[y][index]);
                        score += tiles[y][index];
                        view.setScore(score);
                        sum = 0;
                        index = tiles.length - 1;
                        continue;
                    }
                    sum = tiles[y][i];
                    index = i;
                }
            }
        }
        if (sumTmp == score) {
            noSum = true;
        } else {
            noSum = false;
        }
    }

    public void shiftRight() {
        int countShift = 0;
        for (int y = 0; y < tiles.length; y++) {
            for (int i = tiles.length - 2; i >= 0; i--) {
                if (tiles[y][i] != 0) {
                    while (i != tiles.length - 1 && tiles[y][i + 1] == 0) {
                        tilesSwitchHorizontal(y, i, i + 1);
                        i++;
                        countShift++;
                    }
                }
            }
        }
        if (countShift == 0) {
            noShift = true;
        } else {
            noShift = false;
        }
    }

    public void sumLeft() {
        int sumTmp = score;
        for (int y = 0; y < tiles.length; y++) {
            int sum = 0;
            int index = 0;

            for (int i = 0; i < tiles.length; i++) {
                if (tiles[y][i] != 0) {
                    if (tiles[y][i] == tiles[y][index] && sum != 0) {
                        tiles[y][i] = 0;
                        view.removeFragment(i, y);
                        view.drawFragment(i, y, 0);
                        tiles[y][index] = tiles[y][index] * 2;
                        view.removeFragment(index, y);
                        view.drawFragment(index, y, tiles[y][index]);
                        score += tiles[y][index];
                        view.setScore(score);
                        sum = 0;
                        index = 0;
                        continue;
                    }
                    sum = tiles[y][i];
                    index = i;
                }
            }
        }
        if (sumTmp == score) {
            noSum = true;
        } else {
            noSum = false;
        }
    }

    public void shiftLeft() {
        int countShift = 0;
        for (int y = 0; y < tiles.length; y++) {
            for (int i = 1; i < tiles.length; i++) {
                if (tiles[y][i] != 0) {
                    while (i != 0 && tiles[y][i - 1] == 0) {
                        tilesSwitchHorizontal(y, i, i - 1);
                        i--;
                        countShift++;
                    }
                }
            }
        }
        if (countShift == 0) {
            noShift = true;
        } else {
            noShift = false;
        }
    }

    public void sumDown() {
        int sumTmp = score;
        for (int x = 0; x < tiles.length; x++) {
            int sum = 0;
            int index = tiles.length - 1;

            for (int i = tiles.length - 1; i >= 0; i--) {
                if (tiles[i][x] != 0) {
                    if (tiles[i][x] == tiles[index][x] && sum != 0) {
                        tiles[i][x] = 0;
                        view.removeFragment(x, i);
                        view.drawFragment(x, i, 0);
                        tiles[index][x] = tiles[index][x] * 2;
                        view.removeFragment(x, index);
                        view.drawFragment(x, index, tiles[index][x]);
                        score += tiles[index][x];
                        view.setScore(score);
                        sum = 0;
                        index = tiles.length - 1;
                        continue;
                    }
                    sum = tiles[i][x];
                    index = i;
                }
            }
        }
        if (sumTmp == score) {
            noSum = true;
        } else {
            noSum = false;
        }
    }

    public void shiftDown() {
        int countShift = 0;
        for (int x = 0; x < tiles.length; x++) {
            for (int i = tiles.length - 2; i >= 0; i--) {
                if (tiles[i][x] != 0) {
                    while (i != tiles.length - 1 && tiles[i + 1][x] == 0) {
                        tilesSwitchVertical(x, i, i + 1);
                        i++;
                        countShift++;
                    }
                }
            }
        }
        if (countShift == 0) {
            noShift = true;
        } else {
            noShift = false;
        }
    }

    public void sumUp() {
        int sumTmp = score;
        for (int x = 0; x < tiles.length; x++) {
            int sum = 0;
            int index = 0;

            for (int i = 0; i < tiles.length; i++) {
                if (tiles[i][x] != 0) {
                    if (tiles[i][x] == tiles[index][x] && sum != 0) {
                        tiles[i][x] = 0;
                        view.removeFragment(x, i);
                        view.drawFragment(x, i, 0);
                        tiles[index][x] = tiles[index][x] * 2;
                        view.removeFragment(x, index);
                        view.drawFragment(x, index, tiles[index][x]);
                        score += tiles[index][x];
                        view.setScore(score);
                        sum = 0;
                        index = 0;
                        continue;
                    }
                    sum = tiles[i][x];
                    index = i;
                }
            }
        }
        if (sumTmp == score) {
            noSum = true;
        } else {
            noSum = false;
        }
    }

    public void shiftUp() {
        int countShift = 0;
        for (int x = 0; x < tiles.length; x++) {
            for (int i = 1; i < tiles.length; i++) {
                if (tiles[i][x] != 0) {
                    while (i != 0 && tiles[i - 1][x] == 0) {
                        tilesSwitchVertical(x, i, i - 1);
                        i--;
                        countShift++;
                    }
                }
            }
        }
        if (countShift == 0) {
            noShift = true;
        } else {
            noShift = false;
        }
    }
}