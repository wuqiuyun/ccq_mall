package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.MerchantAdsImageBean;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.controll.GearImageLoad;

/**
 * Created by sue on 2017/1/3.
 */
public class MerchantAdsImageAdapter extends ArrayListAdapter<MerchantAdsImageBean> {

    private OnMerchantAdsImageAdapterListener onMerchantAdsImageAdapterListener;

    public MerchantAdsImageAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_merchant_ads_image, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MerchantAdsImageBean bean = getItem(position);
        GearImageLoad.getSingleton(getContext()).load(bean.getAbsImages(), viewHolder.adsImageIv, R.mipmap.bg_zhuangxiu);

        viewHolder.editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onMerchantAdsImageAdapterListener){
                    onMerchantAdsImageAdapterListener.onEdit(bean, position);
                }

            }
        });

        viewHolder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onMerchantAdsImageAdapterListener){
                    onMerchantAdsImageAdapterListener.onDelete(bean, position);
                }
            }
        });

        return convertView;
    }

    public void setOnMerchantAdsImageAdapterListener(OnMerchantAdsImageAdapterListener onMerchantAdsImageAdapterListener) {
        this.onMerchantAdsImageAdapterListener = onMerchantAdsImageAdapterListener;
    }

    public  interface OnMerchantAdsImageAdapterListener{
        void onDelete(MerchantAdsImageBean bean, int position);
        void onEdit(MerchantAdsImageBean bean, int position);

    }

    static class ViewHolder {
        @Bind(R.id.ads_image_iv)
        ImageView adsImageIv;
        @Bind(R.id.edit_tv)
        TextView editTv;
        @Bind(R.id.delete_tv)
        TextView deleteTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
