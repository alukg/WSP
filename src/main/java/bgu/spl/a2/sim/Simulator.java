/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;

import bgu.spl.a2.WorkStealingThreadPool;
import bgu.spl.a2.sim.json.FactoryPlan;
import bgu.spl.a2.sim.tools.Tool;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {
	private static WorkStealingThreadPool WSP;

	public Simulator(WorkStealingThreadPool myWorkStealingThreadPool) {
		this.WSP = myWorkStealingThreadPool;
	}

	/**
	 * Begin the simulation
	 * Should not be called before attachWorkStealingThreadPool()
	 */

	public static ConcurrentLinkedQueue<Product> start() {
		ConcurrentLinkedQueue<Product> SimulationResult;

		return null;
	}

	/**
	 * attach a WorkStealingThreadPool to the Simulator, this WorkStealingThreadPool will be used to run the simulation
	 *
	 * @param myWorkStealingThreadPool - the WorkStealingThreadPool which will be used by the simulator
	 */
	public static void attachWorkStealingThreadPool(WorkStealingThreadPool myWorkStealingThreadPool) {
		WSP = myWorkStealingThreadPool;
	}

	public static void main(String[] args) {
		final int numofThreads;
		ArrayList<Tool> tools = new ArrayList<>();
		FactoryPlan data = null;
		try {
			String JSON_PATH = "Wave.json";
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


		Simulator simulator = new Simulator();

		getDataFromFactoryPlan(data);
	}

	private static void getDataFromFactoryPlan(FactoryPlan data){
		data.getPlans();
	}
}
