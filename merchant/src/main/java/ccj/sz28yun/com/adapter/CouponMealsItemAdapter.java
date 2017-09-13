package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.CouponGoodsBean;
import ccj.sz28yun.com.bean.CouponMealsItemBean;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2017/1/4.
 */
public class CouponMealsItemAdapter extends ArrayListAdapter<CouponGoodsBean> {


    private int countTotal;
    private int countChoose;

    private CouponMealsItemAdapterListener couponMealsItemAdapterListener;

    public CouponMealsItemAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_coupon_meals_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CouponGoodsBean goodsBean = getItem(position);
        viewHolder.nameTv.setText(goodsBean.getGoodsName());
        viewHolder.minusIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goodsBean.countChoose > 0){
                    goodsBean.countChoose --;
                    notifyDataSetChanged();

                    if(null != couponMealsItemAdapterListener){
                        int totalTmp = 0;
                        for(CouponGoodsBean bean : list){
                            if (bean.countChoose > 0){
                                totalTmp ++;
                            }
//                            totalTmp += bean.countChoose;
                        }
                        couponMealsItemAdapterListener.onTotalChange(totalTmp);
                    }
                }else{
                   // ToastUtils.showShortMessage("不能再减了", getContext());
                }
            }
        });
        viewHolder.plusIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsBean.countChoose ++;
                if(null != couponMealsItemAdapterListener){
                    int totalTmp = 0;
                    for(CouponGoodsBean bean : list){
                        if (bean.countChoose > 0){
                            totalTmp ++;
                        }
//                        totalTmp += bean.countChoose;
                    }
//                    Toast.makeText(context,"totalTmp" + totalTmp , Toast.LENGTH_SHORT).show();
                    couponMealsItemAdapterListener.onTotalChange(totalTmp);
                }
                notifyDataSetChanged();
            }
        });
        viewHolder.numTv.setText(goodsBean.countChoose + "");
        return convertView;
    }

    public ArrayList<CouponGoodsBean> getSelectItems(){
        ArrayList<CouponGoodsBean> couponMealsItemList = new ArrayList<>();
        for(CouponGoodsBean bean : list){
            if( bean.countChoose > 0){
                couponMealsItemList.add(bean);
            }
        }
        return couponMealsItemList;
    }

    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
    }


    public int getCountTotal() {
        return countTotal;
    }


    public int getCountChoose() {
        return countChoose;
    }


    public void setCountChoose(int countChoose) {
        this.countChoose = countChoose;
    }

    private boolean canPlus(){
        boolean can = true;
        int totalTmp = 0;
        totalTmp ++ ;
        for(CouponGoodsBean bean : list){
            totalTmp += bean.countChoose;
        }

        if(totalTmp > countTotal){
            can = false;
        }
        return can;
    }


    public void setCouponMealsItemAdapterListener(CouponMealsItemAdapterListener couponMealsItemAdapterListener) {
        this.couponMealsItemAdapterListener = couponMealsItemAdapterListener;
    }

    public interface CouponMealsItemAdapterListener{

        void onTotalChange(int total);
    }

    static class ViewHolder {
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.minus_iv)
        ImageView minusIv;
        @Bind(R.id.num_tv)
        TextView numTv;
        @Bind(R.id.plus_iv)
        ImageView plusIv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
