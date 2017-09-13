package per.sue.gear2.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import per.sue.gear2.utils.GearLog;
import per.sue.gear2.utils.ToastUtils;


/**
 * Created by SUE on 2016/7/7 0007.
 */
public abstract class UIBaseFragment extends Fragment implements IBaseView {

    protected ProgressDialog progressDialog;
    protected   final String TAG = this.getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(getLayoutResId(), null);
         ButterKnife.bind(this, view);
         initViews(view);
        GearLog.e(TAG, "FragmentonCreateView();");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }





    protected  void initViews(View view){

    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // GearLog.e(TAG, "onActivityCreated();");
        onInitialize(savedInstanceState);
    }

    public  void showProgressDialog(String tip) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setCancelable(true);
        }
        progressDialog.setMessage(tip);
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void onError(int code, String message) {
       dismissProgressDialog();
        if(null != getContext())
        ToastUtils.showShortMessage(message, getContext());

    }


    public void onCompleted() {

    }

    public void showLoading() {

    }

    public void onLoadFailed() {

    }
}
