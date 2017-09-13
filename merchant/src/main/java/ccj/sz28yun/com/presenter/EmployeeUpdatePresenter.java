package ccj.sz28yun.com.presenter;

import android.content.Context;

import org.json.JSONException;

import ccj.sz28yun.com.api.EmployeeAPI;
import ccj.sz28yun.com.api.VerifyAPI;
import ccj.sz28yun.com.bean.EmployeeDetailsBean;
import ccj.sz28yun.com.bean.EmployeeParams;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class EmployeeUpdatePresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private VerifyView view;
    private Context context;
    private EmployeeAPI employeeAPI;
    UserBean userBean ;

    public EmployeeUpdatePresenter(Context context, VerifyView view) {
        this.view = view;
        this.context = context;
        this.employeeAPI = new EmployeeAPI();
        userBean = GlobalDataStorageCache.getInstance().getUserData();
    }

    public void getEmployeeDet(String id){

        this.employeeAPI.getEmployeeDetails(userBean.getToken(), userBean.getStoreId(), id).subscribe(
                result -> { view.onSuccess(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );
    }

    public void add(EmployeeParams employeeParams){
        employeeParams.token = userBean.getToken();
        employeeParams.merchantId = userBean.getStoreId();
        try {
            this.employeeAPI.addEmployee(employeeParams).subscribe(
              result -> { view.onSuccessAdd(result);},
                    throwable -> { view.onError(-1, throwable.getMessage());}
            );
        } catch (JSONException e) {
            e.printStackTrace();
            view.onError(-1, "数据异常， 请联系相关人员");
        }
    }

    public void edit(EmployeeParams employeeParams){
        employeeParams.token = userBean.getToken();
        employeeParams.merchantId = userBean.getStoreId();
        try {
            this.employeeAPI.editEmployee(employeeParams).subscribe(
                    result -> { view.onSuccessEdit(result);},
                    throwable -> { view.onError(-1, throwable.getMessage());}
            );
        } catch (JSONException e) {
            e.printStackTrace();
            view.onError(-1, "数据异常， 请联系相关人员");
        }
    }


    public interface VerifyView extends GearResultView<EmployeeDetailsBean> {

        void onSuccessAdd(String messsage);
        void onSuccessEdit(String messsage);

    }
}
