package bgu.spl.a2.sim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a product produced during the simulation.
 */
public class Product implements Serializable{
	long startId, finalID;
	String name;
	ArrayList<Product> parts;

	/**
	* Constructor
	* @param startId - Product start id
	* @param name - Product name
	*/
    public Product(long startId, String name) {
    	this.startId = startId;
    	this.finalID = startId;
    	this.name = name;
    	parts = new ArrayList<>();
	}

	/**
	* @return The product name as a string
	*/
    public String getName(){
    	return name;
	}

	/**
	* @return The product start ID as a long. start ID should never be changed.
	*/
    public long getStartId(){
    	return startId;
	}

	/**
	* @return The product final ID as a long.
	* final ID is the ID the product received as the sum of all UseOn();
	*/
    public long getFinalId(){
    	return finalID;
	}

	/**
	* @return Returns all parts of this product as a List of Products
	*/
    public List<Product> getParts(){
    	return parts;
	}

	/**
	* Add a new part to the product
	* @param p - part to be added as a Product object
	*/
    public void addPart(Product p){
    	parts.add(p);
	}

	/**
	 * Set finalID.
	 * @param num - Serial number.
	 */
	public void setFinalID(long num){
    	this.finalID=num;
	}
}
