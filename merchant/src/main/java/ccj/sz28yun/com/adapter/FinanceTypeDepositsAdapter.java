package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.FinanceDepositsBean;
import per.sue.gear2.adapter.ArrayListAdapter;

/**
 * Created by sue on 2016/12/19.
 */
public class FinanceTypeDepositsAdapter extends ArrayListAdapter<FinanceDepositsBean> {

    public FinanceTypeDepositsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_finance_type_deposits, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FinanceDepositsBean bean = getItem(position);

        viewHolder.dateTv.setText(bean.getTime());
        viewHolder.fundsTv.setText(new StringBuilder(bean.getAmount() + "").append("元"));
        viewHolder.statusTv.setText(bean.getStatusName());
        viewHolder.bankNumTv.setText(bean.getCardAccount());

        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.bank_num_tv)
        TextView bankNumTv;
        @Bind(R.id.date_tv)
        TextView dateTv;
        @Bind(R.id.status_tv)
        TextView statusTv;
        @Bind(R.id.funds_tv)
        TextView fundsTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
