package com.mycompany.app.backend.data;

public class GameLogic {
    private BoardObject boardObject;
    private ColorObject colorObject;

    public GameLogic(BoardObject boardObject, ColorObject colorObject) {
        this.boardObject = boardObject;
        this.colorObject = colorObject;
    }

    public boolean checkOccupied(int x, int y) {
        if (boardObject.getTile(x, y) == 1) {
            return false;
        }
        return true;
    }

    public boolean checkAdjacent(int x, int y) {
        for (int i = x-1 ; i <= x+1 ; i++) {
            for (int j = y-1 ; j <= y+1 ; j++) {
                if (boardObject.getTile(i, j) == -1) {
                    continue; 
                }
                if (boardObject.getTile(i, j) == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkLine(int x, int y) {
        // check horizontal line first
        for (int i = 0 ; i < boardObject.getBoardLength() ; i++) {
            if (boardObject.getTile(x, i) == 1) {
                return false;
            }
        }

        // check vertical 
        for (int i = 0 ; i < boardObject.getBoardLength() ; i++) {
            if (boardObject.getTile(i, y) == 1) {
                return false;
            }
        }

        return true;
    }

    public boolean checkColor(int x, int y) {
        int colorCode = colorObject.getColorTile(x, y);
        for (int i = 0 ; i < colorObject.getLength() ; i++) {
            for (int j = 0 ; j < colorObject.getLength() ; j++) {
                if (colorObject.getColorTile(i, j) == colorCode && boardObject.getTile(i, j) == 1) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isValidQueen(int x, int y) {
        if (!checkOccupied(x, y)) return false;
        if (!checkAdjacent(x, y)) return false;
        if (!checkColor(x, y)) return false;
        if (!checkLine(x, y)) return false;
        return true;
    }

    public boolean isValidBoard() {
        for (int i = 0 ; i < boardObject.getBoardLength() ; i++) {
            for (int j = 0 ; j < boardObject.getBoardLength() ; j++) {
                if (boardObject.getTile(i, j) == 1) {
                    boardObject.deleteQueen(i, j);
                    if (!isValidQueen(i, j)) {
                        boardObject.setQueen(i, j);
                        return false;
                    }
                    boardObject.setQueen(i, j);
                }
            }
        }
        return true;
    }
}
