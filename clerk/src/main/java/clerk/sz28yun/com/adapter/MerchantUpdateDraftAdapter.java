package clerk.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.bean.MerchantParams;

import butterknife.Bind;
import butterknife.ButterKnife;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by sue on 2016/11/25.
 */
public class MerchantUpdateDraftAdapter extends ArrayListAdapter<MerchantParams> {


    public MerchantUpdateDraftAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_merchant_update_draft, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MerchantParams bean = getItem(position);
        viewHolder.nameTv.setText(bean.merchantName);
        viewHolder.dateTv.setText(DateUtils.getDate(bean.localDate, DateStyle.YYYY_MM_DD_HH_MM_SS_CN));
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.date_tv)
        TextView dateTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
