
package bgu.spl.a2.sim.json;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FactoryPlan implements Serializable
{

    @SerializedName("threads")
    @Expose
    private int threads;
    @SerializedName("toolJsons")
    @Expose
    private List<ToolJson> toolJsons = null;
    @SerializedName("plans")
    @Expose
    private List<Plan> plans = null;
    @SerializedName("waves")
    @Expose
    private List<List<Wafe>> waves = null;
    private final static long serialVersionUID = 962929236297046047L;

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public List<ToolJson> getToolJsons() {
        return toolJsons;
    }

    public void setToolJsons(List<ToolJson> toolJsons) {
        this.toolJsons = toolJsons;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public List<List<Wafe>> getWaves() {
        return waves;
    }

    public void setWaves(List<List<Wafe>> waves) {
        this.waves = waves;
    }

}
