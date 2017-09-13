package per.sue.gear2.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelChangedListener;
import antistatic.spinnerwheel.WheelVerticalView;
import antistatic.spinnerwheel.adapters.ArrayWheelAdapter;
import per.sue.gear2.R;


/**
 * 
 * @author msi
 *
 * @param <T>
 */
public class ArrayWheelPop<T> extends AbsWheelPop{

	private WheelVerticalView proShortWV;
	private Button okBtn;
	
	private Context context;
	private View layout;
	private onChangeSelectedListenter<T> proListenter;	
	private  int selectPosition=0;
    private T items[];

	public ArrayWheelPop(Context context, T items[]) {
		this.context = context;
		this.items=items;
		LayoutInflater inflater = LayoutInflater.from(context);
		layout = inflater.inflate(R.layout.pop_proshort_wheel_spinner, null);
		proShortWV = (WheelVerticalView) layout
				.findViewById(R.id.wheel_proshort);
		okBtn=(Button)layout.findViewById(R.id.ok_btn);
		bindView();
	}
	private void bindView() {

		proShortWV.setViewAdapter(getAdapter(items));
		proShortWV.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(AbstractWheel arg0, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				selectPosition=newValue;

				if (proListenter != null)
					proListenter.onChangeSelected(selectPosition,getCurrentString());
			}
		});

	}
	
	public void setOnOkClickListener(View.OnClickListener l)
	{
		if(l!=null)
		{
			okBtn.setVisibility(View.VISIBLE);
			okBtn.setOnClickListener(l);
		}
	}


	private ArrayWheelAdapter<T> getAdapter( T items[]) {
		ArrayWheelAdapter<T> adapter = new ArrayWheelAdapter<T>(
				context, items);

		return adapter;
	}

	public T getCurrentString()
	{
		return  items[selectPosition];
	}
	

	public void setOnChangeListenter(onChangeSelectedListenter<T> l) {
		this.proListenter = l;
	}

	
	public interface onChangeSelectedListenter<T> {
		void onChangeSelected(int position, T item);

	

	}


	@Override
	protected View getContentView() {
		// TODO Auto-generated method stub
		return layout;
	}

}
