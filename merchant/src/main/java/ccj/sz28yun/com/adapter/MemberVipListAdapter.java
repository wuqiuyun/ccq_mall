package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.MemberVipListBean;
import per.sue.gear2.adapter.ArrayListAdapter;

/**
 * Created by meihuali on 2017/6/13.
 */

public class MemberVipListAdapter extends ArrayListAdapter<MemberVipListBean> {
    public MemberVipListAdapter(Context context) {
        super(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        MemberVipListBean bean = getItem(position);
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_member_vip, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.nameTv.setText(bean.getMemberName());
        viewHolder.timeTv.setText(bean.getMemberTime());
        viewHolder.typeTv.setText(bean.getVip());
        viewHolder.countTv.setText(bean.getOrderNum());
        return convertView;
    }



    static class ViewHolder {
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.time_tv)
        TextView timeTv;
        @Bind(R.id.type_tv)
        TextView typeTv;
        @Bind(R.id.count_tv)
        TextView countTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
