/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;

import bgu.spl.a2.WorkStealingThreadPool;
import bgu.spl.a2.sim.tools.Tool;
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sun.org.apache.regexp.internal.RE;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SimpleTimeZone;
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
		try {
			String JSON_PATH = "Wave.json";
			Gson gson = new Gson();
			Type ReviewType = new TypeToken<String>() {
			}.getType();
			JsonReader reader = new JsonReader(new FileReader(JSON_PATH));
			JsonElement data = gson.fromJson(reader, JsonElement.class);
			numofThreads = data.getAsJsonObject().get("threads").getAsInt();
			tools = gson.fromJson(data.getAsJsonObject().get("tools"),new TypeToken<ArrayList<Tool>>(){}.getType());
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			System.exit(1);
		}

	}
}
