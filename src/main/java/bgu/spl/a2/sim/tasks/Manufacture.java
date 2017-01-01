package bgu.spl.a2.sim.tasks;

import bgu.spl.a2.Task;
import bgu.spl.a2.sim.Product;
import bgu.spl.a2.sim.Warehouse;

import java.util.ArrayList;

/**
 * Created by shahar on 28/12/2016.
 */
public class Manufacture extends Task<Product> {
    Product product;
    Warehouse warehouse;
    ArrayList<Manufacture>tasks = new ArrayList<>();
    public Manufacture(Product product, Warehouse warehouse) {
        this.product = product;
        this.warehouse = warehouse;
    }
    @Override
    protected void start() {
        String[] parts = warehouse.getPlan(product.getName()).getParts();
        String[] tools = warehouse.getPlan(product.getName()).getTool();
        if(parts.length==0)
            complete(product);
        else{
            for(String p1 : parts){
                Manufacture task = new Manufacture(new Product(product.getStartId()+1,p1),warehouse);
                spawn(task);
                tasks.add(task);
            }
            whenResolved(tasks,() -> {
                tasks.forEach(task -> {
                    product.addPart(task.getResult().get());
                });
                ArrayList<ToolTask> tooltasks = new ArrayList<>();
                if(tools.length==0){
                    product.setFinalID(product.getStartId());
                    complete(product);
                }
                else {
                    for (String tool : tools) {
                        ToolTask tooltask = new ToolTask(tool, product, warehouse);
                        spawn(tooltask);
                        tooltasks.add(tooltask);
                    }
                    whenResolved(tooltasks, () -> {
                        Long finalId = new Long(product.getStartId());
                        for (ToolTask tsk : tooltasks) {
                            finalId += tsk.getResult().get();
                        }
                        product.setFinalID(finalId);
                        complete(product);
                    });
                }
            });
        }


    }
}
