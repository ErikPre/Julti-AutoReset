package xyz.duncanruns.julti.resetting;

import xyz.duncanruns.julti.Julti;
import xyz.duncanruns.julti.instance.MinecraftInstance;

import java.util.List;

public class MultiResetManager extends ResetManager {
    public MultiResetManager(Julti julti) {
        super(julti);
    }

    @Override
    public boolean doReset() {
        List<MinecraftInstance> instances = instanceManager.getInstances();

        // Return if no instances
        if (instances.size() == 0) {
            return false;
        }

        // Get selected instance and next instance, return if no selected instance,
        // if there is only a single instance, reset it and return.
        MinecraftInstance selectedInstance = instanceManager.getSelectedInstance();
        if (selectedInstance == null) {
            return false;
        }
        if (instances.size() == 1) {
            selectedInstance.reset(true);
            return true;
        }

        int nextInstInd = (instances.indexOf(selectedInstance) + 1) % instances.size();
        MinecraftInstance nextInstance = instances.get(nextInstInd);

        nextInstance.activate();
        julti.switchScene(nextInstInd + 1);

        selectedInstance.reset();

        super.doReset();
        return true;
    }

    @Override
    public boolean doBGReset() {
        super.doBGReset();
        MinecraftInstance selectedInstance = instanceManager.getSelectedInstance();
        if (selectedInstance == null) {
            return false;
        }
        for (MinecraftInstance instance : instanceManager.getInstances()) {
            if (!instance.equals(selectedInstance)) {
                instance.reset();
            }
        }
        return true;
    }
}