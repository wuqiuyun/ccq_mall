package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.FinanceDayBean;
import per.sue.gear2.adapter.ArrayListAdapter;

/**
 * Created by sue on 2016/12/19.
 */
public class FinanceTypeDayAdapter extends ArrayListAdapter<FinanceDayBean> {

    public FinanceTypeDayAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_finance_type_day, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FinanceDayBean bean = getItem(position);

        viewHolder.dateTv.setText(bean.getDatetime());
        viewHolder.fundsTv.setText(new StringBuilder(bean.getValue() + "").append("元"));
        viewHolder.nameTv.setText(bean.getUserName());
        viewHolder.orderNumTv.setText("订单号: " + bean.getOrderSn());

        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.order_num_tv)
        TextView orderNumTv;
        @Bind(R.id.date_tv)
        TextView dateTv;
        @Bind(R.id.funds_tv)
        TextView fundsTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
