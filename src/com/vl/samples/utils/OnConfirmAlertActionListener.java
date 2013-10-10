package com.vl.samples.utils;



/**
 * Interface definition for a callback to be invoked when a button is clicked in
 * confirm alert dialog.
 */
public interface OnConfirmAlertActionListener {

    /**
     * Called when a button has been clicked in alert dialog. It will pass the
     * button code (@link ).
     */
    public void onButtonClicked(int buttonCode);

}
