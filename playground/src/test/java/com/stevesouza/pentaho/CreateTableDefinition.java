package com.stevesouza.pentaho;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generate ddl like the following from pentaho field types.  Note with pentaho there is no need to
 * create the Field object as it has the equivalent of it in the following
 * <p>
 * below outputRowMeta = RowMetaInterface
 * https://github.com/pentaho/pentaho-kettle/blob/master/core/src/main/java/org/pentaho/di/core/row/RowMetaInterface.java
 * <p>
 * ValueMetaInterface = Field (in my code)
 * https://github.com/pentaho/pentaho-kettle/blob/master/core/src/main/java/org/pentaho/di/core/row/ValueMetaInterface.java
 * <p>
 * List<ValueMetaInterface> list = data.outputRowMeta.getValueMetaList();
 * Same as my List<Field>
 * <p>
 * CREATE TABLE gsa_fas_finance.tbl_actual_ytd_plan_1 (
 * `FiscalYear` int(11) DEFAULT NULL,
 * `AccountingPeriod` int(11) DEFAULT NULL,
 * `Region` varchar(255) DEFAULT NULL,
 * `SegmentAgency` varchar(255) DEFAULT NULL,
 * `SegmentPortfolio` varchar(255) DEFAULT NULL,
 * `SegmentSummary` varchar(255) DEFAULT NULL,
 * `SegmentDetail` varchar(255) DEFAULT NULL,
 * `LineSummarySequence` int(11) DEFAULT NULL,
 * `LineMidlevelSequence` int(11) DEFAULT NULL,
 * `SummaryLabel` varchar(255) DEFAULT NULL,
 * `MidlevelLabel` varchar(255) DEFAULT NULL,
 * `LineDateLabel` varchar(255) DEFAULT NULL,
 * `PLLineItem` varchar(255) DEFAULT NULL,
 * `ActualPlanForecast` varchar(255) DEFAULT NULL,
 * `Amount` decimal(14,2) DEFAULT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 */
public class CreateTableDefinition {

    private final String tableName;
    private final List<Field> fields;

    public CreateTableDefinition(String tableName, List<Field> fields) {
        this.tableName = tableName;
        this.fields = fields;
    }

    public static void main(String[] args) {
        List<Field> fields = new ArrayList<>();
        fields.add(new Field("fiscalYear", "Number", 15, 2));
        fields.add(new Field("accountingPeriod", "String", 35, 0));
        fields.add(new Field("fname", "String", 100, 0));
        fields.add(new Field("age", "Integer", 35, 0));
        fields.add(new Field("lname", "XXXnoexist", 155, 0));

        CreateTableDefinition table = new CreateTableDefinition("mytable", fields);
        System.out.println(table.getTableDefinition());
    }

    /**
     * Taken a list of fields objects generate table ddl from it.
     */
    public String getTableDefinition() {
        final String ROW_SUFFIX = ",\n";
        return fields.stream().map(f -> getFieldDdl(f))
                .collect(Collectors.joining(ROW_SUFFIX, getTablePrefix(), getTableSuffix()));
    }

    private String getTablePrefix() {
        return String.format("CREATE TABLE IF NOT EXISTS %s (\n", tableName);
    }

    private String getTableSuffix() {
        return "\n) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
    }

    /*
    Returns rows like the following given a Field metadata from Pentaho
        `FiscalYear` int DEFAULT NULL,
        `Region` varchar(255) DEFAULT NULL,
     */
    public String getFieldDdl(Field field) {
        StringBuilder sb = new StringBuilder("    ")
                .append("`").append(field.getName()).append("` ");

        switch (field.getType()) {
            case "String":
                sb.append(String.format("varchar(%d)", field.getLength()));
                break;
            case "Number":
                sb.append(String.format("decimal(%d,%d)", field.getLength(), field.getPrecision()));
                break;
            case "Integer":
                sb.append("int");
                break;
            default:
                sb.append("varchar(255)");
                break;
        }

        return sb.append(" DEFAULT NULL").toString();
    }
}
