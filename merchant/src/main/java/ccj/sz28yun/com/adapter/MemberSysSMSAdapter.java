package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.MemberSysSMSBean;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by sue on 2016/12/15.
 */
public class MemberSysSMSAdapter extends ArrayListAdapter<MemberSysSMSBean> {
    public MemberSysSMSAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MemberSysSMSBean bean = getItem(position);
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_member_sys_sms, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(!TextUtils.isEmpty(bean.getDate())){
            long date = Long.valueOf(bean.getDate());
            viewHolder.dateTv.setText(DateUtils.getDate(date, DateStyle.YYYY_MM_DD_HH_MM_SS));
        }else{
            viewHolder.dateTv.setText("无");
        }

        viewHolder.countTv.setText(new StringBuilder(bean.getCount() + "") .append("条") );
        viewHolder.paymentTv.setText(new StringBuilder(bean.getPayment() + "") .append("元") );
        viewHolder.signtrueTv.setText(bean.getSigntrue());

        if (0 == bean.getStatus()){//审核中:0 审核通过待处理:1 未通过审核:2 发送成功:3 发送失败:4
            viewHolder.statusTv.setText("审核中");
        }else if (1 == bean.getStatus()){
            viewHolder.statusTv.setText("审核通过待处理");
        }else if (2 == bean.getStatus()){
            viewHolder.statusTv.setText("未通过审核");
        }else if (3 == bean.getStatus()){
            viewHolder.statusTv.setText("发送成功");
        }else if (4 == bean.getStatus()){
            viewHolder.statusTv.setText("发送失败");
        }

        viewHolder.typeTv.setText(bean.getType());


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.date_tv)
        TextView dateTv;
        @Bind(R.id.signtrue_tv)
        TextView signtrueTv;
        @Bind(R.id.type_tv)
        TextView typeTv;
        @Bind(R.id.count_tv)
        TextView countTv;
        @Bind(R.id.payment_tv)
        TextView paymentTv;
        @Bind(R.id.status_tv)
        TextView statusTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
