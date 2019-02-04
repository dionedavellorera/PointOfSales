package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.RoomConstants;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;

public class RoomsTablesAsync extends AsyncTask<RoomTableModel, Void, List<RoomTableModel>>  {
    private AsyncContract asyncContract;
    public RoomsTablesAsync(AsyncContract asyncContract) {
        this.asyncContract = asyncContract;
    }

    @Override
    protected List<RoomTableModel> doInBackground(RoomTableModel... productsModels) {
        List<RoomTableModel> productsModelList = new ArrayList<>();
        String[]images = {"https://imageog.flaticon.com/icons/png/512/51/51882.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF"};
        productsModelList.add(new RoomTableModel("Room 101", "100", true, images[0], RoomConstants.CLEAN, "#7dff44"));
        productsModelList.add(new RoomTableModel("Room 102", "100", true, images[0], RoomConstants.CLEAN, "#7dff44"));
        productsModelList.add(new RoomTableModel("Room 103", "100", true, images[0], RoomConstants.CLEAN, "#7dff44"));
        productsModelList.add(new RoomTableModel("Room 104", "100", true, images[0], RoomConstants.CLEAN, "#7dff44"));
        productsModelList.add(new RoomTableModel("Room 105", "100", true, images[0], RoomConstants.CLEAN, "#7dff44"));
        productsModelList.add(new RoomTableModel("Room 106", "100", true, images[0], RoomConstants.CLEAN, "#7dff44"));
        productsModelList.add(new RoomTableModel("Room 107", "100", true, images[0], RoomConstants.CLEAN, "#7dff44"));
        productsModelList.add(new RoomTableModel("Room 108", "100", true, images[0], RoomConstants.CLEAN, "#7dff44"));
//        for (int i = 0; i < 20; i++) {
//            productsModelList.add(new RoomTableModel("Table" + i, "100", true, images[0]));
//        }
        return productsModelList;
    }

    @Override
    protected void onPostExecute(List<RoomTableModel> productsModels) {
        this.asyncContract.doneLoading(productsModels, "roomstables");
        super.onPostExecute(productsModels);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
