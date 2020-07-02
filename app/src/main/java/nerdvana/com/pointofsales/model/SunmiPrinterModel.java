package nerdvana.com.pointofsales.model;

import com.sunmi.devicemanager.device.Device;

public class SunmiPrinterModel {
    public static String PRINTER_EXTERNAL = "external";
    public static String PRINTER_BUILT_IN = "built_in";

    private String type;
    private Device device;
    public SunmiPrinterModel(String type, Device device) {
        this.type = type;
        this.device = device;
    }

    public String getType() {
        return type;
    }

    public Device getDevice() {
        return device;
    }

}
