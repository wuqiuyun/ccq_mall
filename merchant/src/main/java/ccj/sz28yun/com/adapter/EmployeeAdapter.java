package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.EmployeeBean;
import ccj.sz28yun.com.bean.MerchantChainBean;
import per.sue.gear2.adapter.ArrayListAdapter;

/**
 * Created by sue on 2017/1/3.
 */
public class EmployeeAdapter extends ArrayListAdapter<EmployeeBean> {

    OnEmployeeAdapterListener onEmployeeAdapterListener;

    public EmployeeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_employee, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EmployeeBean bean = getItem(position);
        viewHolder.nameTv.setText(bean.getName());
        viewHolder.positionTv.setText(bean.getIdentity());
        viewHolder.accountTv.setText(bean.getSellerName());
        viewHolder.mobileTv.setText(bean.getPhone());
        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onEmployeeAdapterListener) {
                    onEmployeeAdapterListener.onDelete(bean, position);
                }
            }
        });
        viewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onEmployeeAdapterListener) {
                    onEmployeeAdapterListener.onEdit(bean, position);
                }
            }
        });

        return convertView;
    }

    public interface OnEmployeeAdapterListener {

        void onDelete(EmployeeBean bean, int position);
        void onEdit(EmployeeBean bean, int position);
    }


    public void setOnEmployeeAdapterListener(OnEmployeeAdapterListener onEmployeeAdapterListener) {
        this.onEmployeeAdapterListener = onEmployeeAdapterListener;
    }

    static class ViewHolder {
        @Bind(R.id.position_tv)
        TextView positionTv;
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.account_tv)
        TextView accountTv;
        @Bind(R.id.mobile_tv)
        TextView mobileTv;
        @Bind(R.id.delete_btn)
        TextView deleteBtn;
        @Bind(R.id.edit_btn)
        TextView editBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
