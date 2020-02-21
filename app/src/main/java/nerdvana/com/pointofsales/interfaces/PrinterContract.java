package nerdvana.com.pointofsales.interfaces;

import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;

public interface PrinterContract {
    void errorHappen(PrinterStatusInfo printerStatusInfo);
}
