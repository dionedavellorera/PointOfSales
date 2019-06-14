package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.ProductsModel;

public class ButtonsAsync extends AsyncTask<ButtonsModel, Void, List<ButtonsModel>> {
    private AsyncContract asyncContract;
    private Context context;
    public ButtonsAsync(AsyncContract asyncContract, Context context) {
        this.asyncContract = asyncContract;
        this.context = context;
    }

    @Override
    protected List<ButtonsModel> doInBackground(ButtonsModel... buttonsModels) {
        List<ButtonsModel> buttonsModelList = new ArrayList<>();
        String[]images = {"", ""};
        switch (Utils.getSystemType(context)) {
            case "not_supported":
                break;
            case "franchise":
                buttonsModelList.add(new ButtonsModel(105,"PAYMENT", "",6));
                buttonsModelList.add(new ButtonsModel(100,"SAVE TRANSACTION", "",3));
                buttonsModelList.add(new ButtonsModel(9988,"RESUME TRANSACTION", "",2));
                break;
            case "table":
                break;
            case "room":
                buttonsModelList.add(new ButtonsModel(105,"CHECKOUT", "",6));
                buttonsModelList.add(new ButtonsModel(9988,"TAKE ORDER", "",2));
                buttonsModelList.add(new ButtonsModel(100,"SAVE", "",3));
                buttonsModelList.add(new ButtonsModel(9999,"ROOMS", "",1));
                buttonsModelList.add(new ButtonsModel(111,"GUEST INFO", "",4));
                buttonsModelList.add(new ButtonsModel(106,"SOA", "",5));
                buttonsModelList.add(new ButtonsModel(107,"CHECK-IN", "",10));
                buttonsModelList.add(new ButtonsModel(103,"ADD RATE", "",11));
                buttonsModelList.add(new ButtonsModel(114,"TRANSFER ROOM", "",14));
                buttonsModelList.add(new ButtonsModel(108,"ORDER SLIP", "",15));
                buttonsModelList.add(new ButtonsModel(122,"CANCEL OVERTIME", "",18));
                buttonsModelList.add(new ButtonsModel(125,"ROOM LIST VIEW", "",8));
                buttonsModelList.add(new ButtonsModel(130,"FREEBIES", "",24));
                buttonsModelList.add(new ButtonsModel(126,"FOC", "",24));
                break;
        }




        buttonsModelList.add(new ButtonsModel(124,"BACKOUT", "",23));
//        buttonsModelList.add(new ButtonsModel(105,"CHECKOUT", "",6));
        buttonsModelList.add(new ButtonsModel(115,"DISCOUNT", "",7));
        buttonsModelList.add(new ButtonsModel(102,"DEPOSIT", "",9));
        buttonsModelList.add(new ButtonsModel(113,"POST VOID", "",12));
        buttonsModelList.add(new ButtonsModel(101,"ITEM VOID", "",13));

        buttonsModelList.add(new ButtonsModel(112,"VIEW RECEIPT", "",16));
        buttonsModelList.add(new ButtonsModel(118,"SAFEKEEPING", "",17));

        buttonsModelList.add(new ButtonsModel(121,"XREAD", "",19));
        buttonsModelList.add(new ButtonsModel(116,"CANCEL", "",20));
        buttonsModelList.add(new ButtonsModel(120,"ZREAD", "",21));
        buttonsModelList.add(new ButtonsModel(123,"REPRINT X READING", "",22));
        buttonsModelList.add(new ButtonsModel(127,"REPRINT Z READING", "",22));


        buttonsModelList.add(new ButtonsModel(128,"BACKUP", "",24));
        buttonsModelList.add(new ButtonsModel(129,"SETTINGS", "",24));


        buttonsModelList.add(new ButtonsModel(997,"LOGOUT", "",100));

//        buttonsModelList.add(new ButtonsModel(110,"SET PRINTER", "",99));

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

