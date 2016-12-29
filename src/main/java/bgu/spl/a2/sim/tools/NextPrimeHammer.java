package bgu.spl.a2.sim.tools;

import bgu.spl.a2.sim.Product;
import bgu.spl.a2.sim.Warehouse;

public class NextPrimeHammer implements Tool {
    @Override
    public String getType() {
        return "np-hammer";
    }

    @Override
    public long useOn(Product p) {
        long checkedNum = p.getStartId() + 1;
        while (true) {
            if (isPrime(checkedNum)) {
                return checkedNum;
            }
            checkedNum++;
        }
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
