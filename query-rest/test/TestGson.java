import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 4/18/14
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestGson {

    public static void main(String args[]) {
        TestObj testObj = new TestObj<String, String>();

        Map<String, String> valueMap1 = new HashMap<>();
        valueMap1.put("value 1", "10");
        valueMap1.put("value 2", "101");
        valueMap1.put("value 3", "1011");

        Map<String, String> valueMap2 = new HashMap<>();
        valueMap2.put("value 12", "20");
        valueMap2.put("value 22", "202");
        valueMap2.put("value 32", "2022");


        testObj.put("1", valueMap1);

        testObj.put("2", valueMap2);


//        testObj.put("value 2", "101");
//        testObj.put("value 3", "1011");
//        testObj.put("value 4", "1011");
//        testObj.put("value 5", "1011");
//        testObj.put("value 6", "1011");
//        testObj.put("value 7", "1011");
//        testObj.put("value 8", "1011");
//        testObj.put("value 9", "1011");
//        testObj.put("value 10", "1011");

        Gson gson = new Gson();
        String json = gson.toJson(testObj);

        System.out.println("Json : "+json);

    }
}
