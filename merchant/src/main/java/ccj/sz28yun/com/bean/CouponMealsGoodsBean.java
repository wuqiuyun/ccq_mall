package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sue on 2016/12/19.
 */
public class CouponMealsGoodsBean {


   public GoodsCategoryBean goodsCategoryBean;
   public ArrayList<CouponGoodsBean> couponGoodsArrayList;

    public int total;
    public int choose;

    public CouponMealsGoodsBean(GoodsCategoryBean goodsCategoryBean, ArrayList<CouponGoodsBean> couponGoodsArrayList,  int total, int choose) {
        this.goodsCategoryBean = goodsCategoryBean;
        this.couponGoodsArrayList = couponGoodsArrayList;
        this.total = total;
        this.choose = choose;
    }
}
