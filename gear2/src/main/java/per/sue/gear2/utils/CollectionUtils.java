package per.sue.gear2.utils;

/**
 * Created by SUE on 2016/8/1 0001.
 */
public class CollectionUtils {

    public static int getPositionAtArray(String item, String[] arr) {
        int k = 0;

        if(null == item || null == arr)return -1;

        for (String str : arr) {
            if (str.equals(item)) {
                return k;
            }
            k++;
        }
        k = -1;
        return k;
    }
}
