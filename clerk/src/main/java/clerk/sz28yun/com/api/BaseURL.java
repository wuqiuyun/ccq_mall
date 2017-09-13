package clerk.sz28yun.com.api;

/**
 * Created by sue on 2016/11/15.
 */
public class BaseURL {

    public static final String URL_ACCOUNT_PRE_TOKEN = "index/token"; //token检查接口
    public static final String URL_ACCOUNT_CONFIG = "http://app.28yun.com/index.php/webapi_v2/index/config"; //配置文件接口
//    public static final String URL_ACCOUNT_CONFIG = "http://over.28yun.cn/index.php/webapi_v2/index/config"; //配置文件接口 测试环境
//    public static final String URL_ACCOUNT_CONFIG = "http://b.hy.com/index.php/webapi_v2/index/config"; //配置文件接口 上线时测试

    public static final String URL_IMG_UPLOAD = "/public/index.php?s=/webapi/Img2/upload"; //图片上传(传到暂存区)
    //通用接口后缀
    public static final String URL_ACCOUNT_CHECK_TOKEN = "check_token"; //token检查接口
    public static final String URL_ACCOUNT_LOGIN = "index/login"; //登录
    public static final String URL_ACCOUNT_SEND_CODE = "Member/phone_security"; //发送验证码
    public static final String URL_ACCOUNT_VERIFY_CODE = "member/check_code"; //验证验证码
    public static final String URL_ACCOUNT_PASSWORD_FORGET = "marketer/member_pwd"; //重设密码
    public static final String URL_ACCOUNT_PASSWORD_RESET = "index/modify_pw"; //重设密码

    public static final String URL_VERSION_UPDATE = "Tool/versions";//更新版本

    public static final String URL_CONFIG_SHARE = "tool/marketer_config"; //分享汇总
    public static final String URL_STATISTIC = "Marketer/summarizing"; //统计汇总

    public static final String URL_HOME_DATA = "Marketer/index"; //主页数据

    public static final String URL_MERCHANT_LIST = "Marketer/my_business"; //我的业务
    public static final String URL_MERCHANT_ADD = "Marketer/add_store"; //添加商家
    public static final String URL_MERCHANT = "Marketer/check_store"; //查看申请
    public static final String URL_MERCHANT_MEMBER_ACCOUNT_VERIFY = "index/check_member_name";
    public static final String URL_MERCHANT_CATEGORY = "Goodclass/bunding_class"; //商家分类

    public static final String URL_MERCHANT_MEMBER_ACCOUNT_REGIST = "index/reg";

    public static final String URL_PERFORMANCE_MY = "Marketer/my_performance"; //我的业绩
    public static final String URL_PERFORMANCE_MONTH = "Marketer/my_performance_mon"; //按月业绩
    public static final String URL_PERFORMANCE_MERCHANT = "Marketer/store_performance"; //商家业绩
    public static final String URL_STATISTIC_MEMBER = "Marketer/member_count"; //会员统计

    public static final String URL_CHILD_ACCOUNT_LIST = "Marketer/child_list"; //子账号列表
    public static final String URL_CHILD_ACCOUNT_UPDATE = "Marketer/updata_child"; //子账号更新
    public static final String URL_CHILD_ACCOUNT_ADD = "Marketer/add_child"; //子账号更新
    public static final String URL_CHILD_ACCOUNT = "Marketer/child_data"; //子账号

    public static final String URL_CHILD_PERFORMANCE_MEMBER = "Marketer/child_performance_mon"; //子账号业绩
    public static final String URL_CHILD_PERFORMANCE_MERCHANT = "Marketer/child_performance_mon_store"; //子账号商家业绩


}
