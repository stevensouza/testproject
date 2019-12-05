package com.stevesouza.xml;

import org.json.JSONObject;
import org.json.XML;

public class XmlToJson {

    public static void main(String[] arg) {
        String xml="<note>\n" +
                "<to>Tove</to>\n" +
                "<from>Jani</from>\n" +
                "<heading>Reminder</heading>\n" +
                "<body>Don't forget me this weekend!</body>\n" +
                "</note>";


        System.out.println(XML.toJSONObject(xml).toString(3));

        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "\n" +
                "<ns2:INVResponseMessage\n" +
                "\n" +
                "xmlns=\"http://www.fema.gov/financialservices/schema/ResponseSchema\"\n" +
                "\n" +
                "       xmlns:ns2=\"http://www.example.org/INV_ResponseSchema\"\n" +
                "\n" +
                "xmlns:ns3=\"http://www.fema.gov/financialservices/schema/IFMISSyncSchema\">\n" +
                "\n" +
                "       <ns2:SFSResponse>\n" +
                "\n" +
                "             <ResponseElements>\n" +
                "\n" +
                "                    <ReferenceId>FO2019400FFS001</ReferenceId>\n" +
                "\n" +
                "                    <SupportRqstId>941121</SupportRqstId>\n" +
                "\n" +
                "             </ResponseElements>\n" +
                "\n" +
                "             <IfmisResponseCode>Accepted</IfmisResponseCode>\n" +
                "\n" +
                "             <IfmisResponseDate>2019-09-04T17:57:28.000Z</IfmisResponseDate>\n" +
                "\n" +
                "       </ns2:SFSResponse>\n" +
                "\n" +
                "       <ns2:IFMISSync>\n" +
                "\n" +
                "             <ns3:ScheduleNumber></ns3:ScheduleNumber>\n" +
                "\n" +
                "             <ns3:LineItem>\n" +
                "\n" +
                "                    <ns3:ObligReferenceLine>111</ns3:ObligReferenceLine>\n" +
                "\n" +
                "                    <ns3:Balance></ns3:Balance>\n" +
                "\n" +
                "                    <ns3:InvoiceLineNumber>1</ns3:InvoiceLineNumber>\n" +
                "\n" +
                "             </ns3:LineItem>\n" +
                "\n" +
                "             <ns3:LineItem>\n" +
                "\n" +
                "                    <ns3:ObligReferenceLine>111</ns3:ObligReferenceLine>\n" +
                "\n" +
                "                    <ns3:Balance></ns3:Balance>\n" +
                "\n" +
                "                    <ns3:InvoiceLineNumber>2</ns3:InvoiceLineNumber>\n" +
                "\n" +
                "             </ns3:LineItem>\n" +
                "\n" +
                "             <ns3:LineItem>\n" +
                "\n" +
                "                    <ns3:ObligReferenceLine>111</ns3:ObligReferenceLine>\n" +
                "\n" +
                "                    <ns3:Balance></ns3:Balance>\n" +
                "\n" +
                "                    <ns3:InvoiceLineNumber>3</ns3:InvoiceLineNumber>\n" +
                "\n" +
                "             </ns3:LineItem>\n" +
                "\n" +
                "       </ns2:IFMISSync>\n" +
                "\n" +
                "</ns2:INVResponseMessage>";

        JSONObject jsonObject = XML.toJSONObject(xml);

        System.out.println(jsonObject.toString(3));
        System.out.println(jsonObject.getJSONObject("ns2:INVResponseMessage").getJSONObject("ns2:SFSResponse").getString("IfmisResponseCode"));
        System.out.println(jsonObject.getJSONObject("ns2:INVResponseMessage").getJSONObject("ns2:SFSResponse").getJSONObject("ResponseElements"));
        System.out.println(jsonObject.getJSONObject("ns2:INVResponseMessage").getJSONObject("ns2:SFSResponse").getJSONObject("ResponseElements").get("SupportRqstId"));
        System.out.println(jsonObject.getJSONObject("ns2:INVResponseMessage").getJSONObject("ns2:SFSResponse").getJSONObject("ResponseElements").get("SupportRqstId").getClass());

    }
}
