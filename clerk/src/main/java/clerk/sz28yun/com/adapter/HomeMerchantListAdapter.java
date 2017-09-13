package clerk.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.bean.HomeMerchantBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import per.sue.gear2.adapter.ArrayListAdapter;

/**
 * Created by sue on 2016/11/16.
 */
public class HomeMerchantListAdapter extends ArrayListAdapter<HomeMerchantBean> {
    public HomeMerchantListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        HomeMerchantBean bean = getItem(position);
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_home_merchant_new, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.nameTv.setText(bean.getStoreName());
        viewHolder.statusTv.setText(bean.getStatusName());
        return convertView;
    }



    static class ViewHolder {
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.status_tv)
        TextView statusTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
