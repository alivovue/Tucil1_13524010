package com.mycompany.app.backend.data;

import java.util.ArrayList;

// solutions is array containing the positions of queens in which columns in each rows
// so, solution [1,2,3] means having a queen at row 1 column 1, row 2 column 2, row 3 column 3

public class SolutionObject {
    private ArrayList<int[]> solutions;
    private int time;
    private int iteration;

    public SolutionObject() {
        solutions = new ArrayList<>();
        time = 0;
        iteration = 0;
    }

    // setter
    public void addSolution(int[] solution) {
        solutions.add(solution.clone());
    }

    public void setTime(int t) {
        time = t;
    }

    public void setIteration(int it) {
        iteration = it;
    }

    public void incrementIteration() {
        iteration++;
    }


    // getter
    public ArrayList<int[]> getSolutions() {
        return solutions;
    }

    public int getTime() {
        return time;
    }

    public int getIteration() {
        return iteration;
    }

    public void clearSolutions() {
        solutions.clear();
    }
}
