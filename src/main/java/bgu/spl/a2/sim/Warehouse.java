package bgu.spl.a2.sim;

import bgu.spl.a2.sim.tools.GCD_Screwdriver;
import bgu.spl.a2.sim.tools.NextPrimeHammer;
import bgu.spl.a2.sim.tools.RandomSumPliers;
import bgu.spl.a2.sim.tools.Tool;
import bgu.spl.a2.sim.conf.ManufactoringPlan;
import bgu.spl.a2.Deferred;
import bgu.spl.a2.Task;
import bgu.spl.a2.test.MergeSort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A class representing the warehouse in your simulation
 * <p>
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add to this class can
 * only be private!!!
 */
public class Warehouse {
        HashMap<String,ManufactoringPlan> mpList;
        ConcurrentLinkedQueue<GCD_Screwdriver> gcd;
        ConcurrentLinkedQueue<RandomSumPliers> rdm;
        ConcurrentLinkedQueue<NextPrimeHammer> prm;
    /**
     * Constructor
     */
    public Warehouse(){
        mpList = new HashMap<>();
        gcd = new ConcurrentLinkedQueue<>();
        rdm = new ConcurrentLinkedQueue<>();
        prm = new ConcurrentLinkedQueue<>();
    }

    /**
     * ToolJson acquisition procedure
     * Note that this procedure is non-blocking and should return immediatly
     *
     * @param type - string describing the required tool
     * @return a deferred promise for the  requested tool
     */
    public Deferred<Tool> acquireTool(String type){

    }

    /**
     * ToolJson return procedure - releases a tool which becomes available in the warehouse upon completion.
     *
     * @param tool - The tool to be returned
     */
    public void releaseTool(Tool tool){
        tool.accept(this);
    }
    private void releaseTool(GCD_Screwdriver tool){
        gcd.add(tool);
    }
    private void releaseTool(NextPrimeHammer tool){
        prm.add(tool);
    }
    private void releaseTool(RandomSumPliers tool){
        rdm.add(tool);
    }

    /**
     * Getter for ManufactoringPlans
     *
     * @param product - a string with the product name for which a ManufactoringPlan is desired
     * @return A ManufactoringPlan for product
     */
    public ManufactoringPlan getPlan(String product){
        return mpList.get(product);
    }

    /**
     * Store a ManufactoringPlan in the warehouse for later retrieval
     *
     * @param plan - a ManufactoringPlan to be stored
     */
    public void addPlan(ManufactoringPlan plan){
        mpList.put(plan.getProductName(),plan);
    }

    /**
     * Store a qty Amount of tools of type tool in the warehouse for later retrieval
     *
     * @param tool - type of tool to be stored
     * @param qty  - amount of tools of type tool to be stored
     */
    public void addTool(Tool tool, int qty){
        switch(tool.getType()){
            case "gs-driver":
                for(int i=0;i<qty;i++){
                    gcd.add(new GCD_Screwdriver());
                }
            case "np-hammer":
                for(int i=0;i<qty;i++){
                    prm.add(new NextPrimeHammer());
                }
            case "rs-pliers":
                for(int i=0;i<qty;i++){
                    rdm.add(new RandomSumPliers());
                }
        }
    }

}
