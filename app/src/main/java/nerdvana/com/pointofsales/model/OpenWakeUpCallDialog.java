package nerdvana.com.pointofsales.model;

import java.util.List;

public class OpenWakeUpCallDialog {
    private List<WakeUpCallModel> wakeUpCallModels;

    public OpenWakeUpCallDialog(List<WakeUpCallModel> wakeUpCallModelList) {
        this.wakeUpCallModels = wakeUpCallModelList;
    }

    public List<WakeUpCallModel> getWakeUpCallModels() {
        return wakeUpCallModels;
    }
}
