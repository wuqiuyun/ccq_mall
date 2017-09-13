package clerk.sz28yun.com.presenter;

import android.content.Context;

import clerk.sz28yun.com.api.PerformanceAPI;
import clerk.sz28yun.com.bean.PerformanceDayBean;
import clerk.sz28yun.com.bean.PerformanceDayResult;
import clerk.sz28yun.com.bean.PerformanceMonBean;
import clerk.sz28yun.com.bean.PerformanceMonResult;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import java.util.ArrayList;
import java.util.Calendar;

import per.sue.gear2.exception.GearException;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by sue on 2016/11/16.
 */
public class PerformanceDayPresenter extends ListPresenter<ArrayList<PerformanceDayBean>> {

    private PerformanceAPI performanceAPI;
    private int type = 1;
    private String year;
    private String mon;
    private String day;
    String token;
    String menberId;
    PerformanceDayResultView resultView;
    private boolean isQueryByMon;

    public PerformanceDayPresenter(Context context, PerformanceDayResultView resultView) {
        super(context, resultView);
        this.resultView = resultView;
        this.performanceAPI  =  new PerformanceAPI();
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.token = userBean.getToken();
        this.menberId = userBean.getMemberId();
        Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR) + "";
        this.mon =   (calendar.get(Calendar.MONTH) + 1) + "";
//        this.day =   calendar.get(Calendar.DATE) + "";

    }

    public void initialize(){
        Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR) + "";
        this.mon =   (calendar.get(Calendar.MONTH) + 1) + "";
//        this.day =   calendar.get(Calendar.DATE) + "";
    }

    @Override
    public void query() {
        if(!isQueryByMon){//如果是按天查
            subscription =  performanceAPI.getPerformanceDayList(token, menberId, type,year, mon, day,  getPageNum()).subscribe(
                    result ->  {
                        if( null == result ||  null == result.getList()){
                            result = new PerformanceDayResult();
                            result.setList(new ArrayList<>());
                        }
                        if(isRefresh){
                            this.resultView.onSuccessDayRefresh(result.getList());
                            this.resultView.onSuccessStatic(result.getStatistics().currentVip,result.getStatistics().currentGoods,
                                    result.getStatistics().currentMerchants,result.getStatistics().currentPromote);
                        }else{
                            this.resultView.onSuccessDayLoadModre(result.getList());
//                            this.resultView.onSuccessDayRefresh(result.getList());
                            this.resultView.onSuccessStatic(result.getStatistics().currentVip,result.getStatistics().currentGoods,
                                    result.getStatistics().currentMerchants,result.getStatistics().currentPromote);
                        }

                    },
                    throwable ->{
                        if(throwable instanceof GearException){
                            GearException gearException = (GearException)throwable;
                            if(300 == gearException.getCode()){//300 为空数据
                                if(isRefresh){
                                    resultView .onSuccessMonRefresh(new ArrayList<>());
//                                    resultView.onSuccessStatic(result.getStatistics().currentVip,result.getStatistics().currentGoods,
//                                            result.getStatistics().currentMerchants,result.getStatistics().currentPromote);
                                }else{
                                    resultView.onSuccessMonLoadModre(new ArrayList<>());
                                }
                                resultView.onError(gearException.getCode(), throwable.getMessage());
                            }else{
                                resultView.onError(gearException.getCode(), throwable.getMessage());
                            }

                        }else{
                            resultView.onError(-1, throwable.getMessage());
                        }
                    }
            );
            addSubscription(subscription);
        }else{//如果是按月查
            subscription =  performanceAPI.getPerformanceMonList(token, menberId, type,year, mon,  getPageNum()).subscribe(
                    result ->  {
                        if( null == result ||  null == result.getList()){
                            result = new PerformanceMonResult();
                            result.setList(new ArrayList<>());
                        }

                        if(isRefresh){
                            this.resultView.onSuccessMonRefresh(result.getList());
                            this.resultView.onSuccessStatic(result.getStatistics().currentVip,result.getStatistics().currentGoods,
                                    result.getStatistics().currentMerchants,result.getStatistics().currentPromote);
                        }else{
                            this.resultView.onSuccessMonLoadModre(result.getList());
//                            this.resultView.onSuccessMonRefresh(result.getList());
                            this.resultView.onSuccessStatic(result.getStatistics().currentVip,result.getStatistics().currentGoods,
                                    result.getStatistics().currentMerchants,result.getStatistics().currentPromote);
                        }

                    },
                    throwable ->{
                        if(throwable instanceof GearException){
                            GearException gearException = (GearException)throwable;
                            if(300 == gearException.getCode()){//300 为空数据
                                if(isRefresh){
                                    resultView .onSuccessMonRefresh(new ArrayList<>());
//                                    resultView.onSuccessStatic(result.getStatistics().currentVip,result.getStatistics().currentGoods,
//                                            result.getStatistics().currentMerchants,result.getStatistics().currentPromote);
                                }else{
                                    resultView.onSuccessMonLoadModre(new ArrayList<>());
                                }
                                resultView.onError(gearException.getCode(), throwable.getMessage());
                            }else{
                                resultView.onError(gearException.getCode(), throwable.getMessage());
                            }

                        }else{
                            resultView.onError(-1, throwable.getMessage());
                        }
                    }
            );
            addSubscription(subscription);
        }


    }

    public void setType(int type) {
        this.type = type;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public void setDate(String year, String mon){
        this.year = year;
        this.mon = mon;
    }

    public void setQueryByMon(boolean queryByMon) {
        isQueryByMon = queryByMon;
    }

    public boolean isQueryByMon() {
        return isQueryByMon;
    }

    public interface   PerformanceDayResultView extends  ListResultView<ArrayList<PerformanceDayBean>>{

        public void onSuccessMonRefresh(ArrayList<PerformanceMonBean> result);
        public void onSuccessMonLoadModre(ArrayList<PerformanceMonBean> result) ;
        public void onSuccessDayRefresh(ArrayList<PerformanceDayBean> result) ;
        public void onSuccessDayLoadModre(ArrayList<PerformanceDayBean> result) ;
        public void onSuccessStatic(String currentVip, String currentGoods, String currentMerchants, String currentPromote);
    }


}
