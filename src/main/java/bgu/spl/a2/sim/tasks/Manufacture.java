package bgu.spl.a2.sim.tasks;

import bgu.spl.a2.Task;
import bgu.spl.a2.sim.Product;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.conf.ManufactoringPlan;

import java.util.ArrayList;

public class Manufacture extends Task<Product> {
    Product p;

    public Manufacture(Product p) {
        this.p = p;
    }

    @Override
    protected void start() {
        ArrayList<Manufacture> tasks = new ArrayList<>();

        if (p.getParts().size() == 0)
            complete(p);
        else {
            for (Product p1 : p.getParts()) {
                Manufacture task = new Manufacture(p1);
                spawn(task);
                tasks.add(task);
            }
            whenResolved(tasks, () -> {

            });
        }
    }
}
