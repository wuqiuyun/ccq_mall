package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2017/1/3.
 */
public class MerchantInfoParams {

    @SerializedName(value = "token")
    public String token;
    @SerializedName(value = "store_id")
    public String merchantId;
    @SerializedName(value = "store_name")
    public String merchantName;
    @SerializedName(value = "store_address")
    public String merchantAddress;
    @SerializedName(value = "contacts_phone")
    public String frMobile;
    @SerializedName(value = "company_phone")
    public String serviceMobile;
    @SerializedName(value = "settlement_bank_account_name")
    public String contractName;
    @SerializedName(value = "settlement_bank_account_number")
    public String bankAccount;
    @SerializedName(value = "settlement_bank_name")
    public String bankName;

    public MerchantInfoParams(String token, String merchantId) {
        this.token = token;
        this.merchantId = merchantId;
    }

    public void setValueFromMerchantInfo(MerchantInfoBean bean){
        merchantName = bean.getStoreName();
        merchantAddress = bean.getStoreAddress();
        frMobile = bean.getContactsPhone();
        serviceMobile = bean.getCompanyPhone();
        contractName = bean.getSettlementBankAccountName();
        bankAccount = bean.getSettlementBankAccountNumber();
        bankName = bean.getSettlementBankName();
    }
}
