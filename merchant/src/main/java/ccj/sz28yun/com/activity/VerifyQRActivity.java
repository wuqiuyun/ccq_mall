package ccj.sz28yun.com.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.OrderCCJBean;
import ccj.sz28yun.com.presenter.VerifyPresenter;
import per.sue.gear2.qr.CaptureViewHelper;
import per.sue.gear2.qr.ViewfinderView;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2016/12/4.
 */
public class VerifyQRActivity extends CCJActivity implements VerifyPresenter.VerifyView {


    @Bind(R.id.preview_view)
    SurfaceView previewView;
    @Bind(R.id.viewfinder_view)
    ViewfinderView viewfinderView;

    CaptureViewHelper captureViewHelper;
    VerifyPresenter verifyPresenter;
    @Bind(R.id.open_deng_ck)
    CheckBox openDengCk;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, VerifyQRActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_verify_qr;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        verifyPresenter = new VerifyPresenter(getActivity(), this);

        captureViewHelper = new CaptureViewHelper(this, viewfinderView, previewView);
        captureViewHelper.initialzie();

        captureViewHelper.setOnScanListener(new CaptureViewHelper.onScanListener() {
            @Override
            public void handleDecode(Result result, Bitmap barcode) {
                String codeUrl = result.getText();
                if (codeUrl.equals("")) {
                    Toast.makeText(getApplication(), "Scan failed!", Toast.LENGTH_SHORT).show();
                } else {
                    verifyPresenter.verifyCode(codeUrl);
                }
            }
        });


        openDengCk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                captureViewHelper.flashHandler();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        captureViewHelper.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        captureViewHelper.onPause();
    }

    @Override
    protected void onDestroy() {
        captureViewHelper.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onSuccess(OrderCCJBean result) {
        showLookOrderDialog(result);
        ToastUtils.showError("验证成功", getApplication());
    }

    private Dialog lookOrderdialog;
    private void showLookOrderDialog(OrderCCJBean result) {
        View view = LayoutInflater.from(VerifyQRActivity.this)
                .inflate(R.layout.dialog_blue_moban, null);
        TextView dialog_text = (TextView) view.findViewById(R.id.dialog_text);
        TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        TextView tv_dialog_cancel = (TextView) view
                .findViewById(R.id.tv_dialog_cancel);
        dialog_text.setText("验证成功");
        tv_dialog_cancel.setText("查看订单");
        tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lookOrderdialog.dismiss();
                startActivity(OrderCCJDetailsActivity.startIntent(getActivity(),result));
                finish();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lookOrderdialog.dismiss();
                finish();
            }
        });
        lookOrderdialog = new Dialog(VerifyQRActivity.this,
                R.style.mDialogStyle);
        lookOrderdialog.setContentView(view);
        lookOrderdialog.setCanceledOnTouchOutside(false);
        lookOrderdialog.show();
    }

}
