package com.deltadental.android.views;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF; 
import android.view.View;
import android.widget.Toast;

public class PieChart extends View {
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private float[] value_degree; 
	private Context context;
	float temp = 0;

	public PieChart(Context context, float[] values) {
		super(context);
		this.context=context;
		   
		value_degree = new float[values.length];
		for (int i = 0; i < values.length; i++) {
			value_degree[i] = values[i];
		}
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		 
		RectF rectf = new RectF(120, 120, 380,380);   
		Random r; 
		for (int i = 0; i < value_degree.length; i++) {
			if (i == 0) {
				r = new Random();
				int color = Color.argb(255, 230, 0,
						0);
				paint.setColor(color); 
				 
				canvas.drawArc(rectf, 90, value_degree[i], true, paint); 
			} else {
				temp =90 +value_degree[i - 1];
				r = new Random();
				int color = Color.argb(255,0, 255,
						0); 
				paint.setColor(color);
				  
				canvas.drawArc(rectf, temp, value_degree[i], true, paint);
			}
		}
	}
}
