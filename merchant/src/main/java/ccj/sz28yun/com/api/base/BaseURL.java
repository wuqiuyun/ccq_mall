package ccj.sz28yun.com.api.base;

/**
 * Created by sue on 2016/11/15.
 */
public class BaseURL {

    public static final String URL_ACCOUNT_PRE_TOKEN = "index/token"; //token检查接口
      public static final String URL_ACCOUNT_CONFIG = "http://app.28yun.com/index.php/webapi_v2/index/config"; //配置文件接口
//      public static final String URL_ACCOUNT_CONFIG = "http://over.28yun.cn/index.php/webapi_v2/index/config"; //配置文件接口 测试环境
//      public static final String URL_ACCOUNT_CONFIG = "http://b.hy.com/index.php/webapi_v2/index/config"; //配置文件接口 上线时测试

    public static final String URL_IMG_UPLOAD = "/public/index.php?s=/webapi/Img2/upload"; //图片上传(传到暂存区)
    //通用接口后缀
    public static final String URL_ACCOUNT_CHECK_TOKEN = "check_token"; //token检查接口
    public static final String URL_ACCOUNT_LOGIN = "index/login"; //登录
    public static final String URL_ACCOUNT_SEND_CODE = "Member/phone_security"; //发送验证码
    public static final String URL_ACCOUNT_VERIFY_CODE = "member/check_code"; //验证验证码
    public static final String URL_ACCOUNT_CHECK_PHONE = "Storemember/find_pwd_step1"; //验证商家手机号是否存在
    public static final String URL_ACCOUNT_PASSWORD_FORGET = "Storemember/reset_pwd"; //重设密码
    public static final String URL_ACCOUNT_PASSWORD_RESET = "index/modify_pw"; //重设密码
    public static final String URL_CONFIG_SHARE = "tool/marketer_config"; //分享汇总


    public static final String URL_MERCHANT_MEMBER_ACCOUNT_REGIST = "index/reg";

    public static final String URL_VERSION_UPDATE = "Tool/versions";

    public static final String URL_VERIFY_CODE = "Storeorder/check_code";
    public static final String URL_VERIFY_BILL_SET = "Storeservice/set_pay_bill";
    public static final String URL_VERIFY_BILL_LIST = "Storeorder/pay_bill";
    public static final String URL_VERIFY_BILL_CHECK = "Storeorder/check_pay_bill";
    public static final String URL_VERIFY_BILL_SETTING = "Storeservice/get_pay_bill";

    public static final String URL_GOODS_UPLOAD_LIST = "Storegoods/goods_list";
    public static final String URL_GOODS_UPLOAD_CLEAR = "Storegoods/clear_goods";
    public static final String URL_GOODS_UPLOAD_DELETE = "Storegoods/del_goods";
    public static final String URL_GOODS_UPLOAD_DETAIL_INFO = "Storegoods/update_goods_info";
    public static final String URL_GOODS_CATEGORY_LIST = "Goodclass/bunding_class_mall";
    public static final String URL_GOODS_CATEGORY_LIST_BY_PARENTID = "Storegoods/get_next_class";
    public static final String URL_GOODS_UPLOAD = "Storegoods/add_package_goods";
    public static final String URL_GOODS_SPECIFICATION = "Storegoods/get_package_goods_spec";

    public static final String URL_EVALUATE_STATISTIC = "Storeservice/sum_evaluate";
    public static final String URL_EVALUATE_MERCHANT_LIST = "Storeservice/evaluate";
    public static final String URL_EVALUATE_GOODS_LIST = "Storeservice/evaluate";

    public static final String URL_EVALUATE_MERCHANT_REPLY = "Storeservice/reply";
    public static final String URL_EVALUATE_GOODS_REPLY = "Storeservice/reply";

    public static final String URL_OPERATING_DATA_ORDER = "Storemember/get_order_count";
    public static final String URL_OPERATING_DATA_MERCHANT = "Storeservice/storeflow";

    public static final String URL_ORDER_HISTORY_CCJ = "Storeorder/order_list";
    public static final String URL_ORDER_HISTORY_CONSUME_BILL = "Storeorder/pay_bill";

