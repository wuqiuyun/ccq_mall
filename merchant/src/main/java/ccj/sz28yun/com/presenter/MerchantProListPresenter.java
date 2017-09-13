package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.MerchantAPI;
import ccj.sz28yun.com.bean.MerchantProListBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.exception.GearException;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class MerchantProListPresenter extends ListPresenter<ArrayList<MerchantProListBean>> {
    private static final String TAG = "FinanceTypeDepositsPresenter";

    private CouponProView view;
    private Context context;
    private MerchantAPI merchantAPI;
    private String nameKey;
    private String type ;
    private String goods_id ;
    UserBean userBean ;
    public MerchantProListPresenter(Context context, CouponProView view) {
        super(context, view);
        this.view = view;
        this.context = context;
        this.merchantAPI = new MerchantAPI();
        userBean = GlobalDataStorageCache.getInstance().getUserData();

        setQuestBeforeRunable(new Runnable() {
            @Override
            public void run() {
                setObservable(merchantAPI.getProList(userBean.getToken(),userBean.getStoreId(),  getPageNum() ));
            }
        });
    }



    public interface CouponProView extends ListResultView<ArrayList<MerchantProListBean>> {
    }


    @Override
    public void query() {
//        super.query();
        subscription =  merchantAPI.getProList(userBean.getToken(),userBean.getStoreId() ,  getPageNum()).subscribe(
                MerchantProListBean -> {
                    if(isRefresh){
                        view .onSuccessRefresh(MerchantProListBean);
                    }else{
                        view.onSuccessLoadModre(MerchantProListBean);
                    }
                },
                throwable -> {
                    if(throwable instanceof GearException){
                        GearException gearException = (GearException)throwable;
                        if(300 == gearException.getCode()){//300 为空数据
                            if(isRefresh){
                                view .onSuccessRefresh(new ArrayList<>());
                            }else{
                                view.onSuccessLoadModre(new ArrayList<>());
                            }
                        }else{
                            view.onError(gearException.getCode(), throwable.getMessage());
                        }

                    }else{
                        view.onError(-1, throwable.getMessage());
                    }
                }
        );
        addSubscription(subscription);
    }
}
