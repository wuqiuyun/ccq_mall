package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/19.
 */
public class FinanceResult {


    @SerializedName(value = "data1")
    private FinanceFundsBean financeFundsBean;
    @SerializedName(value = "data2")
    private  FinanceBankBean financeBankBean;

    public FinanceFundsBean getFinanceFundsBean() {
        return financeFundsBean;
    }

    public void setFinanceFundsBean(FinanceFundsBean financeFundsBean) {
        this.financeFundsBean = financeFundsBean;
    }

    public FinanceBankBean getFinanceBankBean() {
        return financeBankBean;
    }

    public void setFinanceBankBean(FinanceBankBean financeBankBean) {
        this.financeBankBean = financeBankBean;
    }
}
