package nerdvana.com.pointofsales.interfaces;

import java.util.List;

public interface AsyncContract<T> {
    void doneLoading(List<T> list, String isFor);
}
