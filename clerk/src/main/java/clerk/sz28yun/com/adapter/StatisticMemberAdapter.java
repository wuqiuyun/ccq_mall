package clerk.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.bean.MemberStatisticBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import per.sue.gear2.adapter.ArrayListAdapter;

/**
 * Created by sue on 2016/11/21.
 */
public class StatisticMemberAdapter extends ArrayListAdapter<MemberStatisticBean> {

    public StatisticMemberAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_statistic, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MemberStatisticBean bean = getItem(position);
        viewHolder.nameTv.setText(bean.getMemberName());
        viewHolder.countRegistTv.setText(bean.getInviteMemberCount() + "");
        viewHolder.countMemberTv.setText(bean.getInviteUnvipCount() + "");
        viewHolder.countVipTv.setText(bean.getSumInviteVipCount()  + "");

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.count_regist_tv)
        TextView countRegistTv;
        @Bind(R.id.count_member_tv)
        TextView countMemberTv;
        @Bind(R.id.count_vip_tv)
        TextView countVipTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
