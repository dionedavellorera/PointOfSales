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
        buttonsModelList.add(new ButtonsModel(9999,"ROOMS", "",1));
        buttonsModelList.add(new ButtonsModel(9988,"TAKE ORDER", "",2));
        buttonsModelList.add(new ButtonsModel(100,"SAVE", "",3));
        buttonsModelList.add(new ButtonsModel(101,"VOID", "",4));
        buttonsModelList.add(new ButtonsModel(102,"DEPOSIT", "",5));
        buttonsModelList.add(new ButtonsModel(103,"ADD RATE", "",6));
        buttonsModelList.add(new ButtonsModel(105,"CHECKOUT", "",7));
        buttonsModelList.add(new ButtonsModel(106,"SOA", "",8));
        buttonsModelList.add(new ButtonsModel(107,"CHECK-IN", "",9));
        buttonsModelList.add(new ButtonsModel(108,"ORDER SLIP", "",10));
        buttonsModelList.add(new ButtonsModel(109,"FOC", "",11));
        buttonsModelList.add(new ButtonsModel(110,"SET PRINTER", "",12));
        buttonsModelList.add(new ButtonsModel(111,"GUEST INFO", "",13));
        buttonsModelList.add(new ButtonsModel(112,"VIEW RECEIPT", "",14));
        buttonsModelList.add(new ButtonsModel(113,"POST VOID", "",15));
        buttonsModelList.add(new ButtonsModel(114,"SWITCH ROOM", "",16));
        buttonsModelList.add(new ButtonsModel(115,"DISCOUNT", "",17));
        buttonsModelList.add(new ButtonsModel(116,"CANCEL", "",20));
        buttonsModelList.add(new ButtonsModel(117,"CHANGE SHIFT", "",18));
        buttonsModelList.add(new ButtonsModel(118,"SAFEKEEPING", "",19));
//        buttonsModelList.add(new ButtonsModel(119,"X-READ", "",21));
        buttonsModelList.add(new ButtonsModel(120,"Z-READ", "",22));
        buttonsModelList.add(new ButtonsModel(121,"X-READ", "",23));
        buttonsModelList.add(new ButtonsModel(122,"CANCEL OVERTIME", "",24));
        buttonsModelList.add(new ButtonsModel(123,"REPRINT X/Z READING", "",24));

        buttonsModelList.add(new ButtonsModel(997,"LOGOUT", "",100));

//        buttonsModelList.add(new ButtonsModel(110,"VALIDATE SLIP", "",10));
//        buttonsModelList.add(new ButtonsModel(110,"IN-TRANSIT", "",10));
//        buttonsModelList.add(new ButtonsModel(110,"CALCULATOR", "",10));
//        buttonsModelList.add(new ButtonsModel(110,"POST VOID", "",10));
//        buttonsModelList.add(new ButtonsModel(110,"SPLIT BILL", "",10));
//        buttonsModelList.add(new ButtonsModel(110,"TENDER DECLARATION", "",10));
//        buttonsModelList.add(new ButtonsModel(110,"PROMO", "",10));
//        buttonsModelList.add(new ButtonsModel(110,"CHARGE REDEMPTION", "",10));
//        buttonsModelList.add(new ButtonsModel(110,"DISCOUNT EXCEMPT", "",10));
//        buttonsModelList.add(new ButtonsModel(110,"SAFEKEEPING", "",10));
//        buttonsModelList.add(new ButtonsModel(110,"ADJUSTMENT", "",10));
//        buttonsModelList.add(new ButtonsModel(110,"REPRINT OR", "",10));
//        buttonsModelList.add(new ButtonsModel(110,"VIEW RECEIPT", "",10));
//        buttonsModelList.add(new ButtonsModel(110,"DEPOSIT RECALL", "",10));

        Collections.sort(buttonsModelList);

//        Collections.sort(buttonsModelList, new Comparator<ButtonsModel>() {
//            @Override
//            public int compare(ButtonsModel o1, ButtonsModel o2) {
//                return String.valueOf(o1.getPosition()).compareTo(String.valueOf(o2.getPosition()));
//            }
//        });
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

