package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.MessageBean;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by sue on 2017/1/3.
 */
public class MessageAdapter extends ArrayListAdapter<MessageBean> {
    public MessageAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MessageBean bean = getItem(position);
        viewHolder.contentTv.setText(bean.getSmContent());
        viewHolder.dateTv.setText(bean.getSmAddtime());
        viewHolder.statusNameTv.setText(bean.getRead() ==  0 ? "未读": "已读");
        viewHolder.typeNameTv.setText(bean.getSmtCode());


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.date_tv)
        TextView dateTv;
        @Bind(R.id.type_name_tv)
        TextView typeNameTv;
        @Bind(R.id.status_name_tv)
        TextView statusNameTv;
        @Bind(R.id.content_tv)
        TextView contentTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
