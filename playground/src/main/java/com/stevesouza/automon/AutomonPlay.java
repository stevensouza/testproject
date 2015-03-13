package com.stevesouza.automon;

import org.automon.implementations.NullImp;

/**
 * Created by stevesouza on 3/13/15.
 */
public class AutomonPlay {

    public static void main(String[] args) {
        NullImp so = new NullImp();
        so.exception(null, null);
        so.start(null);
        so.stop(null);
        so.stop(null, null);
    }
}
