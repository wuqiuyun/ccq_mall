package clerk.sz28yun.com.presenter;

import android.content.Context;

import clerk.sz28yun.com.api.MerchantAPI;
import clerk.sz28yun.com.bean.BusinessBean;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import java.util.ArrayList;

import per.sue.gear2.exception.GearException;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by sue on 2016/11/16.
 */
public class BusinessPresenter extends ListPresenter<ArrayList<BusinessBean>> {

    private Context context;
    private ListResultView businessView;
    private MerchantAPI businessAPI;
    private String state ;
    String token;
    String menberId;
    public BusinessPresenter(Context context, ListResultView<ArrayList<BusinessBean>> listResultView) {
        super(context, listResultView);
        businessView = listResultView;
        this.businessAPI  =  new MerchantAPI();
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.token = userBean.getToken();
        this.menberId = userBean.getMemberId();


    }


    @Override
    public void query() {
       // super.query();
       subscription =  businessAPI.getBusinessList(token, menberId, getPageNum(), state).subscribe(

                businessBeens -> {
                    if(isRefresh){
                        businessView .onSuccessRefresh(businessBeens);
                    }else{
                        businessView.onSuccessLoadModre(businessBeens);
                    }
                },
                throwable -> {
                    if(throwable instanceof GearException){
                        GearException gearException = (GearException)throwable;
                        if(300 == gearException.getCode()){//300 为空数据
                            if(isRefresh){
                                businessView .onSuccessRefresh(new ArrayList<>());
                            }else{
                                businessView.onSuccessLoadModre(new ArrayList<>());
                            }
                           /// businessView.onError(gearException.getCode(), throwable.getMessage());
                        }else{
                            businessView.onError(gearException.getCode(), throwable.getMessage());
                        }

                    }else{
                        businessView.onError(-1, throwable.getMessage());
                    }
                }

        );

        addSubscription(subscription);
    }

    public void setMenberId(String menberId) {
        this.menberId = menberId;
    }

    public void setState(String state) {
        this.state = state;
    }





}
