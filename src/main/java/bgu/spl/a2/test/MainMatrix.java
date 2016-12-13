package bgu.spl.a2.test;

import bgu.spl.a2.WorkStealingThreadPool;

import java.util.Random;

/**
 * Created by guy on 13/12/16.
 */
public class MainMatrix {
    public static void main(String[] args) throws InterruptedException {
        WorkStealingThreadPool pool = new WorkStealingThreadPool(4);
        int[][] array = new int[10][10];
        randomizeMatrix(array);
        printArray(array);
        SumMatrix myTask = new SumMatrix(array);
        pool.start();
        pool.submit(myTask);
//some stuff
        pool.shutdown(); //stopping all the threads
    }

    public static void randomizeMatrix(int[][] array) {
        Random random = new Random();
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[0].length; col++) {
                array[row][col] = random.nextInt(9 - 0);
            }
        }
    }

    public static void printArray(int[][] array) {
        int rowSum;
        for (int row = 0; row < array.length; row++) {
            rowSum = 0;
            for (int col = 0; col < array[0].length; col++) {
                rowSum = rowSum + array[row][col];
                if (col == array[0].length - 1)
                    System.out.println(array[row][col] + "   |  " + rowSum);
                else
                    System.out.print(array[row][col] + "  ");
            }
        }
    }
}