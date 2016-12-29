
package bgu.spl.a2.sim.json;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Series implements Serializable
{

    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("qty")
    @Expose
    private int qty;
    @SerializedName("startId")
    @Expose
    private long startId;
    private final static long serialVersionUID = -5096891835137059162L;

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

    public long getStartId() {
        return startId;
    }

    public void setStartId(long startId) {
        this.startId = startId;
    }

}
