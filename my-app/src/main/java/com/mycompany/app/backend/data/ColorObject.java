package com.mycompany.app.backend.data;

// 0 = default color

public class ColorObject {
    private int[][] color;
    
    public ColorObject(int size) {
        color = new int[size][size];
        resetColor();
    }

    // getter

    public int[][] getColor() {
        return color;
    }

    public int getColorTile(int x, int y) {
        if (x < color.length && x >= 0 && y < color[0].length && y >=0) {
            return color[x][y];
        }
        else {
            return -1;
        }
    }

    public int getLength() {
        return color.length;
    }

    // setter

    public void setColor(int x, int y, int colorNumber) {
        if (x < color.length && x >= 0 && y < color[0].length && y >= 0) {
            color[x][y] = colorNumber;
        }
        else {
            throw new IllegalArgumentException("invalid position");
        }
    }



    public void resetColor() {
        for (int i = 0 ; i < color.length ; i++) {
            for (int j = 0 ; j < color[i].length ; j++) {
                color[i][j] = 0;
            }
        }
    }

    public void copyBoard(ColorObject colorObject) {
        int length = colorObject.getLength();
        this.color = new int[length][length];
        for (int i = 0 ; i < color.length ; i++) {
            for (int j = 0 ; j < color.length ; j++) {
                this.color[i][j] = colorObject.getColorTile(i, j);
            }
        }
    }

    public ColorObject copyColorObject() {
        int n = getLength();
        ColorObject temp = new ColorObject(n);
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                temp.setColor(i, j, this.color[i][j]);
            }
        }
        return temp;
    }

    public void setSize(int size) {
        this.color = new int[size][size];
    }

    // checking
    public boolean isValidColor() {
        for (int i = 0 ; i < color.length ; i++) {
            for (int j = 0 ; j < color.length ; j++) {
                if (color[i][j] == 0 || color[i][j] > 26) {
                    return false;
                }
            }
        }
        return true;
    }
}
