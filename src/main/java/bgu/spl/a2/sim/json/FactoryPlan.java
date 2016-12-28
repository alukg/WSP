
package bgu.spl.a2.sim.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FactoryPlan implements Serializable
{

    @SerializedName("threads")
    @Expose
    private int threads;
    @SerializedName("tools")
    @Expose
    private List<Tool> tools = null;
    @SerializedName("plans")
    @Expose
    private List<Plan> plans = null;
    @SerializedName("waves")
    @Expose
    private List<List<Wave>> waves = null;
    private final static long serialVersionUID = 8308571332376543278L;

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public List<List<Wave>> getWaves() {
        return waves;
    }

    public void setWaves(List<List<Wave>> waves) {
        this.waves = waves;
    }

}
