package per.sue.gear2.pop;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelChangedListener;
import antistatic.spinnerwheel.WheelHorizontalView;
import antistatic.spinnerwheel.WheelVerticalView;
import antistatic.spinnerwheel.adapters.NumericWheelAdapter;
import per.sue.gear2.R;


public class SelectDateWheelPop extends AbsWheelPop{
	
	private WheelVerticalView startY,startMon,startD;
	private WheelHorizontalView startH,startMin;
	private Context context;
	private View layout;
	
	private onChangeDateListenter dateListenter;
	private String startDate;
	public static final int DATE_TYPE_DETAIL=0;
	public static final int DATE_TYPE_ONLY_YMD=1;
	public static final int DATE_TYPE_ONLY_HM=2;
	private int mDateType=0;
	
	
	private Calendar calendar = Calendar.getInstance();
	private static int START_YEAR = 1900, END_YEAR = 2100;

	@Override
	protected View getContentView() {
		// TODO Auto-generated method stub
		return layout;
	}
	
	@Override
	public int getWidth(View parent) {
		// TODO Auto-generated method stub
		return LayoutParams.WRAP_CONTENT;
	}
	
	
	public SelectDateWheelPop(Context context,String startDate) {
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = LayoutInflater.from(context);
		layout = inflater.inflate(R.layout.pop_selectdate_wheel, null);
		this.context=context;
		this.startDate=startDate;
		init();
	}
	
	public SelectDateWheelPop(Context context,String startDate,int type) {
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = LayoutInflater.from(context);
		layout = inflater.inflate(R.layout.pop_selectdate_wheel, null);
		this.context=context;
		this.startDate=startDate;
		this.mDateType=type;
		init();
	}
	
	private void init()
	{
		intView();
		initData();
		bindView();
	}
	
