package bgu.spl.a2.test;

import bgu.spl.a2.Task;

import java.util.ArrayList;
import java.util.List;

public class SumMatrix extends Task<int[]> {
    private int[][] array;

    public SumMatrix(int[][] array) {
        this.array = array;
    }

    protected void start() {
        int sum = 0;
        List<Task<Integer>> tasks = new ArrayList<>();
        int rows = array.length;
        for (int i = 0; i < rows; i++) {
            SumRow newTask = new SumRow(array, i);
            spawn(newTask);
            tasks.add(newTask);
        }
        whenResolved(tasks, () -> {
                    int[] res = new int[rows];
                    for (int j = 0; j < rows; j++) {
                        res[j] = tasks.get(j).getResult().get();
                    }
                    /********* print for debug *********/
                    System.out.println("Complete all the RowSum:");
                    for (int i = 0; i < res.length; i++) {
                        System.out.print(res[i] + " ");
                    }
                    System.out.println();
                    /***********************************/
                    complete(res);
                }
        );
    }
}