package bgu.spl.a2.sim.tasks;

import bgu.spl.a2.Task;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.tools.Tool;

public class ReleaseTask extends Task {
    private Tool tool;
    private Warehouse warehouse;

    public ReleaseTask(Warehouse warehouse, Tool tool) {
        this.tool = tool;
        this.warehouse = warehouse;
    }

    protected void start() {
        warehouse.releaseTool(tool);
    }
}
