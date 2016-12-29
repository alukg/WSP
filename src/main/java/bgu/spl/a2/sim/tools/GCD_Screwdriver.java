package bgu.spl.a2.sim.tools;

import bgu.spl.a2.sim.Product;
import bgu.spl.a2.sim.Warehouse;

import java.math.BigInteger;

/**
 * Created by guy on 27/12/16.
 */
public class GCD_Screwdriver implements Tool {

    public String getType(){
        return "gs-driver";
    }

    public long useOn(Product p){
        long returnValue = 0;
        for(Product part : p.getParts()){
            long num1 = part.getFinalId();
            long num2 = Long.reverse(num1);
            long x, y;

            while(num2%num1 !=0){
                x=num1;
                y=num2%num1;
                num2 = x;
                num1 = y;
            }
            returnValue += Math.abs(num1);
        }
        return returnValue;
    }

}
