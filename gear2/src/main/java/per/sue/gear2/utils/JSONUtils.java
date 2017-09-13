package per.sue.gear2.utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by SUE on 2016/7/28 0028.
 */
public class JSONUtils {

    public static HashMap<String, String> beanToMap(Object object) throws JSONException {
        HashMap<String, String> params = new HashMap<>();
        String json = new Gson().toJson(object);
        params = JSONUtils.toHashMap(json);
        return params;
    }


    public static HashMap<String, String> toHashMap(String json) throws JSONException {
        HashMap<String, String> data = new HashMap<String, String>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = new JSONObject(json);
        Iterator it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            String value = String.valueOf(jsonObject.get(key));
            data.put(key, value);
        }
        return data;
    }
}
