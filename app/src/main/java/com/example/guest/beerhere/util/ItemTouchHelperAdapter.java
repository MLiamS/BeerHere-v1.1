package com.example.guest.beerhere.util;

/**
 * Created by Guest on 6/20/17.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
