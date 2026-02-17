package com.mycompany.app.backend.data;

import java.util.function.LongConsumer;

public class BruteSolver {
    private BoardObject boardObject;
    private ColorObject colorObject;
    private SolutionObject solutionObject;
    private GameLogic gameLogic;

    public BruteSolver(BoardObject boardObject, ColorObject colorObject, SolutionObject solutionObject) {
        this.boardObject = boardObject;
        this.colorObject = colorObject;
        this.solutionObject = solutionObject;
        this.gameLogic = new GameLogic(boardObject, colorObject);
    }

    // logic : misal 3x3, bikin 1 array [1,1,1,0,0,0,..] terus nanti gerakin 1 nya ke kanan terus
    // ato mendingan bikin array queen [1,2,3] terus increment 3 nya sampe 9 -> baru nanti add 2 nya baru add lg 3 nya
    public void exhaustiveBrute(LongConsumer tick, long period) {
        long startTimer = System.nanoTime();
        int[] queen = new int[boardObject.getBoardLength()];
        for (int i = 0 ; i < boardObject.getBoardLength() ; i++) {
            queen[i] = i + 1;
        }

        int tempIter = 0;
        while (true) {
            applyToBoard(queen);

            tempIter++;
            solutionObject.incrementIteration();

            if (tick != null && period > 0 && (tempIter % period == 0)) {
                tick.accept(tempIter);
            }

            if (gameLogic.isValidBoard()) {
                solutionObject.addSolution(queen.clone());
                break;
            }
            if (!nextCombination(queen, boardObject.getBoardLength())) break;
        }
        long endTimer = System.nanoTime();
        int timeTaken = (int) ((endTimer - startTimer) / 1000000);
        solutionObject.setTime(timeTaken);

        if (tick != null) tick.accept(tempIter);
    }

    public boolean nextCombination(int[] queen, int n) {
        int max = n*n;
        for (int i = queen.length - 1 ; i >= 0 ; i--) {
            int limit = max - (queen.length - 1 - i);

            if (queen[i] < limit) {
                queen[i]++;
                for (int j = i + 1 ; j < queen.length ; j++) {
                    queen[j] = queen[j-1] + 1;
                }
                return true;
            }
        }
        return false;
    }

    public void applyToBoard(int[] queen) {
        boardObject.resetBoard();
        for (int i = 0 ; i < queen.length ; i++) {
            int row = (queen[i] - 1)/boardObject.getBoardLength();
            int col = (queen[i] - 1)%boardObject.getBoardLength();
            boardObject.setQueen(row, col);

        }
    }

}
