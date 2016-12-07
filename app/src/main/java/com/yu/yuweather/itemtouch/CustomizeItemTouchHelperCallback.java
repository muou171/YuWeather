package com.yu.yuweather.itemtouch;


import android.graphics.Canvas;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class CustomizeItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private SwipeRefreshLayout swipeRefreshLayout;

    public CustomizeItemTouchHelperCallback(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    /**
     * 当Item被长按的时候是否可以拖动
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }


    /**
     * 当Item被拖动的时候调用
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (isCurrentlyActive) {
            // 当Item被拖动的时候，禁用SwipeRefreshLayout
            swipeRefreshLayout.setEnabled(false);
        } else {
            // 当Item被拖动松开的时候，启用SwipeRefreshLayout
            swipeRefreshLayout.setEnabled(true);
        }
    }

    /**
     * Item是否可以被滑动
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * 当用户拖拽或者滑动Item的时候需要我们告诉系统滑动或者拖拽的方向
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            int dragFlags = 0;
            int swipeFlags = 0;
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                // 横屏显示
                dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            } else if (orientation == LinearLayoutManager.VERTICAL) {
                // 竖屏显示
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }
        return 0;
    }

    /**
     * 当Item被拖拽的时候被回调
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (onItemTouchCallbackListener != null) {
            return onItemTouchCallbackListener.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return false;
    }

    /**
     * 当Item被滑动的时候被回调
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (onItemTouchCallbackListener != null) {
            onItemTouchCallbackListener.onSwiped(viewHolder.getAdapterPosition());
        }
    }

    private OnItemTouchCallbackListener onItemTouchCallbackListener;

    public void setOnItemTouchCallbackListener(OnItemTouchCallbackListener onItemTouchCallbackListener) {
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    public interface OnItemTouchCallbackListener {
        // 当某个Item被滑动删除的时候
        void onSwiped(int adapterPosition);

        // 当两个Item位置互换的时候被回调
        boolean onMove(int srcPosition, int targetPosition);
    }
}
