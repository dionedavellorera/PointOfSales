package nerdvana.com.pointofsales.interfaces;

import java.util.List;

import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;

public interface SafeKeepingContract {
    void reprintSk(List<CollectionFinalPostModel> collectionFinalPostModelList);
}
