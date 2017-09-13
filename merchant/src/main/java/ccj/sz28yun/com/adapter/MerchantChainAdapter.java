package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.MerchantChainBean;
import per.sue.gear2.adapter.ArrayListAdapter;

/**
 * Created by sue on 2017/1/3.
 */
public class MerchantChainAdapter extends ArrayListAdapter<MerchantChainBean> {

    OnMerchantChainAdapterListener onMerchantChainAdapterListener;

    public MerchantChainAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_merchant_chain, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MerchantChainBean bean = getItem(position);
        viewHolder.nameTv.setText(bean.getStoreName());
        viewHolder.addressTv.setText(bean.getAddress());
        viewHolder.personNameTv.setText(bean.getMemberName());
        viewHolder.personMobileTv.setText(bean.getContactTelephone());
        viewHolder.serviceMobileTv.setText(bean.getContactTelephone());
        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onMerchantChainAdapterListener){
                    onMerchantChainAdapterListener.onDelete(bean, position);
                }
            }
        });

        return convertView;
    }

    public interface OnMerchantChainAdapterListener{

        void onDelete(MerchantChainBean bean, int position);
    }

    public void setOnMerchantChainAdapterListener(OnMerchantChainAdapterListener onMerchantChainAdapterListener) {
        this.onMerchantChainAdapterListener = onMerchantChainAdapterListener;
    }

    static class ViewHolder {
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.person_name_tv)
        TextView personNameTv;
        @Bind(R.id.person_mobile_tv)
        TextView personMobileTv;
        @Bind(R.id.address_tv)
        TextView addressTv;
        @Bind(R.id.service_mobile_tv)
        TextView serviceMobileTv;
        @Bind(R.id.delete_btn)
        TextView deleteBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
