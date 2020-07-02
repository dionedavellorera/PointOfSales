package nerdvana.com.pointofsales;

import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.PrintingListModel;
import nerdvana.com.pointofsales.model.SunmiPrinterModel;

public interface PrintoutSelectionIntf {
    void clicked(int position, PrintingListModel printingListModel);
    void closeClicked(int position, int innerPosition);
}
