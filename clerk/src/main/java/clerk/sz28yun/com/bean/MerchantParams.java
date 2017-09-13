package clerk.sz28yun.com.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 添加商家数据提交参数
 * Created by sue on 2016/11/23.
 */
public class MerchantParams implements Serializable {

    public String localId = UUID.randomUUID().toString(); //默认有个唯一ID
    public long localDate;

    public String token; //token
    @SerializedName(value = "member_name")
    public String memberName; //商家会员
    @SerializedName(value = "member_id")
    public String memberId; //商家会员Id
    @SerializedName(value = "store_name")
    public String merchantName; //商家名称
    @SerializedName(value = "company_master")
    public String merchantLegalPerson; //商家法人
    @SerializedName(value = "id_card_no")
    public String merchantLegalPersonID; //法人身份证

    @SerializedName(value = "contacts_phone")
    public String constactsMobileContact; //联系人电话
    @SerializedName(value = "company_phone")
    public String constactsMobileServicet; //服务电话

    @SerializedName(value = "sc_id")
    public String merchantCategoryId; //商家分类ID

    @SerializedName(value = "sc_id_name")
    public String merchantCategoryName;

    @SerializedName(value = "narea_p")
    public String addressProvince; //省 (如果没有请返回0)
    @SerializedName(value = "narea_s")
    public String addressCity; //市(如果没有请返回0)
    @SerializedName(value = "narea_q")
    public String addressArea; //区(如果没有请返回0)

    @SerializedName(value = "narea_z")
    public String addressTown; //镇 (如果没有请返回0)

    @SerializedName(value = "company_address_detail")
    public String addressDetails; //商家详细地址

    @SerializedName(value = "longitude")
    public String longitude; //经度

    @SerializedName(value = "latitude")
    public String latitude; //纬度

    @SerializedName(value = "addstore_image_01")
    public String imageBase; //上传基本图片 [ 15, 19, 20 ] 顺序与为界面上 左到右
    @SerializedName(value = "addstore_image_02")
    public String imageMerchant; //上传商家图片  [ 15, 19, 20 ] 顺序与为界面上 左到右
    @SerializedName(value = "addstore_image_03")
    public String imageKitchen; //上传厨房图片  [ 15, 19, 20 ] 顺序与为界面上 左到右
    @SerializedName(value = "addstore_image_04")
    public String imageMerchantSn; //上传商家缩略图片  [ 15, 19, 20 ] 顺序与为界面上 左到右
    @SerializedName(value = "addstore_image_05")
    public String imageMerchantBanner; //上传商家banner图片  [ 15, 19, 20 ] 顺序与为界面上 左到右

    public ArrayList<ImageUploadResult> imageBaseList = new ArrayList<>(4);
    public ArrayList<ImageUploadResult> imageMerchantList = new ArrayList<>(3);
    public ArrayList<ImageUploadResult> imageKitchenList = new ArrayList<>(3);
    public ArrayList<ImageUploadResult> imageMerchantSnList = new ArrayList<>(1);
    public ArrayList<ImageUploadResult> imageMerchantBannerList = new ArrayList<>(1);

    public void initialize() {
        for (int i = 0; i < 5; i++) {
            imageBaseList.add(new ImageUploadResult());
            imageMerchantList.add(new ImageUploadResult());
            imageKitchenList.add(new ImageUploadResult());
            imageMerchantSnList.add(new ImageUploadResult());
            imageMerchantBannerList.add(new ImageUploadResult());
        }
    }

    public void changeImageListToJson() {
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("id1", imageBaseList.get(0).getId());
        map1.put("id2", imageBaseList.get(1).getId());
        map1.put("id3", imageBaseList.get(2).getId());
        map1.put("id4", imageBaseList.get(3).getId());

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("id1", imageMerchantList.get(0).getId());
        map2.put("id2", imageMerchantList.get(1).getId());
        map2.put("id3", imageMerchantList.get(2).getId());

        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("id1", imageKitchenList.get(0).getId());
        map3.put("id2", imageKitchenList.get(1).getId());
        map3.put("id3", imageKitchenList.get(2).getId());

        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("id1", imageMerchantSnList.get(0).getId());

        Map<String, String> map5 = new HashMap<String, String>();
        map5.put("id1", imageMerchantBannerList.get(0).getId());

        this.imageBase = new Gson().toJson(map1);
        this.imageMerchant = new Gson().toJson(map2);
        this.imageKitchen = new Gson().toJson(map3);
        this.imageMerchantSn = new Gson().toJson(map4);
        this.imageMerchantBanner = new Gson().toJson(map5);

    }

    @SerializedName(value = "type")
    public String type; //新增商家 0 修改商家1 (修改商家时填充除了member_id的所以字段)

