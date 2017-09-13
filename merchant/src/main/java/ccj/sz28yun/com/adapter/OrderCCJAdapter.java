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
import ccj.sz28yun.com.bean.OrderCCJBean;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.controll.GearImageLoad;

/**
 * Created by sue on 2016/12/7.
 */
public class OrderCCJAdapter extends ArrayListAdapter<OrderCCJBean> {
    public OrderCCJAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final OrderCCJBean bean = getItem(position);
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_order_history_ccj, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.orderNumTv.setText(new StringBuilder("订单号: ").append(bean.getOrderSn()));
        viewHolder.dateTv.setText(bean.getAddTime() + "");
        viewHolder.userNameTv.setText(new StringBuilder("用户: ").append(bean.getUserName()));
        viewHolder.goodsNameTv.setText(new StringBuilder("商品名称: ").append(bean.getGoodsName()));
        viewHolder.ccjCodeTv.setText(new StringBuilder("餐餐券码: ").append(bean.getCheckNumber()));
        viewHolder.priceSaleTv.setText(new StringBuilder("销售价格: ").append(bean.getMarkPrice()) + "");
        viewHolder.collectCashTv.setText(new StringBuilder("收取现金: ").append(bean.getOrderAmount()) + "");
        viewHolder.addressTv.setText(new StringBuilder("").append(bean.getAddress()) + "");
        viewHolder.percentTv.setText(bean.getDiscount()  + "折");
        GearImageLoad.getSingleton(getContext()).load( bean.getImage(), viewHolder.avatarIv);
        viewHolder.statusTv.setText(bean.getOrderState());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.order_num_tv)
        TextView orderNumTv;
        @Bind(R.id.date_tv)
        TextView dateTv;
        @Bind(R.id.avatar_iv)
        ImageView avatarIv;
        @Bind(R.id.percent_tv)
        TextView percentTv;
        @Bind(R.id.user_name_tv)
        TextView userNameTv;
        @Bind(R.id.goods_name_tv)
        TextView goodsNameTv;
        @Bind(R.id.ccj_code_tv)
        TextView ccjCodeTv;
        @Bind(R.id.price_sale_tv)
        TextView priceSaleTv;
        @Bind(R.id.collect_cash_tv)
        TextView collectCashTv;
        @Bind(R.id.address_tv)
        TextView addressTv;
        @Bind(R.id.status_tv)
        TextView statusTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