	private void intView() {
		startY=(WheelVerticalView)layout.findViewById(R.id.wheel_startdate_year);
		startMon=(WheelVerticalView)layout.findViewById(R.id.wheel_startdate_month);
		startD=(WheelVerticalView)layout.findViewById(R.id.wheel_startdate_day);
		startH=(WheelHorizontalView)layout.findViewById(R.id.wheel_startdate_hour);
		startMin=(WheelHorizontalView)layout.findViewById(R.id.wheel_startdate_minute);
		layout.findViewById(R.id.ok_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// ;TODO Auto-generated method stub
				if(dateListenter!=null)
					dateListenter.onChangeDate(startDate);
			
				dismiss();
			}
		});
	}
	
	public void setShowType(int type)
	{
		mDateType=type;
		if(mDateType==DATE_TYPE_ONLY_YMD)
		{
			startH.setVisibility(View.GONE);
			startMin.setVisibility(View.GONE);
		}
		
		if(mDateType==DATE_TYPE_ONLY_HM) {
			startY.setVisibility(View.GONE);
			startMon.setVisibility(View.GONE);
			startD.setVisibility(View.GONE);
		}
	}
	
	
	
	
	private void initData()
	{
		
	}
	
	private void bindView()
	{
		bindWheelVerticalView(startDate, startY, startMon, startD, startH, startMin);
		
	}
	

	
	public void setonChangeDateListenter(onChangeDateListenter l) {
		this.dateListenter = l;
	}


	private void bindWheelVerticalView(final String str,final WheelVerticalView wv_year,final WheelVerticalView  wv_month,final WheelVerticalView  wv_day,final WheelHorizontalView wv_hours,final WheelHorizontalView wv_mins)
	{
		
		setInitDate(str);
		calendar.get(Calendar.HOUR_OF_DAY );
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hours=calendar.get(Calendar.HOUR);
		int minute=calendar.get(Calendar.MINUTE);
		
		// ��Ӵ�С���·ݲ�����ת��Ϊlist,����֮����ж�
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };
		
		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		// ��
		NumericWheelAdapter adapter=new NumericWheelAdapter(context, START_YEAR, END_YEAR);
		
		wv_year.setViewAdapter(adapter);// ����"��"����ʾ����
		wv_year.setCyclic(true);// ��ѭ������
	//	wv_year.set// �������		
		wv_year.setCurrentItem(year - START_YEAR);// ��ʼ��ʱ��ʾ������
	
		//wv_year.set

		// ��	
		wv_month.setViewAdapter(new NumericWheelAdapter(context,1, 12));
		wv_month.setCyclic(true);
		//wv_month.setLabel("��");
		wv_month.setCurrentItem(month);

		// ��	
		wv_day.setCyclic(true);
	  // �жϴ�С�¼��Ƿ�����,����ȷ��"��"������
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 30));
		} else {
			// ����
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 29));
			else
				wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 28));
		}
		//wv_day.setLabel("��");
		wv_day.setCurrentItem(day -1);
		
		
		
		// ʱ		
		final  NumericWheelAdapter wheelAdapterHour=new NumericWheelAdapter(context,0, 23, "%02d");
		wheelAdapterHour.setItemResource(R.layout.wheel_text_centered);
		wheelAdapterHour.setItemTextResource(R.id.text);
		wv_hours.setViewAdapter(wheelAdapterHour);
		wv_hours.setCyclic(true);
		//wv_hours.setLabel("ʱ");
		wv_hours.setCurrentItem(hours);
		
		
		
		// ��
		final NumericWheelAdapter wheelAdapterMin=new NumericWheelAdapter(context,0, 59, "%02d");
		wheelAdapterMin.setItemResource(R.layout.wheel_text_centered_dark_back);
		wheelAdapterMin.setItemTextResource(R.id.text);
		wv_mins.setViewAdapter(wheelAdapterMin);
		wv_mins.setCyclic(true);
		//wv_mins.setLabel("��");
		wv_mins.setCurrentItem(minute);
		
		
		// ���"��"����
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// �жϴ�С�¼��Ƿ�����,����ȷ��"��"������
				if (list_big
						.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setViewAdapter(new NumericWheelAdapter(context, 1, 31));
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 29));
					else
						wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 28));
				}
				setTextView(str, wv_year, wv_month, wv_day, wv_hours, wv_mins);
			}
		};
		// ���"��"����
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// �жϴ�С�¼��Ƿ�����,����ȷ��"��"������
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
							.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
						wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 29));
					else
						wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 28));
				}
				
				setTextView(str, wv_year, wv_month, wv_day, wv_hours, wv_mins);
			}
		};
		
		OnWheelChangedListener wheelListener_other=new OnWheelChangedListener() {
			
			@Override
			public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				setTextView(str, wv_year, wv_month, wv_day, wv_hours, wv_mins);
			}
		};
		
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);
		wv_day.addChangingListener(wheelListener_other);
		wv_hours.addChangingListener(wheelListener_other);
		wv_mins.addChangingListener(wheelListener_other);
		
	}
	
	private void  setInitDate(String startDate)
	{
		if(startDate!=null&&!"".equals(startDate))
			{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			ParsePosition pos = new ParsePosition(0);
		    Date	mDate   = formatter.parse(startDate, pos);
		   if(mDate!=null)
		    calendar.setTime(mDate);		
		}
	}
	
	private void setTextView(final String str,final WheelVerticalView wv_year,final WheelVerticalView  wv_month,final WheelVerticalView  wv_day,WheelHorizontalView wv_hours,WheelHorizontalView wv_mins)
	{
		
			startDate=getTime(wv_year, wv_month, wv_day, wv_hours, wv_mins);
		
	}

	public String getTime(final WheelVerticalView wv_year,final WheelVerticalView  wv_month,final WheelVerticalView  wv_day,WheelHorizontalView wv_hours,WheelHorizontalView wv_mins) {
		StringBuffer sb = new StringBuffer();
	
		String month=wv_month.getCurrentItem() + 1+"";
		String day=wv_day.getCurrentItem() + 1+"";
		String hour=wv_hours.getCurrentItem() +"";
		String min=wv_mins.getCurrentItem() +"";
		if(month.length()==1)
			month="0"+month;
		
		if(day.length()==1)
			day="0"+day;
		if(hour.length()==1)
			hour="0"+hour;
		if(min.length()==1)
			min="0"+min;
		
		
		sb.append((wv_year.getCurrentItem() + START_YEAR)).append("-")
				.append(month).append("-")
				.append(day).append(" ")
				.append(hour).append(":")
				.append(min);
		return sb.toString();
	}
	public interface onChangeDateListenter {
		void onChangeDate(String startDate);

	}



}
