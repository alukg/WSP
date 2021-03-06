
package bgu.spl.a2.sim.json;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plan implements Serializable
{

    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("tools")
    @Expose
    private String[] tools = null;
    @SerializedName("parts")
    @Expose
    private String[] parts = null;
    private final static long serialVersionUID = 2145806663185057797L;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String[] getTools() {
        return tools;
    }

    public void setTools(String[] tools) {
        this.tools = tools;
    }

    public String[] getParts() {
        return parts;
    }

    public void setParts(String[] parts) {
        this.parts = parts;
    }

}
