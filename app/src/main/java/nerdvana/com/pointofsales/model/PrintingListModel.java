package nerdvana.com.pointofsales.model;

import java.util.List;

public class PrintingListModel {
    private String type;
    private String nameToShow;
    private boolean isEditing;

    private List<SelectedPrinterData> selectedPrinterList;

    public PrintingListModel(String type, String nameToShow,
                             boolean isEditing, List<SelectedPrinterData> selectedPrinterList) {
        this.type = type;
        this.isEditing = isEditing;
        this.nameToShow = nameToShow;
        this.selectedPrinterList = selectedPrinterList;
    }

    public List<SelectedPrinterData> getSelectedPrinterList() {
        return selectedPrinterList;
    }

    public void setSelectedPrinterList(List<SelectedPrinterData> selectedPrinterList) {
        this.selectedPrinterList = selectedPrinterList;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public String getNameToShow() {
        return nameToShow;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public String getType() {
        return type;
    }

    public static class SelectedPrinterData {
        private String id;
        private String printerName;

        public SelectedPrinterData(String id, String printerName) {
            this.id = id;
            this.printerName = printerName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrinterName() {
            return printerName;
        }

        public void setPrinterName(String printerName) {
            this.printerName = printerName;
        }
    }
}
