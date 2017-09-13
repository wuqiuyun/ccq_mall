package clerk.sz28yun.com.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.bean.PerformanceDayBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by sue on 2016/11/18.
 */
public class PerformanceDayAdapter extends ArrayListAdapter<PerformanceDayBean> {


    public PerformanceDayAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            DayViewHolder viewHolder;
            if (null == convertView) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_performance_day, null);
                viewHolder = new DayViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (DayViewHolder) convertView.getTag();
            }

            PerformanceDayBean bean = (PerformanceDayBean) getItem(position);
            viewHolder.nameTv.setText(formatMemberName(bean.getMemberName()));
            viewHolder.orderTv.setText(formatOrderNum(bean.getOrderSn()));
            viewHolder.dateTv.setText(DateUtils.getDate(bean.getDatetime()*1000, DateStyle.YYYY_MM_DD_EN));
            viewHolder.valueTv.setText(new StringBuilder("￥").append(DoubleUtil.formatPrice(bean.getValue())));


        return convertView;
    }

    private String formatMemberName(String memberName) {
        if (TextUtils.isEmpty(memberName)) {
            memberName = "";
        } else if (memberName.length() > 7) {
            memberName = memberName.substring(memberName.length() - 4, memberName.length());
            memberName = new StringBuilder("***").append(memberName).toString();
        }
        return memberName;
    }

    private String formatOrderNum(String orderNum) {
        if (TextUtils.isEmpty(orderNum)) {
            orderNum = "";
        } else if (orderNum.length() > 12) {
            String orderNumStart = orderNum.substring(0, 6);
            String orderNumEnd = orderNum.substring(orderNum.length() - 6, orderNum.length());
            orderNum = new StringBuilder(orderNumStart).append("***").append(orderNumEnd).toString();
        }
        return orderNum;
    }

    static class DayViewHolder {
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.order_tv)
        TextView orderTv;
        @Bind(R.id.date_tv)
        TextView dateTv;
        @Bind(R.id.value_tv)
        TextView valueTv;

        DayViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
