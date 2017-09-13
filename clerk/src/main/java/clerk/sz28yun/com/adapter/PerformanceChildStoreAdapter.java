package clerk.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import clerk.sz28yun.com.R;
import clerk.sz28yun.com.bean.PerformanceChildMemberBean;
import clerk.sz28yun.com.bean.PerformanceChildMerchantBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by sue on 2016/11/17.
 */
public class PerformanceChildStoreAdapter extends ArrayListAdapter<PerformanceChildMemberBean> {

    private OnChildStoreDetailListener onChildStoreDetailListener;

    public PerformanceChildStoreAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_performance_child_store, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PerformanceChildMemberBean bean = getItem(position);
        viewHolder.tvData.setText(bean.getDate());
        viewHolder.tvTijiaoStore.setText(bean.getSum_store_joinin());
        viewHolder.tvRuzhuStore.setText(bean.getSum_store());
        viewHolder.tvLookStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onChildStoreDetailListener) {
                    onChildStoreDetailListener.onClickDetail(bean);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_data)
        TextView tvData;
        @Bind(R.id.tv_tijiao_store)
        TextView tvTijiaoStore;
        @Bind(R.id.tv_ruzhu_store)
        TextView tvRuzhuStore;
        @Bind(R.id.tv_look_store)
        TextView tvLookStore;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setOnChildStoreDetailListener(OnChildStoreDetailListener onChildStoreDetailListener) {
        this.onChildStoreDetailListener = onChildStoreDetailListener;
    }

    public interface OnChildStoreDetailListener {

        void onClickDetail(PerformanceChildMemberBean bean);
    }
}
