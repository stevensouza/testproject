package com.stevesouza.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.beanutils.PropertyUtils;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class XmlToJson {

    public static void main(String[] arg) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
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

        Map<String, Object> jsonMap = jsonObject.toMap();
        System.out.println(PropertyUtils.getProperty(jsonMap, "ns2:INVResponseMessage.ns2:SFSResponse.ResponseElements.SupportRqstId"));
        System.out.println(get(jsonMap, "ns2:INVResponseMessage.ns2:SFSResponse.ResponseElements"));
        Map<String, Object> responseElements = get(jsonMap, "ns2:INVResponseMessage.ns2:SFSResponse.ResponseElements");
        System.out.println("SupportRqstId="+responseElements.get("SupportRqstId")+", class="+responseElements.get("SupportRqstId").getClass());
        JsonNode jsonNode = xmlToJson(xml);
        // note jackson incorrectly converts the xml array of 3 to an object of 1.
        System.out.println(jsonNode.toPrettyString());

        System.out.println(get(toMap(jsonNode), "SFSResponse.ResponseElements").get("SupportRqstId"));

    }

    public static JsonNode xmlToJson(String xml) throws IOException {
        System.out.println("*** Converting XML to JSON with jackson***");

        // Create a new XmlMapper to read XML tags
        XmlMapper xmlMapper = new XmlMapper();

        //Reading the XML
        JsonNode jsonNode = xmlMapper.readTree(xml);

        //Create a new ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        //Get JSON as a string
        String value = objectMapper.writeValueAsString(jsonNode);

        return jsonNode;

    }

    public static Map<String, Object> toMap(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.convertValue(jsonNode, new TypeReference<Map<String, Object>>(){});
        return result;
    }

    public static Map<String, Object> get(Map<String, Object> jsonMap, String path) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return (Map<String, Object>) PropertyUtils.getProperty(jsonMap, path);
    }

}

