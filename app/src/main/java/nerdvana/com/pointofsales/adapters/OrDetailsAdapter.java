package nerdvana.com.pointofsales.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.api_responses.ViewReceiptResponse;

public class OrDetailsAdapter {
//        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    List<ViewReceiptResponse.Post_> selectedOrPost;
//
//    public class TextHeader extends RecyclerView.ViewHolder {
//        public TextView header;
//
//        public TextHeader(View view) {
//            super(view);
//            header = (TextView) view.findViewById(R.id.header);
//        }
//    }
//
//    public OrDetailsAdapter(List<ViewReceiptResponse.Post_> selectedOrPost) {
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = null;
////        switch (viewType) {
////            case TYPE_HEADER:
////                itemView = LayoutInflater.from(parent.getContext())
////                        .inflate(R.layout.view_text_header, parent, false);
////                return new BookingOptionsAdapter.TextHeader(itemView);
////            case TYPE_TEXT_IMAGE:
////                itemView = LayoutInflater.from(parent.getContext())
////                        .inflate(R.layout.view_text_image, parent, false);
////                return new BookingOptionsAdapter.TextImage(itemView);
////            case TYPE_TEXT_TEXT:
////                itemView = LayoutInflater.from(parent.getContext())
////                        .inflate(R.layout.view_text_text, parent, false);
////                return new BookingOptionsAdapter.TextText(itemView);
////            case TYPE_TEXT:
////                itemView = LayoutInflater.from(parent.getContext())
////                        .inflate(R.layout.view_text, parent, false);
////                return new BookingOptionsAdapter.Text(itemView);
////            case TYPE_SMALL_HEADER:
////                itemView = LayoutInflater.from(parent.getContext())
////                        .inflate(R.layout.view_text_small_header, parent, false);
////                return new BookingOptionsAdapter.TextSmallHeader(itemView);
////        }
////        throw new RuntimeException("No match for " + viewType + ".");
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        int viewType = 0;
////        switch (bookingOptions.get(position).getViewType()) {
////            case TYPE_HEADER:
////                viewType = TYPE_HEADER;
////                break;
////            case TYPE_TEXT_IMAGE:
////                viewType = TYPE_TEXT_IMAGE;
////                break;
////            case TYPE_TEXT_TEXT:
////                viewType = TYPE_TEXT_TEXT;
////                break;
////            case TYPE_TEXT:
////                viewType = TYPE_TEXT;
////                break;
////            case TYPE_SMALL_HEADER:
////                viewType = TYPE_SMALL_HEADER;
////        }
//        return viewType;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
////        if(holder instanceof BookingOptionsAdapter.TextHeader){
////        } else if (holder instanceof BookingOptionsAdapter.TextImage) {
////        } else if (holder instanceof BookingOptionsAdapter.TextText) {
////        } else if (holder instanceof BookingOptionsAdapter.Text) {
////        } else if (holder instanceof BookingOptionsAdapter.TextSmallHeader) {
////        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return selectedOrPost.size();
//    }
}
