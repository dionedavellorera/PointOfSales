package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.model.ButtonsModel;

public class CategoryAsync {

//        extends AsyncTask<ButtonsModel, Void, List<ButtonsModel>> {
//    private AsyncContract asyncContract;
//    public CategoryAsync(AsyncContract asyncContract) {
//        this.asyncContract = asyncContract;
//    }
//
//    @Override
//    protected List<ButtonsModel> doInBackground(ButtonsModel... buttonsModels) {
//        List<ButtonsModel> buttonsModelList = new ArrayList<>();
//        String[]images = {"", ""};
//        for (int i = 0; i < 20; i++) {
//            buttonsModelList.add(new ButtonsModel("Product" + i, "", i));
//        }
//        return buttonsModelList;
//    }
//
//    @Override
//    protected void onPostExecute(List<ButtonsModel> buttonsModel) {
//        this.asyncContract.doneLoading(buttonsModel, "category");
//        super.onPostExecute(buttonsModel);
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
}
