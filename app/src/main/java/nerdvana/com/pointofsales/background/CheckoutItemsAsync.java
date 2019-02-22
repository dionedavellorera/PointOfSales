package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.orm.SugarTransactionHelper;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.TransactionConstants;
import nerdvana.com.pointofsales.entities.CartEntity;
import nerdvana.com.pointofsales.entities.TransactionEntity;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.CheckoutItemsContract;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.UserModel;

//public class CheckoutItemsAsync extends AsyncTask<ProductsModel, Void, ProductsModel> {
//    private CheckoutItemsContract checkoutItemsContract;
//    private ProductsModel selectedProduct;
//    private List<ProductsModel> productList;
//    private Context context;
//    private String _transId = "";
//    private int quantity = 0;
//    private String roomTableNumber;
//    public CheckoutItemsAsync(CheckoutItemsContract checkoutItemsContract, List<ProductsModel> productsList,
//                              ProductsModel selectedProduct, Context context,
//                              String _transId, int quantity,
//                              String roomTableNumber) {
//        this.checkoutItemsContract = checkoutItemsContract;
//        this.selectedProduct = selectedProduct;
//        this.productList = productsList;
//        this.context = context;
//        this._transId = _transId;
//        this.quantity = quantity;
//        this.roomTableNumber = roomTableNumber;
//    }
//
//    @Override
//    protected ProductsModel doInBackground(ProductsModel... productsModels) {
//
//        final UserModel userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.userSettings), UserModel.class);
//
//        if (!TextUtils.isEmpty(_transId)) { //just insert product, transId exist
////            CartEntity cartItem = new CartEntity(
////                    selectedProduct.getName(),
////                    selectedProduct.getShortName(),
////                    selectedProduct.getPrice(),
////                    selectedProduct.getVat(),
////                    selectedProduct.isAvailable(),
////                    selectedProduct.getImageUrls()[0],
////                    selectedProduct.isVattable(),
////                    selectedProduct.isSerialNumberRequired(),
////                    selectedProduct.getLowStackCount(),
////                    selectedProduct.getProductStatus(),
////                    _transId,
////                    quantity,
////                    selectedProduct.getProductId()
////            );
////            addToList(selectedProduct, cartItem.getId());
////            cartItem.save();
//        } else { //create transId and insert new product
//
//            String transactionId = String.format("%s-%s",
//                    userModel.getUsername(),
//                    String.valueOf(System.currentTimeMillis()));
//
//
//            TransactionEntity transaction = new TransactionEntity(
//                    transactionId,
//                    "",
//                    roomTableNumber,
//                    TransactionConstants.PENDING
//                    );
//            transaction.save();
//
//
////            CartEntity cartItem = new CartEntity(
////                    selectedProduct.getName(),
////                    selectedProduct.getShortName(),
////                    selectedProduct.getPrice(),
////                    selectedProduct.getVat(),
////                    selectedProduct.isAvailable(),
////                    selectedProduct.getImageUrls()[0],
////                    selectedProduct.isVattable(),
////                    selectedProduct.isSerialNumberRequired(),
////                    selectedProduct.getLowStackCount(),
////                    selectedProduct.getProductStatus(),
////                    transactionId,
////                    quantity,
////                    selectedProduct.getProductId()
////
////            );
////
////            addToList(selectedProduct, cartItem.getId());
////            cartItem.save();
//
//
//
//        }
//
//
//
//        return selectedProduct;
//    }
//
//    @Override
//    protected void onPostExecute(ProductsModel productsModel) {
//
//        super.onPostExecute(productsModel);
//
//        this.checkoutItemsContract.itemAdded(productsModel);
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    private void addToList(ProductsModel selectedProduct, Long productId) {
//        productList.add(new ProductsModel(
//                selectedProduct.getName(), selectedProduct.getPrice(),
//                selectedProduct.getVat(), selectedProduct.isAvailable(),
//                selectedProduct.getImageUrls(), selectedProduct.isVattable(),
//                selectedProduct.getShortName(), selectedProduct.getProductsList(),
//                selectedProduct.isSelected(), selectedProduct.isSerialNumberRequired(),
//                selectedProduct.getLowStackCount(), selectedProduct.getProductStatus(),
//                productId
//        ));
//    }
//}
