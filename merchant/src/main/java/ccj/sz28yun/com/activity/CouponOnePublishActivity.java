package ccj.sz28yun.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.CouponGoodsAdapter;
import ccj.sz28yun.com.base.ActivityCode;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.CouponGoodsBean;
import ccj.sz28yun.com.bean.GoodsCategoryBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.fragment.CouponDoublePublishFragment;
import ccj.sz28yun.com.fragment.CouponOnePublishFragment;
import ccj.sz28yun.com.presenter.CouponGoodsPresenter;
import ccj.sz28yun.com.presenter.CouponOnePublishPresenter;
import ccj.sz28yun.com.presenter.GoodsCategoryListPresenter;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.ToastUtils;

/**
 * 单品券发布
 * Created by sue on 2016/12/19.
 */
public class CouponOnePublishActivity extends CCJActivity  {


    @Bind(R.id.publish_type_one_rb)
    RadioButton publishTypeOneRb;
    @Bind(R.id.publish_type_batch_rb)
    RadioButton publishTypeBatchRb;
    @Bind(R.id.publish_type_rg)
    RadioGroup publishTypeRg;



    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, CouponOnePublishActivity.class);
        return intent;
    }


    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_coupon_one_publish;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {


        getHeadBarView().addRightTextItem("说明", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDescribeDialog();
            }
        });

        publishTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.publish_type_one_rb){
                    replaceFragment(R.id.content_fl, new CouponOnePublishFragment());
                }else{
                    replaceFragment(R.id.content_fl, new CouponDoublePublishFragment());
                }
            }
        });
        replaceFragment(R.id.content_fl, new CouponOnePublishFragment());
    }

    GearCustomDialog gearCustomDialog;
    private void showDescribeDialog(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_coupon_one_publish_describe, null);
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(view)
                .setUseDefult(false)
                .setTitle("说明")
                .create();
        gearCustomDialog.show();

    }



}
