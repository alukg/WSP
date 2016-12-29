package bgu.spl.a2.sim.tools;

import bgu.spl.a2.sim.Product;
import bgu.spl.a2.sim.Warehouse;

/**
 * an interface to be implemented by all tools
 */
public interface Tool {
	/**
	* @return tool name as string
	*/
    public String getType();
	/** ToolJson use method
	* @param p - Product to use tool on
	* @return a long describing the result of tool use on Product package
	*/
    public abstract long useOn(Product p);

}
