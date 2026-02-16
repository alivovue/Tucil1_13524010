package com.mycompany.app.backend.data;

// 0 = default color

public class ColorObject {
    private int[][] color;
    
    public ColorObject(int size) {
        color = new int[size][size];
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
            throw new IllegalArgumentException("invalid color");
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


}
