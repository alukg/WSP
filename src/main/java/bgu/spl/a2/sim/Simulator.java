/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;

import bgu.spl.a2.WorkStealingThreadPool;
import bgu.spl.a2.sim.conf.ManufactoringPlan;
import bgu.spl.a2.sim.json.*;
import bgu.spl.a2.sim.tasks.Manufacture;
import bgu.spl.a2.sim.tools.GCD_Screwdriver;
import bgu.spl.a2.sim.tools.NextPrimeHammer;
import bgu.spl.a2.sim.tools.RandomSumPliers;
import bgu.spl.a2.sim.tools.Tool;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {
    private static WorkStealingThreadPool WSP;
    private static Warehouse warehouse;

    /**
     * Begin the simulation
     * Should not be called before attachWorkStealingThreadPool()
     */
    public static ConcurrentLinkedQueue<Product> start() throws InterruptedException {
        warehouse = new Warehouse();

        FactoryPlan data = null;
        try {
            String JSON_PATH = "Simulation.json";
            Gson gson = new Gson();
            Type ReviewType = new TypeToken<FactoryPlan>() {
            }.getType();
            JsonReader reader = new JsonReader(new FileReader(JSON_PATH));
            data = gson.fromJson(reader, ReviewType);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Can't format json object");
            System.exit(2);
        }

        int numOfThreads = data.getThreads();
        getDataFromFactoryPlan(data);
        attachWorkStealingThreadPool(new WorkStealingThreadPool(numOfThreads));

        ConcurrentLinkedQueue<Manufacture> completedTasks = new ConcurrentLinkedQueue<>();
        List<List<Series>> waves = data.getWaves();

        WSP.start();

        for (List<Series> wave : waves) {
            int numOfProducts = 0;
            for (Series series : wave) {
                numOfProducts += series.getQty();
            }
            CountDownLatch l = new CountDownLatch(numOfProducts);

            for (Series series : wave) {
                long startId = series.getStartId();
                String seriesName = series.getProduct();
                for (int i = 0; i <= series.getQty() - 1; i++) {
                    Manufacture task = new Manufacture(new Product(startId + i, seriesName), warehouse);
                    WSP.submit(task);
                    completedTasks.add(task);
                    task.getResult().whenResolved(() -> {
                        /******* debug ***********/
                        System.out.println("Start ID: " + task.getResult().get().getStartId() + " , FinalID: " + task.getResult().get().getFinalId());
                        /*************************/
                        l.countDown();
                    });
                }
            }
            System.out.println("Submit " + numOfProducts + " Products.");
            l.await();
        }
        ConcurrentLinkedQueue<Product> simulationResult = new ConcurrentLinkedQueue<>();
        for (Manufacture task : completedTasks) {
            simulationResult.add(task.getResult().get());
        }

        return simulationResult;
    }

    /**
     * attach a WorkStealingThreadPool to the Simulator, this WorkStealingThreadPool will be used to run the simulation
     *
     * @param myWorkStealingThreadPool - the WorkStealingThreadPool which will be used by the simulator
     */
    public static void attachWorkStealingThreadPool(WorkStealingThreadPool myWorkStealingThreadPool) {
        WSP = myWorkStealingThreadPool;
    }

    public static void main(String[] args) throws InterruptedException {
        ConcurrentLinkedQueue<Product> result = start();
    }

    private static void getDataFromFactoryPlan(FactoryPlan data) {
        List<Plan> planListFromJson = data.getPlans();
        List<ToolJson> toolsListFromJson = data.getToolJsons();

        planListFromJson.forEach(plan -> {
            ManufactoringPlan manufactoringPlan = new ManufactoringPlan(plan.getProduct(), plan.getParts(), plan.getTools());
            warehouse.addPlan(manufactoringPlan);
        });

        toolsListFromJson.forEach(toolJson -> {
            Tool tool = null;
            switch (toolJson.getTool()) {
                case "gs-driver":
                    tool = new GCD_Screwdriver();
                    break;
                case "np-hammer":
                    tool = new NextPrimeHammer();
                    break;
                case "rs-pliers":
                    tool = new RandomSumPliers();
                    break;
            }
            warehouse.addTool(tool, toolJson.getQty());
        });
    }
}
