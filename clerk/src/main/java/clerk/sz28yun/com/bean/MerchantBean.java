package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/11/23.
 */
public class MerchantBean {

    @SerializedName(value = "narea_p")
    private String nareaP;
    @SerializedName(value = "narea_s")
    private String nareaS;
    @SerializedName(value = "addstore_image_01")
    private String addstoreImage_01; //身份证反面面
    @SerializedName(value = "addstore_image_01_id")
    private String addstoreImageID_01; //身份证反面面ID
    @SerializedName(value = "addstore_image_02")
    private String addstoreImage_02; //身份证正面
    @SerializedName(value = "addstore_image_02_id")
    private String addstoreImageID_02; //身份证正面ID
    @SerializedName(value = "addstore_image_03")
    private String addstoreImage_03; //营业执照
    @SerializedName(value = "addstore_image_03_id")
    private String addstoreImageID_03; //营业执照ID
    @SerializedName(value = "addstore_image_04")
    private String addstoreImage_04; //资质证件
    @SerializedName(value = "addstore_image_04_id")
    private String addstoreImageID_04; //资质证件ID

    @SerializedName(value = "thumb_image")
    private String thumbImage; //商家缩略图
    @SerializedName(value = "thumb_image_id")
    private String thumbImageId; //商家缩略图ID
    @SerializedName(value = "banner_1")
    private String banner_1; //banner
    @SerializedName(value = "banner_1_id")
    private String banner_1Id; //bannerID
    @SerializedName(value = "sc_id")
    private String scId;
    @SerializedName(value = "kitchen_list")
//    private ArrayList<String> kitchenList; //厨房相片集合
    private KitchenListBean kitchenListBean;
    @SerializedName(value = "store_list")
//    private ArrayList<String> storeList; //商家相片集合
    private StoreListBean storeListBean;
    @SerializedName(value = "contacts_phone")
    private String contactsPhone;
    @SerializedName(value = "sc_id_name")
    private String scidName;
    @SerializedName(value = "narea_q")
    private String nareaQ;
    @SerializedName(value = "store_name")
    private String storeName;
    @SerializedName(value = "company_master")
    private String companyMaster;
    @SerializedName(value = "member_name")
    private String memberName;
    @SerializedName(value = "company_phone")
    private String companyPhone;
    @SerializedName(value = "id_card_no")
    private String idCardNo;
    @SerializedName(value = "narea_z")
    private String nareaZ;
    @SerializedName(value = "company_address_detail")
    private String companyAddressDetail;
    @SerializedName(value = "member_id")
    private String member_id;
    @SerializedName(value = "longitude")
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @SerializedName(value = "latitude")
    private String latitude;

    public String getBanner_1() {
        return banner_1;
    }

    public void setBanner_1(String banner_1) {
        this.banner_1 = banner_1;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getThumbImageId() {
        return thumbImageId;
    }

    public void setThumbImageId(String thumbImageId) {
        this.thumbImageId = thumbImageId;
    }

    public String getBanner_1Id() {
        return banner_1Id;
    }

    public void setBanner_1Id(String banner_1Id) {
        this.banner_1Id = banner_1Id;
    }

    public String getNareaP() {
        return nareaP;
    }

    public void setNareaP(String nareaP) {
        this.nareaP = nareaP;
    }

    public String getNareaS() {
        return nareaS;
    }

    public void setNareaS(String nareaS) {
        this.nareaS = nareaS;
    }

    public String getAddstoreImage_01() {
        return addstoreImage_01;
    }

    public void setAddstoreImage_01(String addstoreImage_01) {
        this.addstoreImage_01 = addstoreImage_01;
    }

    public String getAddstoreImage_02() {
        return addstoreImage_02;
    }

    public void setAddstoreImage_02(String addstoreImage_02) {
        this.addstoreImage_02 = addstoreImage_02;
    }

    public String getAddstoreImage_03() {
        return addstoreImage_03;
    }

    public void setAddstoreImage_03(String addstoreImage_03) {
        this.addstoreImage_03 = addstoreImage_03;
    }
    public String getAddstoreImage_04() {
        return addstoreImage_04;
    }

    public void setAddstoreImage_04(String addstoreImage_04) {
        this.addstoreImage_04 = addstoreImage_04;
    }

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public String getScidName() {
        return scidName;
    }

    public void setScidName(String scidName) {
        this.scidName = scidName;
    }

    public String getAddstoreImageID_01() {
        return addstoreImageID_01;
    }

    public void setAddstoreImageID_01(String addstoreImageID_01) {
        this.addstoreImageID_01 = addstoreImageID_01;
    }

    public String getAddstoreImageID_02() {
        return addstoreImageID_02;
    }

    public void setAddstoreImageID_02(String addstoreImageID_02) {
        this.addstoreImageID_02 = addstoreImageID_02;
    }

    public String getAddstoreImageID_03() {
        return addstoreImageID_03;
    }

    public void setAddstoreImageID_03(String addstoreImageID_03) {
        this.addstoreImageID_03 = addstoreImageID_03;
    }
    public String getAddstoreImageID_04() {
        return addstoreImageID_04;
    }

    public void setAddstoreImageID_04(String addstoreImageID_04) {
        this.addstoreImageID_04 = addstoreImageID_04;
    }
    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public String getNareaQ() {
        return nareaQ;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public void setNareaQ(String nareaQ) {
        this.nareaQ = nareaQ;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCompanyMaster() {
        return companyMaster;
    }

    public void setCompanyMaster(String companyMaster) {
        this.companyMaster = companyMaster;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getNareaZ() {
        return nareaZ;
    }

    public void setNareaZ(String nareaZ) {
        this.nareaZ = nareaZ;
    }

    public String getCompanyAddressDetail() {
        return companyAddressDetail;
    }

    public void setCompanyAddressDetail(String companyAddressDetail) {
        this.companyAddressDetail = companyAddressDetail;
    }

//    public ArrayList<String> getKitchenList() { return kitchenList;}
//
//    public void setKitchenList(ArrayList<String> kitchenList) {
//        this.kitchenList = kitchenList;
//    }
//
//    public ArrayList<String> getStoreList() {
//        return storeList;
//    }
//
//    public void setStoreList(ArrayList<String> storeList) {
//        this.storeList = storeList;
//    }

    public KitchenListBean getKitchenListBean() {
        return kitchenListBean;
    }

    public void setKitchenListBean(KitchenListBean kitchenListBean) {
        this.kitchenListBean = kitchenListBean;
    }

    public StoreListBean getStoreListBean() {
        return storeListBean;
    }

    public void setStoreListBean(StoreListBean storeListBean) {
        this.storeListBean = storeListBean;
    }


}
