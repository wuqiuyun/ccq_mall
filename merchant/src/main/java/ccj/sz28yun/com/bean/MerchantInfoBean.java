package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sue on 2017/1/3.
 */
public class MerchantInfoBean {

    @SerializedName(value = "settlement_bank_account_name")
    private String settlementBankAccountName;
    @SerializedName(value = "sum_invite_vip_count")
    private int sumInviteVipCount;
    @SerializedName(value = "invite_member_count")
    private int inviteMemberCount;
    @SerializedName(value = "settlement_bank_name")
    private String settlementBankName;
    @SerializedName(value = "store_name")
    private String storeName;
    @SerializedName(value = "settlement_bank_account_number")
    private String settlementBankAccountNumber;
    @SerializedName(value = "store_address")
    private String storeAddress;
    @SerializedName(value = "company_phone")
    private String companyPhone;
    @SerializedName(value = "contacts_phone")
    private String contactsPhone;
    @SerializedName(value = "img")
    private ArrayList<MerchantImage> img;


    public class  MerchantImage{
        @SerializedName(value = "yinyeurl")
        public String yinyeurl;
        @SerializedName(value = "farenurl")
        public String farenurl;
        @SerializedName(value = "faren_f_url")
        public String farenFUrl;
    }

    public String getSettlementBankAccountName() {
        return settlementBankAccountName;
    }

    public void setSettlementBankAccountName(String settlementBankAccountName) {
        this.settlementBankAccountName = settlementBankAccountName;
    }

    public int getSumInviteVipCount() {
        return sumInviteVipCount;
    }

    public void setSumInviteVipCount(int sumInviteVipCount) {
        this.sumInviteVipCount = sumInviteVipCount;
    }

    public int getInviteMemberCount() {
        return inviteMemberCount;
    }

    public void setInviteMemberCount(int inviteMemberCount) {
        this.inviteMemberCount = inviteMemberCount;
    }

    public String getSettlementBankName() {
        return settlementBankName;
    }

    public void setSettlementBankName(String settlementBankName) {
        this.settlementBankName = settlementBankName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSettlementBankAccountNumber() {
        return settlementBankAccountNumber;
    }

    public void setSettlementBankAccountNumber(String settlementBankAccountNumber) {
        this.settlementBankAccountNumber = settlementBankAccountNumber;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public ArrayList<MerchantImage> getImg() {
        return img;
    }

    public void setImg(ArrayList<MerchantImage> img) {
        this.img = img;
    }
}
