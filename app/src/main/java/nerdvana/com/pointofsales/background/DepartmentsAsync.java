package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.DepartmentsModel;

public class DepartmentsAsync extends AsyncTask<DepartmentsModel, Void, List<DepartmentsModel>> {
    private AsyncContract asyncContract;
    public DepartmentsAsync(AsyncContract asyncContract) {
        this.asyncContract = asyncContract;
    }

    @Override
    protected List<DepartmentsModel> doInBackground(DepartmentsModel... buttonsModels) {
        List<DepartmentsModel> buttonsModelList = new ArrayList<>();
        String[]images = {"", ""};
        for (int i = 0; i < 10; i++) {
            buttonsModelList.add(new DepartmentsModel("Dept." + i, ""));
        }
        return buttonsModelList;
    }

    @Override
    protected void onPostExecute(List<DepartmentsModel> buttonsModels) {
        this.asyncContract.doneLoading(buttonsModels, "departments");
        super.onPostExecute(buttonsModels);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
