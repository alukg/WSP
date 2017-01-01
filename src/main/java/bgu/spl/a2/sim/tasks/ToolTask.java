package bgu.spl.a2.sim.tasks;

import bgu.spl.a2.Deferred;
import bgu.spl.a2.sim.Product;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.tools.GCD_Screwdriver;
import bgu.spl.a2.sim.tools.NextPrimeHammer;
import bgu.spl.a2.sim.tools.RandomSumPliers;
import bgu.spl.a2.sim.tools.Tool;
import bgu.spl.a2.Task;

/**
 * Task to useOn all the tools on parts.
 */
public class ToolTask extends Task<Long> {
    Warehouse warehouse;
    String toolName;
    Product product;

    /**
     * Constructor.
     * @param toolName - Which tool to use with.
     * @param product - The product to use on his parts.
     * @param warehouse - For getting the tool.
     */
    public ToolTask(String toolName, Product product, Warehouse warehouse) {
        this.warehouse = warehouse;
        this.product = product;
        this.toolName = toolName;
    }

    @Override
    protected void start() {
        Deferred<Tool> t = warehouse.acquireTool(toolName);
        t.whenResolved(() -> {
            complete(t.get().useOn(product));
            ReleaseTask releaseTask = new ReleaseTask(warehouse,t.get());
            spawn(releaseTask);
        });
    }
}
