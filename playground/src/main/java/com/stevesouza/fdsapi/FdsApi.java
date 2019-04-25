package com.stevesouza.fdsapi;

import com.fdsapi.ResultSetConverter;
import com.fdsapi.arrays.ArraySQL;

/**
 * Created by stevesouza on 8/9/14.
 */
public class FdsApi {
    private static String[] delmeHeader = {"fname"};
    private static String[][] delmeData = {{"steve"}, {"joel"}, {"jeff"},};

    public static void main(String[] args) {
        ResultSetConverter rsc = new ResultSetConverter(delmeHeader, delmeData);
        Object[][] data = new ArraySQL(rsc.getMetaData(), "select * from array").execute(rsc.getResultSet());
        System.out.println(data.length);
        System.out.println(rsc.execute("select * from array"));
    }

}
