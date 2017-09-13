package clerk.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import clerk.sz28yun.com.R;
import clerk.sz28yun.com.bean.PerformanceMerchantBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.controll.GearImageLoad;

/**
 * Created by sue on 2016/11/21.
 */
public class PerformanceMerchantAdapter extends ArrayListAdapter<PerformanceMerchantBean> {
    public PerformanceMerchantAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        PerformanceMerchantBean bean = getItem(position);
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_performance_merchant, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GearImageLoad.getSingleton(getContext()).load(bean.getUnionImg() , viewHolder.imageIv);
        viewHolder.merchantNameTv.setText(bean.getStoreName());
        viewHolder.merchantCountCcjTv.setText(new StringBuilder("餐餐券上传: ").append(bean.getSaleGoodsNum()));
        viewHolder.merchantCountVerifyTv.setText(new StringBuilder("已验证: ").append(bean.getSaleCheckGoodsNum()));
        viewHolder.merchantMemberNumTv.setText(new StringBuilder("注册会员: ").append(bean.getInviteMemberCount()));
        viewHolder.merchantMemberVipNumTv.setText(new StringBuilder("VIP会员: ").append(bean.getSumInviteVipCount()));

        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.image_iv)
        ImageView imageIv;
        @Bind(R.id.merchant_name_tv)
        TextView merchantNameTv;
        @Bind(R.id.merchant_count_ccj_tv)
        TextView merchantCountCcjTv;
        @Bind(R.id.merchant_count_verify_tv)
        TextView merchantCountVerifyTv;
        @Bind(R.id.merchant_member_num_tv)
        TextView merchantMemberNumTv;
        @Bind(R.id.merchant_member_vip_num_tv)
        TextView merchantMemberVipNumTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
