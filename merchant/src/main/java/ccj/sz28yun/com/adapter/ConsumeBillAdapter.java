package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.ConsumeBillBean;
import ccj.sz28yun.com.bean.HomeMenuBean;
import per.sue.gear2.adapter.ArrayListAdapter;

/**
 * Created by sue on 2016/12/4.
 */
public class ConsumeBillAdapter extends ArrayListAdapter<ConsumeBillBean> {

    ConsumeBillAdapterListener consumeBillAdapterListener;

    public ConsumeBillAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final  int position, View convertView, ViewGroup parent) {
        final  ConsumeBillBean bean = getItem(position);
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_consume_bill, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.consumeTv.setText(new StringBuilder(bean.getGoodsAmount() + "").append("元").toString());
        viewHolder.paymentTv.setText(new StringBuilder(bean.getOrderAmount() + "").append("元").toString());
        viewHolder.orderNumTv.setText(new StringBuilder(bean.getOrderSn() + "").toString());
        viewHolder.mobileTailTv.setText(new StringBuilder(bean.getMemberMobile() + "").toString());
        viewHolder.checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != consumeBillAdapterListener){
                    consumeBillAdapterListener.onCheck(bean, position);
                }
            }
        });
        //未付款  验证 已验证 3种状态
        viewHolder.checkBtn.setText(bean.getOrderState());
        if("验证".equals(bean.getOrderState())){
            viewHolder.checkBtn.setEnabled(true);
            viewHolder.checkBtn.setBackgroundResource(R.drawable.selector_squ_r4_b_transparency_primary);

        }else{
            viewHolder.checkBtn.setEnabled(false);
            viewHolder.checkBtn.setBackgroundResource(0);
        }

        return convertView;
    }

    public interface  ConsumeBillAdapterListener{

        void onCheck(ConsumeBillBean bean, int position);
    }

    public void setConsumeBillAdapterListener(ConsumeBillAdapterListener consumeBillAdapterListener) {
        this.consumeBillAdapterListener = consumeBillAdapterListener;
    }

    static class ViewHolder {
        @Bind(R.id.order_num_tv)
        TextView orderNumTv;
        @Bind(R.id.mobile_tail_tv)
        TextView mobileTailTv;
        @Bind(R.id.payment_tv)
        TextView paymentTv;
        @Bind(R.id.consume_tv)
        TextView consumeTv;
        @Bind(R.id.check_btn)
        TextView checkBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
