package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.ProductsModel;

public class ButtonsAsync extends AsyncTask<ButtonsModel, Void, List<ButtonsModel>> {
    private AsyncContract asyncContract;
    public ButtonsAsync(AsyncContract asyncContract) {
        this.asyncContract = asyncContract;
    }

    @Override
    protected List<ButtonsModel> doInBackground(ButtonsModel... buttonsModels) {
        List<ButtonsModel> buttonsModelList = new ArrayList<>();
        String[]images = {"", ""};
        buttonsModelList.add(new ButtonsModel(100,"SAVE", "",1));
        buttonsModelList.add(new ButtonsModel(101,"VOID", "",2));
        buttonsModelList.add(new ButtonsModel(102,"PAY", "",3));
        buttonsModelList.add(new ButtonsModel(103,"ADD RATE", "",4));

        Collections.sort(buttonsModelList, new Comparator<ButtonsModel>() {
            @Override
            public int compare(ButtonsModel o1, ButtonsModel o2) {
                return String.valueOf(o1.getPosition()).compareTo(String.valueOf(o2.getPosition()));
            }
        });
        return buttonsModelList;
    }

    @Override
    protected void onPostExecute(List<ButtonsModel> buttonsModels) {
        this.asyncContract.doneLoading(buttonsModels, "buttons");
        super.onPostExecute(buttonsModels);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}

