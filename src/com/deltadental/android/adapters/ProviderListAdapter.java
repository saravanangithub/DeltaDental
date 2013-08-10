package com.deltadental.android.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deltadental.android.R;
import com.parse.ParseObject;

public class ProviderListAdapter extends BaseAdapter {

	
		 
	    // Declare Variables 
	    Context context;
	    List<ParseObject> providerList;
	   // String[] mSubTitle;
	 
	    LayoutInflater inflater;
	 
	    public ProviderListAdapter(Context context, List<ParseObject> providerList
	            ) {
	        this.context = context; 
	        this.providerList = providerList;
	       // this.mSubTitle = subtitle;
	        //this.mIcon = icon;
	    }
	 
	    @Override
	    public int getCount() {
	        return providerList.size();
	    }
	 
	    @Override
	    public Object getItem(int position) {
	        return providerList.get(position);
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    public View getView(int position, View convertView, ViewGroup parent) {
	        // Declare Variables
	        TextView txtVwFirstName;
	        TextView txtVwLastName;
	        TextView txtVwPhone;
	        TextView txtVwFax;
	        TextView txtVwSpeciality;
	        TextView txtVwAddress;
	        TextView txtVwZipCode;
	        ImageView imgIcon;
	 
	        inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View itemView = inflater.inflate(R.layout.providers_list_item, parent,
	                false);
	 
	        // Locate the TextViews in drawer_list_item.xml
	        txtVwFirstName = (TextView) itemView.findViewById(R.id.txtVwfirtName);
	        txtVwLastName= (TextView) itemView.findViewById(R.id.txtVwLastName);
	        txtVwPhone = (TextView) itemView.findViewById(R.id.txtVwPhone);
	        txtVwFax = (TextView) itemView.findViewById(R.id.txtVwFax);
	        txtVwSpeciality = (TextView) itemView.findViewById(R.id.txtVwSpeciality);
	        txtVwAddress = (TextView) itemView.findViewById(R.id.txtVwAddress);
	        txtVwZipCode = (TextView) itemView.findViewById(R.id.txtVwZipCode);
	        
	        ParseObject parseObject=(ParseObject)getItem(position);
	        
	        txtVwFirstName.setText(parseObject.getString("FirstName"));
	        txtVwLastName.setText(parseObject.getString("LastName"));
	        txtVwPhone.setText(parseObject.getString("Phone"));
	        txtVwFax.setText(parseObject.getString("Fax"));
	        txtVwSpeciality.setText(parseObject.getString("Speciality"));
	        txtVwAddress.setText(parseObject.getString("Address"));
	        txtVwZipCode.setText(parseObject.getString("ZipCode"));  	       
	 
	        return itemView;
	    }
}
