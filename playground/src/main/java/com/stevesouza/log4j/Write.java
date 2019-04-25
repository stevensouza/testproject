package com.stevesouza.log4j;

import org.apache.log4j.Logger;

/**
 * Created by ssouza on 6/2/14.
 */
public class Write {
    private static final Logger LOG = Logger.getLogger(Write.class);

    // test file rollover
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1024; i++) {
            sb.append('a');
        }

        for (int i = 0; i < 1024 * 5; i++) {
            LOG.info("line " + i + " " + sb.toString());
        }

    }
}
