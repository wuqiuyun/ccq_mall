package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.EmployeeDetailsBean;
import ccj.sz28yun.com.bean.EmployeeParams;
import ccj.sz28yun.com.bean.GoodsCategoryBean;
import ccj.sz28yun.com.presenter.EmployeeUpdatePresenter;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.MD5Utils;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2017/1/3.
 */
public class EmployeeUpdateActivity extends CCJActivity implements EmployeeUpdatePresenter.VerifyView {


    @Bind(R.id.type_tv)
    TextView typeTv;
    @Bind(R.id.name_et)
    EditText nameEt;
    @Bind(R.id.account_et)
    EditText accountEt;
    @Bind(R.id.mobile_et)
    EditText mobileEt;
    @Bind(R.id.password_et)
    EditText passwordEt;

    private EmployeeParams employeeParams;
    private EmployeeUpdatePresenter employeeUpdatePresenter;

    private String[] typeNameARR = new String[]{"运营员", "财务专员", "运营专员"};
    private int[] typeValueARR = new int[]{1, 10, 11};

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, EmployeeUpdateActivity.class);
        return intent;
    }

    public static Intent startIntent(Context context, String id) {
        Intent intent;
        intent = new Intent(context, EmployeeUpdateActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_employee_update;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        employeeUpdatePresenter = new EmployeeUpdatePresenter(getActivity(), this);
        String id = getIntent().getStringExtra("id");
        employeeParams = new EmployeeParams();
        if(TextUtils.isEmpty(id)){
            setBarTitle("新增员工");
        }else{
            setBarTitle("修改员工");
            employeeParams.sellerId = id;
            employeeUpdatePresenter.getEmployeeDet(id);
        }
    }

    @OnClick({R.id.type_tv, R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.type_tv:
                chooseType();
                break;
            case R.id.submit_btn:
                if(verify()){
                    submit();
                }
                break;
        }
    }

    @Override
    public void onSuccessAdd(String messsage) {
        ToastUtils.showError("添加成功", getApplication());
        finish();

    }

    @Override
    public void onSuccessEdit(String messsage) {
        ToastUtils.showError("修改成功", getApplication());
        finish();
    }

    @Override
    public void onSuccess(EmployeeDetailsBean result) {
        employeeParams.setValueByDetails(result);
        bindView();
    }

    //店员类型(1运营员 10财务专员 11运营专员)
    private void bindView(){
        typeTv.setText(getTypeName(employeeParams.type));
        nameEt.setText(employeeParams.name);
        accountEt.setText(employeeParams.sellerName);
        mobileEt.setText(employeeParams.phone);
    }

    private String getTypeName(int type){
        String typeName = "运营员";
        switch(type){
            case 10:
                typeName = "财务专员";
                break;
            case 11:
                typeName = "运营专员";
                break;
            default:
                break;
        }
        return typeName;
    }

    private void submit(){
        if(TextUtils.isEmpty(employeeParams.sellerId)){
            employeeUpdatePresenter.add(employeeParams);
        }else{
            employeeUpdatePresenter.edit(employeeParams);
        }

    }

    private boolean verify(){
        boolean canSubmit = true;
        String name = nameEt.getText().toString();
        String account = accountEt.getText().toString();
        String mobile = mobileEt.getText().toString();
        String password = passwordEt.getText().toString();

        if(TextUtils.isEmpty(name)){
            nameEt.requestFocus();
            nameEt.setError("请输入姓名");
            canSubmit = false;
        }else if(TextUtils.isEmpty(account)){
            accountEt.requestFocus();
            accountEt.setError("请输入账号");
            canSubmit = false;
        }else if(TextUtils.isEmpty(mobile)){
            mobileEt.requestFocus();
            mobileEt.setError("请输入联系电话");
            canSubmit = false;
        }else if(TextUtils.isEmpty(password)){
            passwordEt.requestFocus();
            passwordEt.setError("请输入密码");
            canSubmit = false;
        }else if( employeeParams.type == 0){
          ToastUtils.showError("请选择店员类型", getApplication());
            canSubmit = false;
        }else{
            employeeParams.name = name;
            employeeParams.password = MD5Utils.encrypt(password);
            employeeParams.phone = mobile;
            employeeParams.sellerName = account;
        }

        return canSubmit;
    }

    /**
     * 第一级分类
     */

    GearCustomDialog gearCustomDialog;
    private void chooseType(){
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(typeNameARR,   new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        employeeParams.type = typeValueARR[position];
                        typeTv.setText(typeNameARR[position]);
                    }
                })
                .setTitle("请选择店员类型")
                .create();
        gearCustomDialog.show();
    }
}
