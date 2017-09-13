package clerk.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.bean.BusinessBean;
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
public class BusinessListAdapter extends ArrayListAdapter<BusinessBean> {

    private OnBusinessListAdapterListener onBusinessListAdapterListener;

    public BusinessListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        BusinessBean bean = getItem(position);
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_business_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String imageUrl = GlobalDataStorageCache.getInstance().getConfigData().IMG_DOMAIN + bean.getAddstoreImage_02();
        GearImageLoad.getSingleton(getContext()).load(imageUrl , viewHolder.imageIv);
        viewHolder.merchantNameTv.setText(new StringBuilder("商家名称: ").append(bean.getStoreName()));
        viewHolder.merchantAccountTv.setText(new StringBuilder("商家账号: ").append(bean.getSellerName()));
        viewHolder.merchantAddressTv.setText(new StringBuilder("地址: ").append(bean.getCompanyAddressDetail()));
        viewHolder.merchantMemberNumTv.setText(new StringBuilder("注册会员: ").append(bean.getInviteMemberCount()).append("名"));
        viewHolder.merchantMemberVipNumTv.setText(new StringBuilder("VIP会员: ").append(bean.getInviteVipCount()).append("名"));
        viewHolder.merchantDateTv.setText(new StringBuilder(bean.getTimeName()).append(": ").append(DateUtils.getDate(bean.getAddtime() * 1000, DateStyle.YYYY_MM_DD_EN) ));
        viewHolder.merchantStatusTv.setText(bean.getStatusName());
        GearImageLoad.getSingleton(getContext()).load(bean.getAddstoreImage_02(), viewHolder.imageIv);

        if(bean.getJoininState() == 30){
            viewHolder.merchantDetTv.setText("查看理由");
            viewHolder.merchantDetTv.setOnClickListener(
                    v -> {if(null != onBusinessListAdapterListener)onBusinessListAdapterListener.onClickCheckReson(bean);}
            );
        }else{
            viewHolder.merchantDetTv.setText("查看");
            viewHolder.merchantDetTv.setOnClickListener(
                    v -> {if(null != onBusinessListAdapterListener)onBusinessListAdapterListener.onClicCheck(bean);}
            );
        }


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
        @Bind(R.id.merchant_status_tv)
        TextView merchantStatusTv;
        @Bind(R.id.merchant_account_tv)
        TextView merchantAccountTv;
        @Bind(R.id.merchant_address_tv)
        TextView merchantAddressTv;
        @Bind(R.id.merchant_member_num_tv)
        TextView merchantMemberNumTv;
        @Bind(R.id.merchant_member_vip_num_tv)
        TextView merchantMemberVipNumTv;
        @Bind(R.id.merchant_date_tv)
        TextView merchantDateTv;
        @Bind(R.id.merchant_det_tv)
        TextView merchantDetTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
