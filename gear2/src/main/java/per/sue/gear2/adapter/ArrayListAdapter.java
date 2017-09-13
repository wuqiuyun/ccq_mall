/*
 * Copyright (C) 2009 Teleca Poland Sp. z o.o. <android@teleca.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package per.sue.gear2.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Nice wrapper-abstraction around ArrayList
 * 
 * @author Lukasz Wisniewski
 *
 * @param <T>
 */
public abstract class ArrayListAdapter<T> extends BaseAdapter{
	protected ArrayList<T> list;
	protected Context context;
	protected ListView listView;
	public ArrayListAdapter(Context context){
		this.context = context;
	}

	@Override
	public int getCount() {
		if(this.list != null)
			return this.list.size();
		else
			return 0;
	}

	@Override
	public T getItem(int position) {
		if(position >= this.list.size()||position<0)
			return null;
		return this.list == null ? null : this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	abstract public View getView(int position, View convertView, ViewGroup parent);
	
	public void setList(ArrayList<T> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public void addList(ArrayList<T> list){
		if(this.list == null ){
			this.list = list;
		}else{
			this.list.addAll(list);
		}
		notifyDataSetChanged();
	}

	public void addList(int postion, ArrayList<T> list){
		if(this.list == null ){
			this.list = list;
		}else{
			this.list.addAll(postion, list);
		}
		notifyDataSetChanged();
	}




	public ArrayList<T> getList(){
		return this.list ;
	}
	
	public void setList(T[] list){
		ArrayList<T> arrayList = new ArrayList<T>(list.length);  
		for (T t : list) {  
			arrayList.add(t);  
		}  
		setList(arrayList);
	}
	
	public ListView getListView(){
		return listView;
	}
	
	public void setListView(ListView listView){
		listView = listView;
	}
	
	
	public Context getContext(){
		return context;
	}

	public  <T extends View> T findViewById(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

}
