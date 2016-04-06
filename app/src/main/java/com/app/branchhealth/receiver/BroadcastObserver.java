package com.app.branchhealth.receiver;

import java.util.Observable;

/**
 * Created by eReFeRHa on 6/8/15.
 */
public class BroadcastObserver extends Observable {

    public void triggerObservers() {
        // Sets the changed flag for this Observable
        setChanged();
        // notify registered observer objects by calling the update method
        notifyObservers();
    }
}
