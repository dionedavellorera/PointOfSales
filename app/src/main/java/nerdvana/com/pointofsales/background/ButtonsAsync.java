package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.SharedPreferenceManager;
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
//            case "franchise":
//                if (!SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_SETUP).isEmpty()) {
//                    if (SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_SETUP).equalsIgnoreCase("to")) {
//                        buttonsModelList.add(new ButtonsModel(100,"SAVE TRANSACTION", "",3, 0));
//                        buttonsModelList.add(new ButtonsModel(9988,"RESUME TRANSACTION", "",2, 128));
//                    } else {
//                        buttonsModelList.add(new ButtonsModel(105,"PAYMENT", "",6));
//                        buttonsModelList.add(new ButtonsModel(100,"SAVE TRANSACTION", "",3, 128));
//                        buttonsModelList.add(new ButtonsModel(9988,"RESUME TRANSACTION", "",2, 128));
//                    }
//                } else {
//                    buttonsModelList.add(new ButtonsModel(105,"PAYMENT", "",6));
//                    buttonsModelList.add(new ButtonsModel(100,"SAVE TRANSACTION", "",3, 128));
//                    buttonsModelList.add(new ButtonsModel(9988,"RESUME TRANSACTION", "",2, 128));
//                }
//
//                break;
//            case "table":
//                break;
            case "room":

                buttonsModelList.add(new ButtonsModel(105,"CHECKOUT", "",6, 129));
                buttonsModelList.add(new ButtonsModel(106,"SOA", "",5, 123));
                buttonsModelList.add(new ButtonsModel(114,"TRANSFER ROOM", "",14, 69));
                buttonsModelList.add(new ButtonsModel(122,"CANCEL OVERTIME", "",18, 71));
                buttonsModelList.add(new ButtonsModel(103,"ADD RATE", "",11, 0));
                buttonsModelList.add(new ButtonsModel(9988,"TAKE ORDER", "",2, 0));
                buttonsModelList.add(new ButtonsModel(100,"SAVE", "",3, 0));
                buttonsModelList.add(new ButtonsModel(9999,"ROOMS", "",1, 0));
                buttonsModelList.add(new ButtonsModel(111,"GUEST INFO", "",4, 0));
                buttonsModelList.add(new ButtonsModel(107,"CHECK-IN", "",10, 0));
                buttonsModelList.add(new ButtonsModel(108,"ORDER SLIP", "",15, 0));
                buttonsModelList.add(new ButtonsModel(125,"ROOM LIST VIEW", "",8, 0));
                buttonsModelList.add(new ButtonsModel(130,"FREEBIES", "",3, 0));
                buttonsModelList.add(new ButtonsModel(126,"FOC", "",24, 0)); //return later
                buttonsModelList.add(new ButtonsModel(131,"ADD GUEST", "",24, 0));
                buttonsModelList.add(new ButtonsModel(132,"SPOT AUDIT", "",24, 0));
                buttonsModelList.add(new ButtonsModel(133,"SHIFT CUT OFF", "",24, 0));


//                if (!SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_SETUP).isEmpty()) {
//                    if (SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_SETUP).equalsIgnoreCase("to")) {
//                        buttonsModelList.add(new ButtonsModel(9988,"TAKE ORDER", "",2));
//                        buttonsModelList.add(new ButtonsModel(100,"SAVE", "",3));
//                        buttonsModelList.add(new ButtonsModel(9999,"ROOMS", "",1));
//                        buttonsModelList.add(new ButtonsModel(111,"GUEST INFO", "",4));
//                        buttonsModelList.add(new ButtonsModel(106,"SOA", "",5));
//                        buttonsModelList.add(new ButtonsModel(107,"CHECK-IN", "",10));
//                        buttonsModelList.add(new ButtonsModel(103,"ADD RATE", "",11));
//                        buttonsModelList.add(new ButtonsModel(114,"TRANSFER ROOM", "",14));
//                        buttonsModelList.add(new ButtonsModel(108,"ORDER SLIP", "",15));
//                        buttonsModelList.add(new ButtonsModel(122,"CANCEL OVERTIME", "",18));
//                        buttonsModelList.add(new ButtonsModel(125,"ROOM LIST VIEW", "",8));
//                        buttonsModelList.add(new ButtonsModel(130,"FREEBIES", "",3));
//                        buttonsModelList.add(new ButtonsModel(126,"FOC", "",24)); // return later
//                        buttonsModelList.add(new ButtonsModel(131,"ADD GUEST", "",24));
//                        buttonsModelList.add(new ButtonsModel(132,"SPOT AUDIT", "",24));
//                    } else {
//                        buttonsModelList.add(new ButtonsModel(105,"CHECKOUT", "",6));
//                        buttonsModelList.add(new ButtonsModel(9988,"TAKE ORDER", "",2));
//                        buttonsModelList.add(new ButtonsModel(100,"SAVE", "",3));
//                        buttonsModelList.add(new ButtonsModel(9999,"ROOMS", "",1));
//                        buttonsModelList.add(new ButtonsModel(111,"GUEST INFO", "",4));
//                        buttonsModelList.add(new ButtonsModel(106,"SOA", "",5));
//                        buttonsModelList.add(new ButtonsModel(107,"CHECK-IN", "",10));
//                        buttonsModelList.add(new ButtonsModel(103,"ADD RATE", "",11));
//                        buttonsModelList.add(new ButtonsModel(114,"TRANSFER ROOM", "",14));
//                        buttonsModelList.add(new ButtonsModel(108,"ORDER SLIP", "",15));
//                        buttonsModelList.add(new ButtonsModel(122,"CANCEL OVERTIME", "",18));
//                        buttonsModelList.add(new ButtonsModel(125,"ROOM LIST VIEW", "",8));
//                        buttonsModelList.add(new ButtonsModel(130,"FREEBIES", "",3));
//                        buttonsModelList.add(new ButtonsModel(126,"FOC", "",24)); //return later
//                        buttonsModelList.add(new ButtonsModel(131,"ADD GUEST", "",24));
//                        buttonsModelList.add(new ButtonsModel(132,"SPOT AUDIT", "",24));
//                        buttonsModelList.add(new ButtonsModel(133,"SHIFT CUT OFF", "",24));
//                    }
//                } else {
//
//                }



                break;
        }


        buttonsModelList.add(new ButtonsModel(124,"BACKOUT", "",23, 77));
        buttonsModelList.add(new ButtonsModel(115,"DISCOUNT", "",7, 62));
