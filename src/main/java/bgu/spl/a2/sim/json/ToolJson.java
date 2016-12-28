
package bgu.spl.a2.sim.json;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tool implements Serializable
{

    @SerializedName("tool")
    @Expose
    private String tool;
    @SerializedName("qty")
    @Expose
    private int qty;
    private final static long serialVersionUID = -2451039268220854650L;

    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

}
