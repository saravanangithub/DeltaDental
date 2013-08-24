package com.deltadental.android.adapters;


import com.deltadental.android.R;
 
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
 
public class MenuListAdapter extends BaseAdapter {
 
    // Declare Variables 
    Context context;
    String[] mTitle;
   // String[] mSubTitle;
    int[] mIcon;
   
    LayoutInflater inflater;
    PopupMenu popup;
    ImageView settingsIcon;
    public MenuListAdapter(Context context, String[] title,int[] icon
            ) {
        this.context = context;
        this.mTitle = title;
       // this.mSubTitle = subtitle;
       this.mIcon = icon;
       
      
	    
    }
 
    @Override
    public int getCount() {
        return mTitle.length;
    }
 
    @Override
    public Object getItem(int position) {
        return mTitle[position];
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView txtTitle;
        TextView txtSubTitle;
        ImageView imgIcon;
 
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.drawer_list_item, parent,
                false);
 
        // Locate the TextViews in drawer_list_item.xml
        txtTitle = (TextView) itemView.findViewById(R.id.title);
       // txtSubTitle = (TextView) itemView.findViewById(R.id.subtitle);
 
        // Locate the ImageView in drawer_list_item.xml
        imgIcon = (ImageView) itemView.findViewById(R.id.icon);
        
        if(position==1)
        {
        	settingsIcon = (ImageView) itemView.findViewById(R.id.icon1);
        	settingsIcon.setImageResource(R.drawable.settings);
        	final PopupMenu popup = new PopupMenu(context, settingsIcon);
		    MenuInflater inflater = popup.getMenuInflater();
		    inflater.inflate(R.menu.distance, popup.getMenu());
		   final MenuItem menuItemOneKm=popup.getMenu().findItem(R.id.onekm_distance);
		   final MenuItem menuItemTwoKm=popup.getMenu().findItem(R.id.twokm_distance);
		   final  SharedPreferences sharedPreference =context.getSharedPreferences("MyPrefsFile", 0);
		    menuItemOneKm.setCheckable(sharedPreference.getBoolean("oneKmDistance", true));
		    menuItemOneKm.setChecked(sharedPreference.getBoolean("oneKmDistance", true));
		    menuItemTwoKm.setCheckable(sharedPreference.getBoolean("twoKmDistance", true));
		    menuItemTwoKm.setChecked(sharedPreference.getBoolean("twoKmDistance", true)); 
        	settingsIcon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
				    popup.show();   
					
				    popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {  
						
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							// TODO Auto-generated method stub
							
							SharedPreferences.Editor editor=sharedPreference.edit();
							if(!item.isCheckable())
							{
								item.setCheckable(true);
								item.setChecked(true);							
							}
							
							switch(item.getItemId())
							{
							case R.id.onekm_distance:
								menuItemTwoKm.setCheckable(false);
								menuItemTwoKm.setChecked(false);
								
								
								break;
							case R.id.twokm_distance:
								menuItemOneKm.setCheckable(false);
								menuItemOneKm.setChecked(false);
								
								break; 
							}
							editor.putBoolean("oneKmDistance", menuItemOneKm.isChecked());
							editor.putBoolean("twoKmDistance", menuItemTwoKm.isChecked());							
							editor.commit(); 
							//item.setChecked(false);
							return true;  
						}
					});
				}
			});
        }
 
        // Set the results into TextViews
        txtTitle.setText(mTitle[position]);
       // txtSubTitle.setText(mSubTitle[position]);
 
        // Set the results into ImageView
        imgIcon.setImageResource(mIcon[position]); 
  
        return itemView;
    }
 
}