//            buttonsModelList.add(new ButtonsModel(102,"DEPOSIT", "",9));
        buttonsModelList.add(new ButtonsModel(113,"POST VOID", "",12, 67));
        buttonsModelList.add(new ButtonsModel(101,"ITEM VOID", "",13, 68));
        buttonsModelList.add(new ButtonsModel(118,"SAFEKEEPING", "",17, 0));
        buttonsModelList.add(new ButtonsModel(116,"CANCEL", "",20, 0));
        buttonsModelList.add(new ButtonsModel(128,"BACKUP", "",24, 0));
        buttonsModelList.add(new ButtonsModel(129,"SETTINGS", "",24, 124));
        buttonsModelList.add(new ButtonsModel(997,"LOGOUT", "",100, 0));
        buttonsModelList.add(new ButtonsModel(996,"VIEW RECEIPT.", "",100, 125));




//        if (!SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_SETUP).isEmpty()) {
//            if (SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_SETUP).equalsIgnoreCase("to")) {
//                buttonsModelList.add(new ButtonsModel(116,"CANCEL", "",20));
//                buttonsModelList.add(new ButtonsModel(128,"BACKUP", "",24));
//                buttonsModelList.add(new ButtonsModel(129,"SETTINGS", "",24));
//                buttonsModelList.add(new ButtonsModel(997,"LOGOUT", "",100));
//                buttonsModelList.add(new ButtonsModel(996,"VIEW RECEIPT.", "",100));
//                buttonsModelList.add(new ButtonsModel(124,"BACKOUT", "",23));
//                buttonsModelList.add(new ButtonsModel(115,"DISCOUNT", "",7));
//
//            } else {
//                buttonsModelList.add(new ButtonsModel(124,"BACKOUT", "",23));
//                buttonsModelList.add(new ButtonsModel(115,"DISCOUNT", "",7));
////                buttonsModelList.add(new ButtonsModel(102,"DEPOSIT", "",9));
//                buttonsModelList.add(new ButtonsModel(113,"POST VOID", "",12));
//                buttonsModelList.add(new ButtonsModel(101,"ITEM VOID", "",13));
//                buttonsModelList.add(new ButtonsModel(118,"SAFEKEEPING", "",17));
//                buttonsModelList.add(new ButtonsModel(116,"CANCEL", "",20));
//                buttonsModelList.add(new ButtonsModel(128,"BACKUP", "",24));
//                buttonsModelList.add(new ButtonsModel(129,"SETTINGS", "",24));
//                buttonsModelList.add(new ButtonsModel(997,"LOGOUT", "",100));
//                buttonsModelList.add(new ButtonsModel(996,"VIEW RECEIPT.", "",100));
//            }
//        } else {
//            buttonsModelList.add(new ButtonsModel(124,"BACKOUT", "",23));
//            buttonsModelList.add(new ButtonsModel(115,"DISCOUNT", "",7));
////            buttonsModelList.add(new ButtonsModel(102,"DEPOSIT", "",9));
//            buttonsModelList.add(new ButtonsModel(113,"POST VOID", "",12));
//            buttonsModelList.add(new ButtonsModel(101,"ITEM VOID", "",13));
//            buttonsModelList.add(new ButtonsModel(118,"SAFEKEEPING", "",17));
//            buttonsModelList.add(new ButtonsModel(116,"CANCEL", "",20));
//            buttonsModelList.add(new ButtonsModel(128,"BACKUP", "",24));
//            buttonsModelList.add(new ButtonsModel(129,"SETTINGS", "",24));
//            buttonsModelList.add(new ButtonsModel(997,"LOGOUT", "",100));
//            buttonsModelList.add(new ButtonsModel(996,"VIEW RECEIPT.", "",100));
//        }





        Collections.sort(buttonsModelList);
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

