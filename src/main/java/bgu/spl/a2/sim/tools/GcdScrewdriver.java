package bgu.spl.a2.sim.tools;

import bgu.spl.a2.sim.Product;

/**
 * Class for Screwdriver tool.
 */
public class GcdScrewdriver implements Tool {

    /**
     * Get the tool type.
     * @return tool type.
     */
    @Override
    public String getType(){
        return "gs-driver";
    }

    /**
     * Summarize of tool use on product parts.
     * @param p - Product to use tool on
     * @return - Sum of product parts after tool use on.
     */
    @Override
    public long useOn(Product p){
        long returnValue = 0;
        for(Product part : p.getParts()){
            long num1 = part.getFinalId();
            long num2 = reverse(num1);
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

    /**
     * Return a number from the end to start.
     * @param n - number to reverse.
     * @return - the reversed number.
     */
    private long reverse(long n){
        long reverse=0;
        while( n != 0 ){
            reverse = reverse * 10;
            reverse = reverse + n%10;
            n = n/10;
        }
        return reverse;
    }

}
