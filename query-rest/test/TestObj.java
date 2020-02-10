import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 4/18/14
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestObj<K,V> {

    Map<K,Map<K,V>> testMap;

    TestObj()
    {
        testMap = new HashMap<>();
    }

    public void put(K key, Map<K,V> value)
    {
        testMap.put(key, value);
    }

    public Map<K,V> get(K key)
    {
        return testMap.get(key);
    }


}
