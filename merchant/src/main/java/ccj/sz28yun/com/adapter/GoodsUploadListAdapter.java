package ccj.sz28yun.com.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.GoodsUploadResult;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.controll.GearImageLoad;

/**
 * Created by meihuali on 2017/6/13.
 */

public class GoodsUploadListAdapter extends ArrayListAdapter<GoodsUploadResult> {

    private GoodsUploadListAdapterListener goodsUploadListAdapterListener;

    public GoodsUploadListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_goodsupload, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GoodsUploadResult bean = getItem(position);
        GearImageLoad.getSingleton(getContext()).load(bean.getGoodsImage() , viewHolder.productIv);
        viewHolder.proNameTv.setText(bean.getGoodsName());
        viewHolder.proPriceTv.setText("原价: " + bean.getGoodsCostprice() + "元");
        viewHolder.proSaleTv.setText("默认销售量: " +  bean.getGoodsSalenum() + "        规则：" + bean.getSpecName() );
        viewHolder.proTimeTv.setText("上传时间: " + bean.getGoodsAddtime() );

        if ("1".equals(bean.getIsShelves()) ){//是否已上架 用于判断删除和修改按钮颜色 1：已上架（灰色），0：未上架（红色）

            viewHolder.deleteTv.setVisibility(View.GONE);
            viewHolder.editTv.setVisibility(View.GONE);
            viewHolder.noDeleteTv.setVisibility(View.VISIBLE);
            viewHolder.noEditTv.setVisibility(View.VISIBLE);
        }else{
            viewHolder.deleteTv.setVisibility(View.VISIBLE);
            viewHolder.editTv.setVisibility(View.VISIBLE);
            viewHolder.noDeleteTv.setVisibility(View.GONE);
            viewHolder.noEditTv.setVisibility(View.GONE);
        }

        viewHolder.editTv.setOnClickListener(
                v -> {
                    if (null != goodsUploadListAdapterListener) {
                        goodsUploadListAdapterListener.onEdit(bean);
                    }
                }
        );

        viewHolder.deleteTv.setOnClickListener(
                v -> {
                    if (null != goodsUploadListAdapterListener) {
                        goodsUploadListAdapterListener.onDelete(bean);
                    }
                }
        );
        viewHolder.productIv.setOnClickListener(
                v -> {
                    showBigPicDialog(bean.getGoodsImage());
                }
        );
        return convertView;
    }

    private void showBigPicDialog(String goodsImage) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_bigpic, null);
        ImageView iv_big_pic = (ImageView) view.findViewById(R.id.iv_big_pic);
        GearImageLoad.getSingleton(getContext()).load(goodsImage , iv_big_pic);
        Dialog storePicDialog = new Dialog(getContext(),
                R.style.mDialogStyle);
        storePicDialog.setContentView(view);
        storePicDialog.setCanceledOnTouchOutside(true);
        storePicDialog.show();
    }

    public interface GoodsUploadListAdapterListener {

        void onDelete(GoodsUploadResult bean);

        void onEdit(GoodsUploadResult bean);
    }

    public void setCouponMealsAdapterListener(GoodsUploadListAdapterListener goodsUploadListAdapterListener) {
        this.goodsUploadListAdapterListener = goodsUploadListAdapterListener;
    }

    static class ViewHolder {
        @Bind(R.id.product_iv)
        ImageView productIv;
        @Bind(R.id.pro_name_tv)
        TextView proNameTv;
        @Bind(R.id.pro_price_tv)
        TextView proPriceTv;
        @Bind(R.id.pro_sale_tv)
        TextView proSaleTv;
        @Bind(R.id.pro_time_tv)
        TextView proTimeTv;
        @Bind(R.id.delete_tv)
        TextView deleteTv;
        @Bind(R.id.no_delete_tv)
        TextView noDeleteTv;
        @Bind(R.id.edit_tv)
        TextView editTv;
        @Bind(R.id.no_edit_tv)
        TextView noEditTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
