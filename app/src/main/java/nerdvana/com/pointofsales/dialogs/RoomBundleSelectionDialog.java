package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.ProductConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.RoomConstants;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.adapters.BundleSelectionAdapter;
import nerdvana.com.pointofsales.api_requests.AddProductToRequest;
import nerdvana.com.pointofsales.api_requests.AddRoomPriceRequest;
import nerdvana.com.pointofsales.api_responses.FetchProductsResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomRatePriceIdResponse;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.CartItemsModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.SelectedProductsInBundleModel;
import nerdvana.com.pointofsales.model.UpdateProductModel;
import nerdvana.com.pointofsales.model.VoidProductModel;

public abstract class RoomBundleSelectionDialog extends BaseDialog {
    private RecyclerView bundleList;
    private FetchRoomRatePriceIdResponse.Result result;
    private RoomTableModel selectedRoom;
    private String postTransId;
    private String freebieId;
    private FreebiesDialog.Freeby freeby;
    private int qtySelected = 1;
    public RoomBundleSelectionDialog(@NonNull Context context,
                                     FetchRoomRatePriceIdResponse.Result result,
                                     RoomTableModel selectedRoom,
                                     String postTransId,
                                     String freebieId,
                                     FreebiesDialog.Freeby freeby,
                                     int qtySelected) {
        super(context);
        this.result = result;
        this.selectedRoom = selectedRoom;
        this.postTransId = postTransId;
        this.freebieId = freebieId;
        this.qtySelected = qtySelected;
        this.freeby = freeby;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_room_bundle_selection, "SELECT FREEBY");
        bundleList = findViewById(R.id.bundleList);

