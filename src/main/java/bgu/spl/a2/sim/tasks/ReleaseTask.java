package bgu.spl.a2.sim.tasks;

import bgu.spl.a2.Task;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.tools.Tool;

/**
 * Task for releasing the Tool back to the Warehouse.
 */
public class ReleaseTask extends Task {
    private Tool tool;
    private Warehouse warehouse;

    /**
     * Constructor.
     * @param warehouse - Release to.
     * @param tool - For release.
     */
    public ReleaseTask(Warehouse warehouse, Tool tool) {
        this.tool = tool;
        this.warehouse = warehouse;
    }

    /**
     * Release the tool back when invoke.
     */
    protected void start() {
        warehouse.releaseTool(tool);
    }
}