    public static MerchantParams fromMerchantBean(MerchantBean result) {
        MerchantParams params = new MerchantParams();
        params.memberName = result.getMemberName();
        params.addressDetails = result.getCompanyAddressDetail();
        params.merchantLegalPerson = result.getCompanyMaster();
        params.constactsMobileServicet = result.getCompanyPhone();
        params.constactsMobileContact = result.getContactsPhone();
        params.merchantLegalPersonID = result.getIdCardNo();
        params.addressProvince = result.getNareaP();
        params.addressArea = result.getNareaQ();
        params.addressCity = result.getNareaS();
        params.addressTown = result.getNareaZ();
        params.merchantCategoryId = result.getScId();
        params.merchantCategoryName = result.getScidName();
        params.merchantName = result.getStoreName();
        params.memberId = result.getMember_id();
        params.latitude = result.getLatitude();
        params.longitude = result.getLongitude();

        ImageUploadResult im = new ImageUploadResult();
        im.setUrl(result.getKitchenListBean().getKitchenList_01());

//        params.imageBase = result.getAddstoreImage_01();//身份证正面
//        params.memberName = result.getAddstoreImageID_01();
//        params.imageMerchant = result.getAddstoreImage_02();//身份证反面
//        params.memberName = result.getAddstoreImageID_02();
//        params.imageKitchen = result.getAddstoreImage_03();//营业执照
//        params.memberName = result.getAddstoreImageID_03();
//        params.imageKitchen = result.getAddstoreImage_04();//资质证件
//        params.memberName = result.getAddstoreImageID_04();

//        params.imageMerchantSn = result.getThumbImage();//缩略图
//        params.memberName = result.getThumbImageId();
//        params.imageMerchantBanner = result.getBanner_1();//banner图
//        params.memberName = result.getBanner_1Id();


        params.type = "1";

        if (result.getBanner_1Id() == null) {
            params.imageMerchantBannerList.add(new ImageUploadResult());
        } else {
            params.imageMerchantBannerList.add(new ImageUploadResult(result.getBanner_1Id(), result.getBanner_1()));
        }
        if (result.getThumbImageId() == null) {
            params.imageMerchantSnList.add(new ImageUploadResult());
        } else {
            params.imageMerchantSnList.add(new ImageUploadResult(result.getThumbImageId(), result.getThumbImage()));
        }

        StoreListBean storeListBean = result.getStoreListBean();
        if (null != storeListBean) {
            if (storeListBean.getStoreListid_01() == null) {
                params.imageMerchantList.add(new ImageUploadResult());
            } else {
                params.imageMerchantList.add(new ImageUploadResult( storeListBean.getStoreListid_01(),storeListBean.getStoreList_01()));
            }
            if (result.getStoreListBean().getStoreListid_02() == null) {
                params.imageMerchantList.add(new ImageUploadResult());
            } else {
                params.imageMerchantList.add(new ImageUploadResult( storeListBean.getStoreListid_02(),storeListBean.getStoreList_02()));
            }
            if (result.getStoreListBean().getStoreListid_03() == null) {
                params.imageMerchantList.add(new ImageUploadResult());
            } else {
                params.imageMerchantList.add(new ImageUploadResult( storeListBean.getStoreListid_03(),storeListBean.getStoreList_03()));
            }
        } else {
            for (int i = 0; i < 3; i++) {
                params.imageMerchantList.add(new ImageUploadResult());
            }
        }

        KitchenListBean kitchenListBean = result.getKitchenListBean();
        if (null != kitchenListBean) {
            if (kitchenListBean.getKitchenListID_01() == null) {
                params.imageKitchenList.add(new ImageUploadResult());
            } else {
                params.imageKitchenList.add(new ImageUploadResult( result.getKitchenListBean().getKitchenListID_01(),result.getKitchenListBean().getKitchenList_01()));
            }
            if (result.getKitchenListBean().getKitchenListID_02() == null) {
                params.imageKitchenList.add(new ImageUploadResult());
            } else {
                params.imageKitchenList.add(new ImageUploadResult( result.getKitchenListBean().getKitchenListID_02(),result.getKitchenListBean().getKitchenList_02()));
            }
            if (result.getKitchenListBean().getKitchenListID_03() == null) {
                params.imageKitchenList.add(new ImageUploadResult());
            } else {
                params.imageKitchenList.add(new ImageUploadResult( result.getKitchenListBean().getKitchenListID_03(),result.getKitchenListBean().getKitchenList_03()));
            }
        } else {
            for (int i = 0; i < 3; i++) {
                params.imageKitchenList.add(new ImageUploadResult());
            }
        }


        if (result.getAddstoreImageID_01() == null) {
            params.imageBaseList.add(new ImageUploadResult());
        } else {
            params.imageBaseList.add(new ImageUploadResult(result.getAddstoreImageID_01(), result.getAddstoreImage_01()));
        }
        if (result.getAddstoreImageID_02() == null) {
            params.imageBaseList.add(new ImageUploadResult());
        } else {
            params.imageBaseList.add(new ImageUploadResult(result.getAddstoreImageID_02(), result.getAddstoreImage_02()));
        }
        if (result.getAddstoreImageID_03() == null) {
            params.imageBaseList.add(new ImageUploadResult());
        } else {
            params.imageBaseList.add(new ImageUploadResult(result.getAddstoreImageID_03(), result.getAddstoreImage_03()));
        }
        if (result.getAddstoreImageID_04() == null) {
            params.imageBaseList.add(new ImageUploadResult());
        } else {
            params.imageBaseList.add(new ImageUploadResult(result.getAddstoreImageID_04(), result.getAddstoreImage_04()));
        }

        return params;
    }

}
