
package json;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wafe implements Serializable
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

    public int getStartId() {
        return startId;
    }

    public void setStartId(int startId) {
        this.startId = startId;
    }

}
