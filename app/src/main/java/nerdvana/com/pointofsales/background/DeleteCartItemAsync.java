package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;

import java.util.List;

import nerdvana.com.pointofsales.SqlQueries;
import nerdvana.com.pointofsales.entities.CartEntity;
import nerdvana.com.pointofsales.model.ProductsModel;

public class DeleteCartItemAsync extends AsyncTask<Void, Void, Void> {
    private ProductsModel productToDelete;
    public DeleteCartItemAsync(ProductsModel productToDelete) {
        this.productToDelete = productToDelete;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        List<CartEntity> item = CartEntity.findWithQuery(CartEntity.class,
                "SELECT * FROM Cart_Entity where product_id = ?",
                String.valueOf(productToDelete.getProductId()));
        item.get(0).delete();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
