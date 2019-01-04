package nerdvana.com.pointofsales.custom;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import nerdvana.com.pointofsales.interfaces.OverScroll;
//class used for getting overscroll top or bottom from recycler view gridlayout manager
public class GridLayoutManagerOverScroll extends GridLayoutManager {
    private OverScroll overScroll;
    public GridLayoutManagerOverScroll(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public GridLayoutManagerOverScroll(Context context, int spanCount, OverScroll overScroll) {
        super(context, spanCount);
        this.overScroll = overScroll;
    }

    public GridLayoutManagerOverScroll(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrollRange = super.scrollVerticallyBy(dy, recycler, state);
        int overscroll = dy - scrollRange;

        if (overscroll > 0) {
            // bottom overscroll
            this.overScroll.bottomOverScroll();
        } else if (overscroll < 0) {
            // top overscroll
            this.overScroll.topOverScroll();
        }
        return scrollRange;
    }
}