    public static final String URL_FINANCE_FUNDS = "Storemember/analyze_census";
    public static final String URL_FINANCE_FUNDS_TYPE_MONTH = "Storemember/get_month_wind_list";
    public static final String URL_FINANCE_FUNDS_DEPOSITS = "Storemember/withdraw";
    public static final String URL_FINANCE_FUNDS_FREEZE = "Storemember/withdraw";
    public static final String URL_FINANCE_FUNDS_TYPE_DAY = "Storemember/get_month_wind_detail";
    public static final String URL_FINANCE_FUNDS_DEPOSITS_DETIAL = "Storemember/withdraw_detail";
    public static final String URL_FINANCE_FUNDS_DEPOSITS_APPLY_INFO = "Storemember/tixian_member_info";
    public static final String URL_FINANCE_FUNDS_DEPOSITS_APPLY = "Storemember/add_withdraw";


    public static final String URL_COUPON_GOODS_LIST = "Storegoods/get_package_goods_list";
    public static final String URL_COUPON_PUBLISH_ONE = "Storegoods/add_simple_ccq";
    public static final String URL_COUPON_PUBLISH_DOUBLE = "Storegoods/add_double_ccq";
    public static final String URL_COUPON_MEALS_LIST = "Storegoods/bunding_list";
    public static final String URL_COUPON_CHAIN_LIST = "Storemember/get_chain_list";
    public static final String URL_COUPON_MEALS_ADD = "Storegoods/add_ccq";
    public static final String URL_COUPON_MEALS_EDIT = "Storegoods/add_ccq";
    public static final String URL_COUPON_MEALS_INFO = "Storegoods/get_ccq_detail";
    //下架及删除套餐券
    public static final String URL_XIAJIA_DELETE_TAOCANQUAN = "Storegoods/manage_bunding";
    //单品券列表
    public static final String URL_COUPON_DANPIN_LIST = "Storegoods/simple_bunding_list";
    //修改库存
    public static final String URL_EDIT_KUCUN = "storegoods/update_goods_storage";


    public static final String URL_MEMBER_SYSTEM_NEW_SY = "Storeservice/store_member_index";
    public static final String URL_MEMBER_SYSTEM_MEMBER_COUNT = "Storeservice/member_count";
    public static final String URL_MEMBER_SYSTEM_MEMBER_DETAIL = "Storeservice/member_order";
    public static final String URL_MEMBER_SYSTEM_STATUS = "Storeservice/member_manage_index";
    public static final String URL_MEMBER_SYSTEM_SEND_SIGTRUE = "Storeservice/member_manage_add_sign";
    public static final String URL_MEMBER_SYSTEM_SMS_LIST = "Storeservice/member_manage_sms_list";
    public static final String URL_MEMBER_SYSTEM_SMS_SEND = "Storeservice/member_manage_add_tmp";


    public static final String URL_MESSAGE_LIST = "Storeservice/message";
    public static final String URL_MESSAGE_READ = "Storeservice/is_read";

    public static final String URL_MERCHANT_INFO = "Storeservice/store_info";
    public static final String URL_MERCHANT_INFO_EDIT = "Storeservice/set_store";

    public static final String URL_MERCHANT_SERVICE = "Storemember/get_setting";
    public static final String URL_MERCHANT_SERVICE_EDIT = "Storemember/setting";

    public static final String URL_MERCHANT_PROLIST = "Unionstore/unionstore_goods_list";
    public static final String URL_MERCHANT_ALLPIC = "Storesetting/get_store_joinin_img";
    public static final String URL_MERCHANT_ADS = "Storemember/get_store_image";
    public static final String URL_MERCHANT_ADS_EDIT = "Storemember/set_store_image";
    public static final String URL_MERCHANT_ADS_DELETE = "Storemember/del_store_image";
    public static final String URL_MERCHANT_EDIT_ALLPIC = "StoreSetting/store_joinin_img";
    public static final String URL_MERCHANT_ADS_TEXT_SET = "Storemember/setting";

    public static final String URL_MERCHANT_CHAIN_LIST = "Storemember/get_chain_list";
    public static final String URL_MERCHANT_CHAIN_ADD = "Storemember/add_chain";
    public static final String URL_MERCHANT_CHAIN_DELETE = "Storemember/del_chain";

    public static final String URL_MERCHANT_SETTING_RESET_PASSWORD = "Storemember/set_pwd";

    public static final String URL_EMPLOYEE_LIST = "Storeservice/child_all";
    public static final String URL_EMPLOYEE_ADD = "Storeservice/child_account";
    public static final String URL_EMPLOYEE_DELETE = "Storeservice/del_child";
    public static final String URL_EMPLOYEE_EDIT = "Storeservice/set_child";
    public static final String URL_EMPLOYEE_DETAILS = "Storeservice/child_one";


}
