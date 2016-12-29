package bgu.spl.a2.sim.tasks;

import bgu.spl.a2.Task;
import bgu.spl.a2.sim.Product;

import java.util.ArrayList;

/**
 * Created by shahar on 28/12/2016.
 */
public class Manufacture extends Task<Product> {
    Product p;
    ArrayList<Manufacture>tasks = new ArrayList<>();
    public Manufacture(Product p){
        this.p =p;
    }
    @Override
    protected void start() {
        if(p.getParts().size()==0)
            complete(p);
        else{
            for(Product p1 : p.getParts()){
                Manufacture task = new Manufacture(p1);
                spawn(task);
                tasks.add(task);
            }
            whenResolved(tasks,() -> {

            });
        }


    }
}
