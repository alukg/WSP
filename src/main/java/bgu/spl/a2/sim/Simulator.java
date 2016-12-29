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
import bgu.spl.a2.test.MergeSort;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {
	private static WorkStealingThreadPool WSP;
	private Warehouse warehouse;

	public Simulator(WorkStealingThreadPool myWorkStealingThreadPool) {
		this.WSP = myWorkStealingThreadPool;
		warehouse = new Warehouse();
	}

	/**
	 * Begin the simulation
	 * Should not be called before attachWorkStealingThreadPool()
	 */

	public static ConcurrentLinkedQueue<Product> start() {
		ConcurrentLinkedQueue<Product> SimulationResult;
		ConcurrentLinkedQueue<Manufacture> completedTasks;



		List<List<Series>> waves = data.getWaves();


		CountDownLatch l = new CountDownLatch();
		WSP.submit(task);
		WSP.start();
		task.getResult().whenResolved(() -> {
//			/******* debug ***********/
//			boolean ans = true;
//			int length = task.getResult().get().length;
//			for (int i = 1; i < length; i++) {
//				if (task.getResult().get()[i] < task.getResult().get()[i - 1]) {
//					ans = false;
//					break;
//				}
//			}
//			System.out.println();
//			System.out.println(ans);
//			/*************************/
			l.countDown();
		});

		l.await();

	}

	/**
	 * attach a WorkStealingThreadPool to the Simulator, this WorkStealingThreadPool will be used to run the simulation
	 *
	 * @param myWorkStealingThreadPool - the WorkStealingThreadPool which will be used by the simulator
	 */
	public static void attachWorkStealingThreadPool(WorkStealingThreadPool myWorkStealingThreadPool) {
		WSP = myWorkStealingThreadPool;
	}

	public static void main(String[] args) throws InterruptedException{
		final int numofThreads;
		ArrayList<Tool> tools = new ArrayList<>();
		FactoryPlan data = null;
		try {
			String JSON_PATH = "Instructions.json";
			Gson gson = new Gson();
			Type ReviewType = new TypeToken<FactoryPlan>(){}.getType();
			JsonReader reader = new JsonReader(new FileReader(JSON_PATH));
			data = gson.fromJson(reader, ReviewType);
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			System.exit(1);
		} catch (Exception e){
			System.out.println("Can't format json object");
			System.exit(2);
		}
		
		int numOfThreads = data.getThreads();
		Simulator simulator = new Simulator(new WorkStealingThreadPool(numOfThreads));
		simulator.getDataFromFactoryPlan(data);
		simulator.data = data;
		ConcurrentLinkedQueue<Product> result = start();
	}

	private void getDataFromFactoryPlan(FactoryPlan data){
		List<Plan> planListFromJson = data.getPlans();
		List<ToolJson> toolsListFromJson = data.getToolJsons();

		planListFromJson.forEach(plan -> {
			ManufactoringPlan manufactoringPlan = new ManufactoringPlan(plan.getProduct(),plan.getParts(),plan.getTools());
			warehouse.addPlan(manufactoringPlan);
		});

		toolsListFromJson.forEach(toolJson -> {
			Tool tool = null;
			switch (toolJson.getTool()){
				case "gs-driver":
					tool = new GCD_Screwdriver();
				case "np-hammer":
					tool = new NextPrimeHammer();
				case "rs-pliers":
					tool = new RandomSumPliers();
			}
			warehouse.addTool(tool,toolJson.getQty());
		});
	}
}
