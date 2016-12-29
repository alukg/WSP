package bgu.spl.a2.sim.tasks;

import bgu.spl.a2.Task;
import bgu.spl.a2.sim.Product;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.conf.ManufactoringPlan;

import java.awt.color.ProfileDataException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shahar on 28/12/2016.
 */
public class Manufacture extends Task<Product> {
    Product p;
    Warehouse warehouse;
    ArrayList<Manufacture>tasks = new ArrayList<>();
    public Manufacture(Product p, Warehouse warehouse) {
        this.p =p;
        this.warehouse = warehouse;
    }
    @Override
    protected void start() {
        String[] parts = warehouse.getPlan(p.getName()).getParts();
        String[] tools = warehouse.getPlan(p.getName()).getTool();
        if(parts.length==0)
            complete(p);
        else{
            for(String p1 : parts){
                Manufacture task = new Manufacture(new Product(p.getStartId()+1,p1),warehouse);
                spawn(task);
                tasks.add(task);
            }
            whenResolved(tasks,() -> {

            });
        }


    }
}