        BundleProductSelection bundleProductSelection = new BundleProductSelection() {
            @Override
            public void selected(final int position) {
                final FetchRoomRatePriceIdResponse.ProductBundle b =  result.getProductBundleList().get(position);
                if (result.getProductBundleList().get(position).getProduct().getBranchGroup().size() < 1) {
                    final ArrayList<AddRateProductModel> model = new ArrayList<>();

                    DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                    DateTime companyUpdatedAt = new DateTime(df.parseDateTime(result.getProductBundleList().get(position).getProduct().getBranchAlaCart().get(0).getBranchProduct().getUpdatedAt()));
                    Double amount = result.getProductBundleList().get(position).getProduct().getAmount();


                    if (result.getProductBundleList().get(position).getProduct().getBranchAlaCart().size() > 0) {
                        amount = result.getProductBundleList().get(position).getProduct().getBranchAlaCart().get(0).getBranchProduct().getAmount();

                        if (result.getProductBundleList().get(position).getProduct().getBranchAlaCart().get(0).getBranchProduct().getBranchPrice() != null) {
                            DateTime branchUpdatedAt =
                                    new DateTime(
                                            df.parseDateTime(
                                                    result.getProductBundleList().get(position).getProduct().getBranchAlaCart().get(0).getUpdatedAt()));
                            if (branchUpdatedAt.isAfter(companyUpdatedAt)) {
                                amount = result.getProductBundleList().get(position).getProduct().getBranchAlaCart().get(0).getBranchProduct().getBranchPrice().getAmount();
                                amount = ((amount * (result.getProductBundleList().get(position).getProduct().getBranchAlaCart().get(0).getBranchProduct().getBranchPrice().getMarkUp() + 1))) * Double.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE));
                            }
                        }

                    }
                    model.add(new AddRateProductModel(
                            String.valueOf(b.getProduct().getCoreId()),
                            "0",
                            String.valueOf(b.getQty()),
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                            String.valueOf(amount),
                            0,
                            b.getProduct().getProduct(),
                            new ArrayList<AddRateProductModel.AlaCarte>(),
                            new ArrayList<AddRateProductModel.Group>()
                    ));
                    ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getContext()) {
                        @Override
                        public void save(String remarks) {
                            if (selectedRoom.isTakeOut()) {
//                                BusProvider.getInstance().post(new PrintModel("", "TAKEOUT", "FO", GsonHelper.getGson().toJson(model), kitchenPath, printerPath));
                                BusProvider.getInstance().post(new AddProductToRequest(
                                        model,
                                        String.valueOf(selectedRoom.getRoomId()),
                                        String.valueOf(selectedRoom.getAreaId()),
                                        selectedRoom.getControlNo(),
                                        new ArrayList<VoidProductModel>(),
                                        remarks,
                                        postTransId,
                                        freebieId,
                                        new ArrayList<UpdateProductModel>()));
                            } else {
//                                BusProvider.getInstance().post(new PrintModel("", selectedRoom.getName(), "FO", GsonHelper.getGson().toJson(model), kitchenPath, printerPath));
                                BusProvider.getInstance().post(new AddRoomPriceRequest(
                                        model,
                                        String.valueOf(selectedRoom.getRoomId()),
                                        new ArrayList<VoidProductModel>(),
                                        remarks,
                                        "",
                                        postTransId,
                                        freebieId,
                                        new ArrayList<UpdateProductModel>()));
                            }

//                            freeby.clicked(position);
                            completed();
                        }
                    };
                    confirmWithRemarksDialog.show();



                } else {
                    String[]images = {};
                    images = new String[]{"http://192.168.1.90/pos/uploads/company/product/etst.png"};

                    final ProductsModel productsModel = new ProductsModel(
                            b.getProduct().getProduct(),
                            b.getProduct().getAmount(),
                            0.00,
                            true,
                            images,
                            true,
                            b.getProduct().getProductInitial(),
                            new ArrayList<ProductsModel>(),
                            false,
                            false,
                            0,
                            ProductConstants.PENDING,
                            b.getProduct().getCoreId(),
                            b.getProduct().getMarkUp(),
                            0,
                            "",
                            b.getProduct().getAmount(),
                            result.getProductBundleList().get(position).getProduct().getBranchAlaCart(),
                            result.getProductBundleList().get(position).getProduct().getBranchGroup());



                    DialogBundleComposition dialogBundleComposition = new DialogBundleComposition(
                            getContext(),
                            productsModel.getBranchGroupList(),
                            productsModel.getPrice(),
                            qtySelected) {
                        @Override
                        public void bundleCompleted(List<SelectedProductsInBundleModel> selectedProductsInBundleModelList) {

                            ArrayList<AddRateProductModel.AlaCarte> alaCartes = new ArrayList<>();
                            ArrayList<AddRateProductModel.Group> groupLst = new ArrayList<>();
                            ArrayList<AddRateProductModel.GroupCompo> groupCompoList = new ArrayList<>();
                            ArrayList<AddRateProductModel> groupCompoProductsList = new ArrayList<>();
                            for (SelectedProductsInBundleModel sipm : selectedProductsInBundleModelList) {
                                groupCompoList = new ArrayList<>();
                                groupCompoProductsList = new ArrayList<>();
                                for (SelectedProductsInBundleModel.BundleProductModel bpm : sipm.getBundleProductModelList()) {
                                    groupCompoProductsList.add(
                                            new AddRateProductModel(
                                                    String.valueOf(bpm.getProductId()),
                                                    "0",
                                                    String.valueOf(bpm.getQty()),
                                                    SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                                    String.valueOf(bpm.getAmount()),
                                                    0,
                                                    bpm.getName(),
                                                    new ArrayList<AddRateProductModel.AlaCarte>(),
                                                    new ArrayList<AddRateProductModel.Group>()
                                            ));

                                }
                                groupLst.add(new AddRateProductModel.Group(new AddRateProductModel.GroupCompo(sipm.getGroupId(), sipm.getGroupName(), sipm.getTotalQtySelected(),groupCompoProductsList)));
                            }

                            if (productsModel.getBranchAlaCartList().size() > 0) {
                                for (FetchProductsResponse.BranchAlaCart balac : productsModel.getBranchAlaCartList()) {
                                    alaCartes.add(new AddRateProductModel.AlaCarte(
                                            String.valueOf(balac.getBranchProduct().getCoreId()),
                                            "0",
                                            String.valueOf(balac.getQty()),
                                            SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                            String.valueOf(balac.getPrice()),
                                            0,
                                            balac.getBranchProduct().getProductInitial()

                                    ));
                                }
                            }

                            if (selectedProductsInBundleModelList.size() > 0) {
                                final ArrayList<AddRateProductModel> model = new ArrayList<>();
                                model.add(new AddRateProductModel(
                                        String.valueOf(b.getProduct().getCoreId()),
                                        "0",
                                        String.valueOf(b.getQty()),
                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                                        String.valueOf(b.getProduct().getAmount()),
                                        0,
                                        b.getProduct().getProduct(),
                                        alaCartes,
                                        groupLst
                                ));
                                ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getContext()) {
                                    @Override
                                    public void save(String remarks) {
                                        if (selectedRoom.isTakeOut()) {
//                                BusProvider.getInstance().post(new PrintModel("", "TAKEOUT", "FO", GsonHelper.getGson().toJson(model), kitchenPath, printerPath));
                                            BusProvider.getInstance().post(new AddProductToRequest(
                                                    model,
                                                    String.valueOf(selectedRoom.getRoomId()),
                                                    String.valueOf(selectedRoom.getAreaId()),
                                                    selectedRoom.getControlNo(),
                                                    new ArrayList<VoidProductModel>(),
                                                    remarks,
                                                    postTransId,
                                                    freebieId,
                                                    new ArrayList<UpdateProductModel>()));
                                        } else {

//                                BusProvider.getInstance().post(new PrintModel("", selectedRoom.getName(), "FO", GsonHelper.getGson().toJson(model), kitchenPath, printerPath));

                                            BusProvider.getInstance().post(new AddRoomPriceRequest(
                                                    model,
                                                    String.valueOf(selectedRoom.getRoomId()),
                                                    new ArrayList<VoidProductModel>(),
                                                    remarks,
                                                    "",
                                                    postTransId,
                                                    freebieId,
                                                    new ArrayList<UpdateProductModel>()));


                                        }

                                        completed();
                                    }
                                };
                                confirmWithRemarksDialog.show();

                            }
                            completed();
                        }
                    };
                    dialogBundleComposition.show();
                }
            }
        };

        BundleSelectionAdapter bundleSelectionAdapter = new BundleSelectionAdapter(result.getProductBundleList(), bundleProductSelection);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        bundleList.setLayoutManager(llm);
        bundleList.setAdapter(bundleSelectionAdapter);
    }

    public abstract void completed();

    public interface BundleProductSelection {
        void selected(int position);
    }
}
