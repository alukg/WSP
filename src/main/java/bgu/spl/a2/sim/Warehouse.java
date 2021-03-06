package bgu.spl.a2.sim;

import bgu.spl.a2.sim.tools.GcdScrewdriver;
import bgu.spl.a2.sim.tools.NextPrimeHammer;
import bgu.spl.a2.sim.tools.RandomSumPliers;
import bgu.spl.a2.sim.tools.Tool;
import bgu.spl.a2.sim.conf.ManufactoringPlan;
import bgu.spl.a2.Deferred;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A class representing the warehouse in your simulation
 * <p>
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add to this class can
 * only be private!!!
 */
public class Warehouse {
    HashMap<String, ManufactoringPlan> mpList;
    ConcurrentLinkedQueue<GcdScrewdriver> gcd;
    ConcurrentLinkedQueue<RandomSumPliers> rdm;
    ConcurrentLinkedQueue<NextPrimeHammer> prm;
    ConcurrentLinkedQueue<Deferred<Tool>> gcdDefferds;
    ConcurrentLinkedQueue<Deferred<Tool>> rdmDefferds;
    ConcurrentLinkedQueue<Deferred<Tool>> prmDefferds;
    Integer lockGCD;
    Integer lockRDM;
    Integer lockPRM;

    /**
     * Constructor
     */
    public Warehouse() {
        mpList = new HashMap<>();
        gcd = new ConcurrentLinkedQueue<>();
        rdm = new ConcurrentLinkedQueue<>();
        prm = new ConcurrentLinkedQueue<>();
        gcdDefferds = new ConcurrentLinkedQueue<>();
        rdmDefferds = new ConcurrentLinkedQueue<>();
        prmDefferds = new ConcurrentLinkedQueue<>();
        lockGCD = new Integer(0);
        lockRDM = new Integer(0);
        lockPRM = new Integer(0);
    }

    /**
     * Tool acquisition procedure
     * Note that this procedure is non-blocking and should return immediatly
     *
     * @param type - string describing the required tool
     * @return a deferred promise for the  requested tool
     */
    public Deferred<Tool> acquireTool(String type) {
        switch (type) {
            case "gs-driver":
                return acquireToolGCD();
            case "np-hammer":
                return acquireToolPRM();
            case "rs-pliers":
                return acquireToolRDM();
            default:
                throw new NoSuchElementException("no such tool");
        }
    }

    /**
     * Ask for RansomSumPliers Tool from the warehouse.
     * @return - Deferred Object with RansomSumPliers Tool.
     */
    private Deferred<Tool> acquireToolRDM() {
        Tool tool = rdm.poll();
        if(tool == null){
            Deferred<Tool> toolDef= new Deferred<Tool>();
            rdmDefferds.add(toolDef);
            return toolDef;
        }
        else{
            Deferred<Tool> toolDef = new Deferred<>();
            toolDef.resolve(tool);
            return toolDef;
        }
    }

    /**
     * Ask for NextPrimeHammer Tool from the warehouse.
     * @return - Deferred Object with NextPrimeHammer Tool.
     */
    private Deferred<Tool> acquireToolPRM() {
        Tool tool = prm.poll();
        if(tool == null){
            Deferred<Tool> toolDef= new Deferred<Tool>();
            prmDefferds.add(toolDef);
            return toolDef;
        }
        else{
            Deferred<Tool> toolDef = new Deferred<>();
            toolDef.resolve(tool);
            return toolDef;
        }
    }

    /**
     * Ask for GCDScrewdriver Tool from the warehouse.
     * @return - Deferred Object with GCDScrewdriver Tool.
     */
    private Deferred<Tool> acquireToolGCD() {
        Tool tool = gcd.poll();
        if(tool == null){
            Deferred<Tool> toolDef= new Deferred<Tool>();
            gcdDefferds.add(toolDef);
            return toolDef;
        }
        else{
            Deferred<Tool> toolDef = new Deferred<>();
            toolDef.resolve(tool);
            return toolDef;
        }
    }

    /**
     * ToolJson return procedure - releases a tool which becomes available in the warehouse upon completion.
     *
     * @param tool - The tool to be returned
     */
    public void releaseTool(Tool tool) {
        switch (tool.getType()) {
            case "gs-driver":
                releaseTool((GcdScrewdriver)tool);
                break;
            case "np-hammer":
                releaseTool((NextPrimeHammer) tool);
                break;
            case "rs-pliers":
                releaseTool((RandomSumPliers) tool);
                break;
            default:
                throw new NoSuchElementException("no such tool");
        }
    }

    /**
     * Return GCDSCrewdriver to the warehouse or to waiting Deferred.
     * @param tool - for return.
     */
    private void releaseTool(GcdScrewdriver tool){
        synchronized (lockGCD) {
            Deferred<Tool> toolDeferred = gcdDefferds.poll();
            if (toolDeferred == null) {
                gcd.add(tool);
            } else {
                toolDeferred.resolve(tool);
            }
        }
    }

    /**
     * Return NextPrimeHammer to the warehouse or to waiting Deferred.
     * @param tool - for return.
     */
    private void releaseTool(NextPrimeHammer tool){
        synchronized (lockPRM) {
            Deferred<Tool> toolDeferred = prmDefferds.poll();
            if (toolDeferred == null) {
                prm.add(tool);
            } else {
                toolDeferred.resolve(tool);
            }
        }
    }

    /**
     * Return RandomSumPliers to the warehouse or to waiting Deferred.
     * @param tool - for return.
     */
    private void releaseTool(RandomSumPliers tool){
        synchronized (lockRDM) {
            Deferred<Tool> toolDeferred = rdmDefferds.poll();
            if (toolDeferred == null) {
                rdm.add(tool);
            } else {
                toolDeferred.resolve(tool);
            }
        }
    }


    /**
     * Getter for ManufactoringPlans
     *
     * @param product - a string with the product name for which a ManufactoringPlan is desired
     * @return A ManufactoringPlan for product
     */
    public ManufactoringPlan getPlan(String product) {
        return mpList.get(product);
    }

    /**
     * Store a ManufactoringPlan in the warehouse for later retrieval
     *
     * @param plan - a ManufactoringPlan to be stored
     */
    public void addPlan(ManufactoringPlan plan) {
        mpList.put(plan.getProductName(), plan);
    }

    /**
     * Store a qty Amount of tools of type tool in the warehouse for later retrieval
     *
     * @param tool - type of tool to be stored
     * @param qty  - amount of tools of type tool to be stored
     */
    public void addTool(Tool tool, int qty) {
        switch (tool.getType()) {
            case "gs-driver":
                for (int i = 0; i < qty; i++) {
                    gcd.add(new GcdScrewdriver());
                }
                break;
            case "np-hammer":
                for (int i = 0; i < qty; i++) {
                    prm.add(new NextPrimeHammer());
                }
                break;
            case "rs-pliers":
                for (int i = 0; i < qty; i++) {
                    rdm.add(new RandomSumPliers());
                }
                break;
            default:
                throw new NoSuchElementException("no such tool");
        }
    }

}
