package com.mycompany.app.backend.data;

// board = 0 no queen
// board = 1 queen

public class BoardObject {
    private int[][] board;

    public BoardObject(int size) {
        board = new int[size][size];
        resetBoard();
    }

    // getter functions

    public int[][] getBoard() {
        return board;
    }

    public int getTile(int x, int y) {
        if (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
            return board[x][y];
        }
        else {
            return -1;
        }
    }

    public int getBoardLength() {
        return board.length;
    }

    // setter functions

    public void setQueen(int x, int y) {
        if (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
            board[x][y] = 1;
        }
        else {
            throw new IllegalArgumentException("Invalid position");
        }
    }

    public void deleteQueen(int x, int y) {
        if (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
            board[x][y] = 0;
        }
        else {
            throw new IllegalArgumentException("Invalid position");
        }
    }

    public void resetBoard() {
        for (int i = 0 ; i < board.length ; i++) {
            for (int j = 0 ; j < board[i].length ; j++) {
                board[i][j] = 0;
            }
        }
    }

    public void setSize(int n) {
        this.board = new int[n][n];
    }


}
