
package bgu.spl.a2.sim.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wave implements Serializable
{

    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("qty")
    @Expose
    private int qty;
    @SerializedName("startId")
    @Expose
    private int startId;
    private final static long serialVersionUID = 6173866322790579419L;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getStartId() {
        return startId;
    }

    public void setStartId(int startId) {
        this.startId = startId;
    }

}
