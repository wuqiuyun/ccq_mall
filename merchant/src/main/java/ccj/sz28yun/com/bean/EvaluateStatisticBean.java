package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/6.
 */
public class EvaluateStatisticBean {

    @SerializedName(value = "eva_goods")
    private int evaGoods;
    @SerializedName(value = "goods_eva")
    private int goodsEva;
    @SerializedName(value = "eva_rep")
    private int evaRep;
    @SerializedName(value = "eva_posi")
    private int evaPosi;
    @SerializedName(value = "store_eva")
    private int storeEva;
    @SerializedName(value = "eva_bad")
    private int evaBad;

    public int getEvaGoods() {
        return evaGoods;
    }

    public void setEvaGoods(int evaGoods) {
        this.evaGoods = evaGoods;
    }

    public int getGoodsEva() {
        return goodsEva;
    }

    public void setGoodsEva(int goodsEva) {
        this.goodsEva = goodsEva;
    }

    public int getEvaRep() {
        return evaRep;
    }

    public void setEvaRep(int evaRep) {
        this.evaRep = evaRep;
    }

    public int getEvaPosi() {
        return evaPosi;
    }

    public void setEvaPosi(int evaPosi) {
        this.evaPosi = evaPosi;
    }

    public int getStoreEva() {
        return storeEva;
    }

    public void setStoreEva(int storeEva) {
        this.storeEva = storeEva;
    }

    public int getEvaBad() {
        return evaBad;
    }

    public void setEvaBad(int evaBad) {
        this.evaBad = evaBad;
    }
}
