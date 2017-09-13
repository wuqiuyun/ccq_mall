package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.EmployeeAPI;
import ccj.sz28yun.com.api.MerchantAPI;
import ccj.sz28yun.com.bean.EmployeeBean;
import ccj.sz28yun.com.bean.MerchantChainBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by sue on 2016/12/15.
 */
public class EmployeeListPresenter extends ListPresenter<ArrayList<EmployeeBean>> {

    private EmployeeListView messageView;
    private EmployeeAPI employeeAPI;
    UserBean userBean;
    public EmployeeListPresenter(Context context, EmployeeListView listResultView) {
        super(context, listResultView);
        this.messageView = listResultView;
        this.employeeAPI = new EmployeeAPI();
        userBean = GlobalDataStorageCache.getInstance().getUserData();
    }


    @Override
    public void query() {
        employeeAPI.getEmployeeList(userBean.getToken(), userBean.getStoreId(), null,  getPageNum()).subscribe(
                result -> {
                    if (isRefresh) {
                        this.messageView.onSuccessRefresh(result);
                    } else {
                        this.messageView.onSuccessLoadModre(result);
                    }
                },
                throwable -> {
                    messageView.onError(-1, throwable.getMessage());
                }
        );
    }

    public  void delete(String targetId){
        this.employeeAPI.deleteEmployee(userBean.getToken(),  targetId).subscribe(
                result -> { messageView.onSuccessDeleted(result);},
                throwable -> { messageView.onError(-1, throwable.getMessage());}
        );
    }





    public interface EmployeeListView extends ListResultView<ArrayList<EmployeeBean>> {
        public void onSuccessDeleted(String message);

    }
}
