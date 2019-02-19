package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;

import java.util.List;

import nerdvana.com.pointofsales.ProductConstants;
import nerdvana.com.pointofsales.SqlQueries;
import nerdvana.com.pointofsales.TransactionConstants;
import nerdvana.com.pointofsales.entities.CartEntity;
import nerdvana.com.pointofsales.entities.TransactionEntity;
import nerdvana.com.pointofsales.interfaces.SaveTransactionContract;

public class SaveTransactionAsync extends AsyncTask<Void, Void, Void> {
    private List<TransactionEntity> myCart;
    private SaveTransactionContract saveTransactionContract;
    public SaveTransactionAsync(List<TransactionEntity> myCart, SaveTransactionContract saveTransactionContract) {
        this.myCart = myCart;
        this.saveTransactionContract = saveTransactionContract;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (myCart.size() > 0) {
//            for (CartEntity cartRecord : getCartRecord(myCart.get(0).getTransactionId())) {
//                cartRecord.setProductStatus(ProductConstants.SAVED);
//                cartRecord.save();
//            }
//            myCart.get(0).setTransactionStatus(TransactionConstants.SAVED);
//            myCart.get(0).save();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        saveTransactionContract.finishedSaving();

    }

    private List<CartEntity> getCartRecord(String transactionId) {
        return CartEntity.
                findWithQuery(
                        CartEntity.class,
                        SqlQueries.GET_CART_ITEMS,
                        transactionId
                );
    }

}
