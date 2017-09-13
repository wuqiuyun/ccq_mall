package per.sue.gear2.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* 文件名：
* 描 述：
* 作 者：苏昭强
* 时 间：2016/5/4
*/
public class VerifyUtils {


    /**
     * 检测手机号有效性*
     *
     * @param mobile 手机号码
     * @return 是否有效
     */
    public static final boolean isMobileNo(String mobile) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[6-8]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }


    /**
     * 检测是否纯数字
     *
     * @param
     * @return 是否有效
     */
    public static final boolean isNumber(String number) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) number);
        boolean result = matcher.matches();
        return result;
    }

    /**
     * 检测是否纯字母
     *
     * @param
     * @return 是否有效
     */
    public static final boolean isLetter(String str) {
        Pattern pattern = Pattern.compile("[a-zA-Z]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) str);
        boolean result = matcher.matches();
        return result;
    }



    /**
     * 验证验证码
     * @param code
     * @return
     */
    public static boolean isCode(String code){
        Pattern pattern  = Pattern.compile("^\\d{6}$"); // 验证验证码
        Matcher matcher = pattern.matcher(code);
        boolean result = matcher.matches();
        return result;
    }

    /**
     * 检测邮箱地址是否合法
     * @param email
     * @return true合法 false不合法
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
//        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public static final boolean isAccount(String str) {
        Pattern pattern = Pattern.compile("[a-z A-Z 0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) str);
        boolean result = matcher.matches();
        return result;
    }

    /*public static void main(String[] args) {

        System.out.print("result = " +  isCode("738113"));

    }*/
}
