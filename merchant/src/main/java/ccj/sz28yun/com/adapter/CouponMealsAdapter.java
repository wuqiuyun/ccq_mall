package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.CouponMealsBean;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.utils.DoubleUtil;

/**
 * Created by sue on 2016/12/19.
 */
public class CouponMealsAdapter extends ArrayListAdapter<CouponMealsBean> {


    private CouponMealsAdapterListener couponMealsAdapterListener;

    public CouponMealsAdapter(Context context) {
        super(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_coupon_meals, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CouponMealsBean bean = getItem(position);
        viewHolder.nameTv.setText(bean.goodsName);
        viewHolder.priceOriginalTv.setText(new StringBuilder("原价: ").append(bean.goodsMarketprice).append("元"));
        viewHolder.priceStoreTv.setText(new StringBuilder("到店价: ").append(bean.goodsPrice).append("元"));
        viewHolder.unitTv.setText(new StringBuilder("库存: ").append(bean.goodsStorage + ""));
        viewHolder.zhekouTv.setText(new StringBuilder(" ").append(bean.discount + "折"));
        viewHolder.adsTv.setText(bean.remark);
        GearImageLoad.getSingleton(getContext()).load(bean.goodsImage, viewHolder.goodsImageIv);
        viewHolder.editBtn.setOnClickListener(
                v -> {
                    if(null != couponMealsAdapterListener){
                        couponMealsAdapterListener.onEdit(bean);
                    }
                }
        );

        viewHolder.xiajiaBtn.setOnClickListener(
                v -> {
                    if(null != couponMealsAdapterListener){
                        couponMealsAdapterListener.onXiajia(bean);
                    }
                }
        );

        viewHolder.deleteBtn.setOnClickListener(
                v -> {
                    if(null != couponMealsAdapterListener){
                        couponMealsAdapterListener.onDelete(bean);
                    }
                }
        );
        viewHolder.editshangjiaBtn.setOnClickListener(
                v -> {
                    if(null != couponMealsAdapterListener){
                        couponMealsAdapterListener.onEdit(bean);
                    }
                }
        );
        viewHolder.editlookjujuereasonBtn.setOnClickListener(
                v -> {
                    if(null != couponMealsAdapterListener){
                        couponMealsAdapterListener.onLookJujueReason(bean);
                    }
                }
        );
        viewHolder.editkcBtn.setOnClickListener(
                v -> {
                    if(null != couponMealsAdapterListener){
                        couponMealsAdapterListener.onEditKucun(bean);
                    }
                }
        );

//（1单品券 3套餐券）	1 待上线 2 已上线 3 已下线
        if(Integer.parseInt(bean.state)== 1 ){
            if(Integer.parseInt(bean.ischan) == 3 ){
                viewHolder.editBtn.setVisibility(View.VISIBLE);
            }else{
                viewHolder.editBtn.setVisibility(View.GONE);
            }
            viewHolder.editlookjujuereasonBtn.setVisibility(View.GONE);
            viewHolder.xiajiaBtn.setVisibility(View.GONE);
            viewHolder.deleteBtn.setVisibility(View.GONE);
            viewHolder.editshangjiaBtn.setVisibility(View.GONE);
            viewHolder.editkcBtn.setVisibility(View.GONE);
        }else if(Integer.parseInt(bean.state) == 2 ){
            if(Integer.parseInt(bean.ischan) == 3 ){
                viewHolder.editBtn.setVisibility(View.VISIBLE);
                viewHolder.editkcBtn.setVisibility(View.VISIBLE);
            }else{
                viewHolder.editBtn.setVisibility(View.GONE);
                viewHolder.editkcBtn.setVisibility(View.VISIBLE);
            }
            viewHolder.editlookjujuereasonBtn.setVisibility(View.GONE);
            viewHolder.xiajiaBtn.setVisibility(View.VISIBLE);
            viewHolder.deleteBtn.setVisibility(View.GONE);
            viewHolder.editshangjiaBtn.setVisibility(View.GONE);
        }else if(Integer.parseInt(bean.state) == 3){
            if(Integer.parseInt(bean.ischan) == 3 ){
                viewHolder.editshangjiaBtn.setVisibility(View.VISIBLE);
            }else{
                viewHolder.editshangjiaBtn.setVisibility(View.GONE);
            }
            viewHolder.editlookjujuereasonBtn.setVisibility(View.GONE);
            viewHolder.deleteBtn.setVisibility(View.VISIBLE);
            viewHolder.xiajiaBtn.setVisibility(View.GONE);
            viewHolder.editBtn.setVisibility(View.GONE);
            viewHolder.editkcBtn.setVisibility(View.GONE);
        }else if(Integer.parseInt(bean.state) == 4){
            if(Integer.parseInt(bean.ischan) == 3 ){
                viewHolder.editshangjiaBtn.setVisibility(View.GONE);
            }else{
                viewHolder.editshangjiaBtn.setVisibility(View.GONE);
            }
            viewHolder.editlookjujuereasonBtn.setVisibility(View.VISIBLE);
            viewHolder.deleteBtn.setVisibility(View.VISIBLE);
            viewHolder.xiajiaBtn.setVisibility(View.GONE);
            viewHolder.editBtn.setVisibility(View.GONE);
            viewHolder.editkcBtn.setVisibility(View.GONE);
        }


        return convertView;
    }

    public interface CouponMealsAdapterListener{

        void onDelete(CouponMealsBean bean );
        void onXiajia(CouponMealsBean bean );
        void onEdit(CouponMealsBean bean);
        void onLookJujueReason(CouponMealsBean bean);
        void onEditKucun(CouponMealsBean bean);
    }

    public void setCouponMealsAdapterListener(CouponMealsAdapterListener couponMealsAdapterListener) {
        this.couponMealsAdapterListener = couponMealsAdapterListener;
    }

    class ViewHolder {
        @Bind(R.id.goods_image_iv)
        ImageView goodsImageIv;
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.price_original_tv)
        TextView priceOriginalTv;
        @Bind(R.id.zhekou_tv)
        TextView zhekouTv;
        @Bind(R.id.price_store_tv)
        TextView priceStoreTv;
        @Bind(R.id.unit_tv)
        TextView unitTv;

        @Bind(R.id.ads_tv)
        TextView adsTv;
        @Bind(R.id.delete_btn)
        TextView deleteBtn;
        @Bind(R.id.edit_btn)
        TextView editBtn;
        @Bind(R.id.xiajia_btn)
        TextView xiajiaBtn;
        @Bind(R.id.edit_shangjiabtn)
        TextView editshangjiaBtn;
        @Bind(R.id.edit_lookjujuereasonbtn)
        TextView editlookjujuereasonBtn;
        @Bind(R.id.edit_kc_btn)
        TextView editkcBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
