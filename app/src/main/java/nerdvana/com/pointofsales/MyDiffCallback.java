package nerdvana.com.pointofsales;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.model.RoomTableModel;

public class MyDiffCallback extends DiffUtil.Callback{

    List<RoomTableModel> oldPersons;
    List<RoomTableModel> newPersons;

    public MyDiffCallback(List<RoomTableModel> oldPersons, List<RoomTableModel> newPersons) {
        this.newPersons = newPersons;
        this.oldPersons = oldPersons;
    }

    @Override
    public int getOldListSize() {
        return oldPersons.size();
    }

    @Override
    public int getNewListSize() {
        return newPersons.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//        return true;
        return oldPersons.get(oldItemPosition).getStatus() .equalsIgnoreCase( newPersons.get(newItemPosition).getStatus());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//        return true;
        return oldPersons.get(oldItemPosition).equals(newPersons.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        RoomTableModel result = null;
        if(!oldPersons.get(oldItemPosition).getStatus().equalsIgnoreCase(newPersons.get(newItemPosition).getStatus())){
            result = newPersons.get(newItemPosition);
        }

        return result;
    }
}
