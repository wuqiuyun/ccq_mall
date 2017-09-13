package clerk.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.bean.PerformanceChildMemberBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import per.sue.gear2.adapter.ArrayListAdapter;

/**
 * Created by sue on 2016/11/21.
 */
public class PerformanceChildAdapter extends ArrayListAdapter<PerformanceChildMemberBean> {


    public PerformanceChildAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_performance_child, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PerformanceChildMemberBean bean = getItem(position);
        viewHolder.dateTv.setText(bean.getDate() + "");
        viewHolder.countMemberTv.setText(bean.getSumMember() + "");
        viewHolder.countVipTv.setText(bean.getSumVipMember()  + "");
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.datet_tv)
        TextView dateTv;
        @Bind(R.id.count_member_tv)
        TextView countMemberTv;
        @Bind(R.id.count_vip_tv)
        TextView countVipTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
