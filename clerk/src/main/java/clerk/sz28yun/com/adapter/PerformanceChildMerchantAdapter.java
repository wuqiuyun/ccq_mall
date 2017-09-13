package clerk.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.bean.BusinessBean;
import clerk.sz28yun.com.bean.PerformanceChildMerchantBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import butterknife.Bind;
import butterknife.ButterKnife;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by sue on 2016/11/17.
 */
public class PerformanceChildMerchantAdapter extends ArrayListAdapter<PerformanceChildMerchantBean> {

    private OnBusinessListAdapterListener onBusinessListAdapterListener;

    public PerformanceChildMerchantAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        PerformanceChildMerchantBean bean = getItem(position);
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_performance_child_merchant, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String imageUrl = GlobalDataStorageCache.getInstance().getConfigData().IMG_DOMAIN + bean.getAddstoreImage_02();
        GearImageLoad.getSingleton(getContext()).load(imageUrl , viewHolder.imageIv);
        viewHolder.merchantNameTv.setText(new StringBuilder("商家名称: ").append(bean.getStoreName()));
        viewHolder.merchantAddressTv.setText(new StringBuilder("地址: ").append(bean.getCompanyAddressDetail()));
        viewHolder.merchantMemberNumTv.setText(new StringBuilder("注册会员: ").append(bean.getInviteMemberCount()).append("名"));
        viewHolder.merchantMemberVipNumTv.setText(new StringBuilder("VIP会员: ").append(bean.getInviteVipCount()).append("名"));
        viewHolder.merchantDateTv.setText(new StringBuilder("添加时间").append(": ").append(DateUtils.getDate(bean.getAddtime()*1000, DateStyle.YYYY_MM_DD_EN) ));




        return convertView;
    }

    public void setOnBusinessListAdapterListener(OnBusinessListAdapterListener onBusinessListAdapterListener) {
        this.onBusinessListAdapterListener = onBusinessListAdapterListener;
    }

    public interface  OnBusinessListAdapterListener{

        void onClickCheckReson(BusinessBean bean);
        void onClicCheck(BusinessBean bean);
    }

    static class ViewHolder {
        @Bind(R.id.image_iv)
        ImageView imageIv;
        @Bind(R.id.merchant_name_tv)
        TextView merchantNameTv;

        @Bind(R.id.merchant_address_tv)
        TextView merchantAddressTv;
        @Bind(R.id.merchant_member_num_tv)
        TextView merchantMemberNumTv;
        @Bind(R.id.merchant_member_vip_num_tv)
        TextView merchantMemberVipNumTv;
        @Bind(R.id.merchant_date_tv)
        TextView merchantDateTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
