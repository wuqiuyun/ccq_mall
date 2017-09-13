package clerk.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.bean.PerformanceMonBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by sue on 2016/11/18.
 */
public class PerformanceMonAdapter extends ArrayListAdapter<PerformanceMonBean> {

    public PerformanceMonAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MonViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_performance_mon, null);
            viewHolder = new MonViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MonViewHolder) convertView.getTag();
        }

        PerformanceMonBean bean = (PerformanceMonBean) getItem(position);
        viewHolder.dateTv.setText(DateUtils.getDate(bean.getAddtime()*1000, DateStyle.YYYY_MM_DD_EN));
        viewHolder.valueTv.setText((DoubleUtil.formatPrice(bean.getValue())) + "元");
//        viewHolder.detTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        return convertView;
    }

    static class MonViewHolder {
        @Bind(R.id.date_tv)
        TextView dateTv;
        @Bind(R.id.value_tv)
        TextView valueTv;
        @Bind(R.id.det_tv)
        TextView detTv;

        MonViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
