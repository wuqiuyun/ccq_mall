package per.sue.gear2.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author su
 * @version Create Time：2015-4-29 下午07:20:10 浮点型计算
 */
public class DoubleUtil {

	/**
	 * 
	 * 两个Double数相加
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return Double
	 */

	public static Double add(Double v1, Double v2) {

		BigDecimal b1 = new BigDecimal(v1.toString());

		BigDecimal b2 = new BigDecimal(v2.toString());

		return b1.add(b2).doubleValue();

	}

	/**
	 * 
	 * 两个Double数相减
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return Double
	 */

	public static Double sub(Double v1, Double v2) {

		BigDecimal b1 = new BigDecimal(v1.toString());

		BigDecimal b2 = new BigDecimal(v2.toString());

		return b1.subtract(b2).doubleValue();

	}

	/**
	 * 
	 * 两个Double数相乘
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return Double
	 */

	public static Double mul(Double v1, Double v2) {

		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.multiply(b2).doubleValue();

	}

	/**
	 * 除法
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double div(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		if (v2 == 0) {
			v2 = 0.000001;
		}
		return v1 / v2;

	}

	public static String FormatNumber(double d) {
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		return nf.format(d);

	}


	/**
	 *格式化价格，保留两位
	 * @param price
	 * @return
	 */
	public static String formatPrice(double price) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(price);
	}

	public static String formatPrice(String  price) {
		if( (null == price || "".equals(price)) || !isDouble(price)){
			price = "0.00";
		}
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(Double.valueOf(price));
	}

	public static boolean isDouble(String str) {
		try
		{
			Double.parseDouble(str);
			return true;
		}
		catch(NumberFormatException ex){}
		return false;
	}

	/**
	 *
	 * @param price
	 * @return
	 */
	public static String format(double price, String fomat) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(price);
	}


	/*public static void main(String[] args) {
		String p = "3.1234";
		System.out.println(formatPrice(p));
	}*/

}
