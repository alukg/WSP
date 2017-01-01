package bgu.spl.a2.sim.tools;

import bgu.spl.a2.sim.Product;
import bgu.spl.a2.sim.Warehouse;

import java.util.Random;

public class RandomSumPliers implements Tool {

    /**
     * Get the tool type.
     * @return tool type.
     */
    @Override
    public String getType() {
        return "rs-pliers";
    }

    /**
     * Summarize of tool use on product parts.
     * @param p - Product to use tool on
     * @return - Sum of product parts after tool use on.
     */
    @Override
    public long useOn(Product p) {
        long returnValue = 0;
        for(Product part : p.getParts()){
            long sum = 0;
            long productId = part.getFinalId();
            long stop = productId % 10000;

            Random rand = new Random(productId);
            for (int i=1;i<=stop;i++) {
                sum += rand.nextInt();
            }
            returnValue += Math.abs(sum);
        }
        return returnValue;
    }

}

