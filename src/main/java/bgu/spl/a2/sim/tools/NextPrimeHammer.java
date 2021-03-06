package bgu.spl.a2.sim.tools;

import bgu.spl.a2.sim.Product;
import bgu.spl.a2.sim.Warehouse;

public class NextPrimeHammer implements Tool {

    /**
     * Get the tool type.
     * @return tool type.
     */
    @Override
    public String getType() {
        return "np-hammer";
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
            long checkedNum = part.getFinalId() + 1;
            while (true) {
                if (isPrime(checkedNum)) {
                    break;
                }
                checkedNum++;
            }
            returnValue += Math.abs(checkedNum);
        }
        return returnValue;
    }

    private boolean isPrime(long num) {
        for (int j = 2; j <= Math.sqrt(num); j++) {
            if (num % j == 0) {
                return false;
            }
        }
        return true;
    }

}
