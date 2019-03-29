package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_requests.FetchDenominationRequest;
import nerdvana.com.pointofsales.api_responses.FetchDenominationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionDialog extends BaseDialog {
    private String type;
    private RelativeLayout relContainer;
    private LinearLayout linear;
    public CollectionDialog(@NonNull Context context, String type) {
        super(context);
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_collection, type);
        relContainer = findViewById(R.id.relContainer);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        /*params.weight = 0.5f;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.width = 0;*/


        TextView textView = new TextView(getContext());
        textView.setText("TEST DATA");
        textView.setLayoutParams(params1);
        linearLayout.addView(textView);

        EditText editText = new EditText(getContext());
        editText.setHint("MY HINT");
        editText.setLayoutParams(params2);
        linearLayout.addView(editText);

        relContainer.addView(linearLayout);
        requestDenomination();
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    private void requestDenomination() {
        FetchDenominationRequest fetchDenominationRequest = new FetchDenominationRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchDenominationResponse> request = iUsers.fetchDenomination(fetchDenominationRequest.getMapValue());
        request.enqueue(new Callback<FetchDenominationResponse>() {
            @Override
            public void onResponse(Call<FetchDenominationResponse> call, Response<FetchDenominationResponse> response) {
                //do logic here
            }

            @Override
            public void onFailure(Call<FetchDenominationResponse> call, Throwable t) {

            }
        });
    }
}
