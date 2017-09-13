package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ccj.sz28yun.com.R;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.widget.SquareImageView;

/**
 * Created by langgu on 16/6/2.
 */
public class ImagePublicAdapter extends ArrayListAdapter<Object> {


    private int imagesMaxCount = -1;
    private OnClickImageListener mImageOnClickListener;

    public ImagePublicAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(null == convertView){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_publish, null);
        }

        SquareImageView avatarIV = findViewById(convertView, R.id.iv_avatar);
        SquareImageView addIV = findViewById(convertView, R.id.iv_add);
        avatarIV.setTag(position);
        TextView delectTV = findViewById(convertView, R.id.delete_tv);

        Object target = getItem(position);
        if(target instanceof Integer ){
            addIV.setImageResource(R.mipmap.images);
            addIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != mImageOnClickListener) {
                        mImageOnClickListener.onClickAddImage(view);
                    }
                }
            });
            addIV.setVisibility(View.VISIBLE);
            avatarIV.setVisibility(View.GONE);
            delectTV.setVisibility(View.GONE);
        }else{
            avatarIV.setVisibility(View.VISIBLE);
            delectTV.setVisibility(View.VISIBLE);
            addIV.setVisibility(View.GONE);
            avatarIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(null != mImageOnClickListener){
                        int position = (int)view.getTag();
                        mImageOnClickListener.onClickDeleteImage(view, position);
                    }
                }
            });
            GearImageLoad.getSingleton(getContext()).load(target, avatarIV, R.drawable.gear_image_default);
        }

        if((imagesMaxCount ) == position){
            convertView.setVisibility(View.GONE);
        }else{
            convertView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }


    public void setImagesMaxCount(int imagesMaxCount) {
        this.imagesMaxCount = imagesMaxCount;
    }

    public void setImageOnClickListener(OnClickImageListener imageOnClickListener) {
        this.mImageOnClickListener = imageOnClickListener;
    }

    public interface  OnClickImageListener{
        void onClickAddImage(View v);
        void onClickDeleteImage(View v, int position);
    }


}
