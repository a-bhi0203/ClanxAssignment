package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import java.io.*;
import java.util.Properties;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class CommonUtils {
    static RequestSpecification req;
    public RequestSpecification requestSpecification() throws IOException {
        try {
            if(req==null){
                PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
                req = new RequestSpecBuilder().setBaseUri(getPropertyValue("baseUrl"))
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .build();
                return req;
            }
            return req;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getPropertyValue(String keyValue) throws IOException {
        try {
            Properties prop = new Properties();
            FileInputStream fs = new FileInputStream("src/main/resources/global.properties");
            prop.load(fs);
            return prop.getProperty(keyValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject getTestData(String path, String testCaseName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(path);
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            return (JSONObject) jsonObject.get(testCaseName);
        } catch (Exception e) {
            throw e;
        }
    }
}
