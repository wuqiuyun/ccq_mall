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
import ccj.sz28yun.com.bean.CouponGoodsBean;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.controll.GearImageLoad;

/**
 * Created by sue on 2016/12/19.
 */
public class CouponGoodsAdapter extends ArrayListAdapter<CouponGoodsBean> {

    private ArrayList<Boolean> selectedList = new ArrayList<>();
    private boolean isSingleModel = true;

    private int lastIndex  = -1;

    public CouponGoodsAdapter(Context context) {
        super(context);
    }

    @Override
    public void setList(ArrayList<CouponGoodsBean> list) {
        super.setList(list);
        selectedList.clear();
        for(CouponGoodsBean bean : list){
            selectedList.add(false);
        }

    }

    @Override
    public void addList(ArrayList<CouponGoodsBean> list) {
        super.addList(list);
        for(CouponGoodsBean bean : list){
            selectedList.add(false);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_coupon_publish_goods, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CouponGoodsBean bean = getItem(position);
        viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastIndex = position;
                if(isSingleModel){
                    if(viewHolder.checkbox.isChecked()){
                        for(int i = 0; i < selectedList.size(); i++){
                            selectedList.set(i, false);
                        }

                        selectedList.set(position, true);
                        notifyDataSetChanged();
                    }

                }else{
                    selectedList.set(position, !selectedList.get(position));
                    notifyDataSetChanged();
                    //selectedList.set(position, viewHolder.checkbox.isChecked());
                    //viewHolder.checkbox.setChecked( !viewHolder.checkbox.isChecked());
                }

            }
        });



        viewHolder.checkbox.setChecked(selectedList.get(position));
        GearImageLoad.getSingleton(getContext()).load(bean.getAbsGoodsImage(), viewHolder.goodsImageIv);
        viewHolder.nameTv.setText(bean.getGoodsName());
        viewHolder.priceMallTv.setText(new StringBuilder("门店价: ").append(bean.getGoodsCostprice() + "").append("元"));
        viewHolder.priceStoreTv.setText(new StringBuilder("商城价: ").append(bean.getGoodsCostprice() + "").append("元"));
        viewHolder.unitTv.setText(new StringBuilder("规格: ").append(bean.getSpecName() + ""));

        if(isSingleModel){
            viewHolder.checkbox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selector_choice, 0, 0, 0);
        }else{
            viewHolder.checkbox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selector_ck_tick, 0, 0, 0);
        }
        return convertView;
    }


    public  String getSelectListIdArrayInJson(){
        ArrayList<String> ids = new ArrayList<>();
        for(int i = 0; i < selectedList.size(); i++){
            Boolean b = selectedList.get(i);
            if(b){
                ids.add(getItem(i).getGoodsUnionId());//
            }
        }
        return new Gson().toJson(ids);
    }

    public CouponGoodsBean getLastSelectItem(){
        if(lastIndex != -1){
            return getItem(lastIndex);

        }else{
            return null;
        }

    }

    public void setSingleModel(boolean singleModel) {
        isSingleModel = singleModel;
        if(isSingleModel){
            for(Boolean b : selectedList){
                b = false;
            }
        }

        notifyDataSetChanged();
    }

    public boolean isSingleModel() {
        return isSingleModel;
    }

    static class ViewHolder {
        @Bind(R.id.checkbox)
        CheckBox checkbox;
        @Bind(R.id.goods_image_iv)
        ImageView goodsImageIv;
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.price_store_tv)
        TextView priceStoreTv;
        @Bind(R.id.price_mall_tv)
        TextView priceMallTv;
        @Bind(R.id.unit_tv)
        TextView unitTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
