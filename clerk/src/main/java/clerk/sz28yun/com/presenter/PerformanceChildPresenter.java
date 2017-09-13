package clerk.sz28yun.com.presenter;

import android.content.Context;

import clerk.sz28yun.com.api.PerformanceAPI;
import clerk.sz28yun.com.bean.PerformanceChildMemberBean;
import clerk.sz28yun.com.bean.PerformanceChildMemberResult;
import clerk.sz28yun.com.bean.PerformanceChildMerchantBean;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import per.sue.gear2.exception.GearException;
import per.sue.gear2.presenter.ListPresenter;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by sue on 2016/11/16.
 */
public class PerformanceChildPresenter extends ListPresenter<ArrayList<PerformanceChildMemberBean>> {

    private Context context;
    private PerformanceChildView businessView;
    private PerformanceAPI performanceAPI;
    String token;
    String menberId;
    String type;
    String detaildata;
    private boolean isQueryMember = true;

    private long currentTime;
    public PerformanceChildPresenter(Context context, PerformanceChildView listResultView) {
        super(context, listResultView);
        businessView = listResultView;
        this.performanceAPI  =  new PerformanceAPI();
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.token = userBean.getToken();
        this.menberId = userBean.getMemberId();
        currentTime = System.currentTimeMillis();

    }


    @Override
    public void query() {

        if(isQueryMember){
            int year = DateUtils.getYear(new Date(currentTime));
            int month =  DateUtils.getMonth(new Date(currentTime));
            performanceAPI.getPerformanceChildList(token, menberId, year + "", month + "", getPageNum(), type).subscribe(
                    performanceChildResult -> {
                        if(isRefresh){
                            businessView.onSuccessStatistic(performanceChildResult.getStatistic());
                            businessView .onSuccessRefresh(performanceChildResult.getList());
                        }else{
                            businessView.onSuccessLoadModre(performanceChildResult.getList());
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
                            }else{
                                businessView.onError(gearException.getCode(), throwable.getMessage());
                            }

                        }else{
                            businessView.onError(-1, throwable.getMessage());
                        }
                    }
            );
        }else{
//            performanceAPI.getPerformanceChildMerchantList(token, menberId, DateUtils.getDate(currentTime), getPageNum()).subscribe(
            performanceAPI.getPerformanceChildMerchantList(token, menberId, detaildata, getPageNum()).subscribe(
                    list -> {
                        if(isRefresh){
                            businessView.onSuccessMerchantRefresh(list);
                        }else{
                            businessView.onSuccesssMerchantLoadModre(list);
                        }
                    },

                    throwable -> {
                        if(throwable instanceof GearException){
                            GearException gearException = (GearException)throwable;
                            if(300 == gearException.getCode()){//300 为空数据
                                if(isRefresh){
                                    businessView .onSuccessMerchantRefresh(new ArrayList<>());
                                }else{
                                    businessView.onSuccesssMerchantLoadModre(new ArrayList<>());
                                }
                            }else{
                                businessView.onError(gearException.getCode(), throwable.getMessage());
                            }

                        }else{
                            businessView.onError(-1, throwable.getMessage());
                        }
                    }
            );
        }



    }

    public void setMenberId(String menberId) {
        this.menberId = menberId;
    }
    public void setType(String type) {
        this.type = type;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setQueryMember(boolean queryMember) {
        isQueryMember = queryMember;
    }
    public void setData(String detaildata) {
        this.detaildata = detaildata;
    }

    public interface  PerformanceChildView extends ListResultView<ArrayList<PerformanceChildMemberBean>>{
        void onSuccessStatistic(PerformanceChildMemberResult.PerformanceChildStatistic statistic);

        public void onSuccessMerchantRefresh(ArrayList<PerformanceChildMerchantBean> result);
        public void onSuccesssMerchantLoadModre(ArrayList<PerformanceChildMerchantBean> result) ;
    }



}
