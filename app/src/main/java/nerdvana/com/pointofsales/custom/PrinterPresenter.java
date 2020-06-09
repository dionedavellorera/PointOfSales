package nerdvana.com.pointofsales.custom;

import android.content.Context;
import android.os.RemoteException;
import android.os.SystemClock;

import com.sunmi.devicemanager.device.Device;
import com.sunmi.devicesdk.core.IStateCallback;
import com.sunmi.devicesdk.core.PrinterManager;
import com.sunmi.peripheral.printer.SunmiPrinterService;

public class PrinterPresenter {
    private Context context;
    private SunmiPrinterService sunmiPrinterService;
    private PrinterManager mManager;
    public PrinterPresenter(Context context, SunmiPrinterService sunmiPrinterService) {
        this.context = context;
        this.sunmiPrinterService = sunmiPrinterService;
        mManager = PrinterManager.getInstance();
    }


    public void printNormal(String textToPrint) {
        if (sunmiPrinterService == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sunmiPrinterService.openDrawer(null);
                    sunmiPrinterService.printerInit(null);
                    if (sunmiPrinterService.updatePrinterState() != 1) {
                        return;
                    }
                    sunmiPrinterService.printText(textToPrint + "\n", null);
                    sunmiPrinterService.lineWrap(3, null);
                    sunmiPrinterService.cutPaper(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void printByDeviceManager(Device device, String textToPrint) {
        if (mManager == null) return;
        mManager.setDefaultDevice(device);
        mManager.enter(true);
        mManager.initPrinter();

        try {
//            mManager.addBold(true);
//            mManager.addTextSizeDouble();
            mManager.addTextAtCenter(textToPrint);
//            mManager.addBold(false);
//            mManager.addFeedDots(10);
//            mManager.addHorizontalCharLine('*');
//            mManager.addText(String.valueOf(SystemClock.uptimeMillis()));
//            mManager.addFeedDots(10);
//            mManager.addText("NERDVANA");
//            mManager.addFeedDots(10);
            mManager.addFeedLine(3);
            mManager.addCutter();
            mManager.commit(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
