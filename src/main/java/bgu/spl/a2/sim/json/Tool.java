
package bgu.spl.a2.sim.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tool implements Serializable
{

    @SerializedName("tool")
    @Expose
    private String tool;
    @SerializedName("qty")
    @Expose
    private int qty;
    private final static long serialVersionUID = -195106828673651593L;

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
