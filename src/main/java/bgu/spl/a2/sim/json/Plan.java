
package bgu.spl.a2.sim.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Plan implements Serializable
{

    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("tools")
    @Expose
    private List<String> tools = null;
    @SerializedName("parts")
    @Expose
    private List<String> parts = null;
    private final static long serialVersionUID = -3375666727608761373L;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public List<String> getTools() {
        return tools;
    }

    public void setTools(List<String> tools) {
        this.tools = tools;
    }

    public List<String> getParts() {
        return parts;
    }

    public void setParts(List<String> parts) {
        this.parts = parts;
    }

}
