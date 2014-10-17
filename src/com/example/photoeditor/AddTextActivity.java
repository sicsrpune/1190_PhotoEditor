package com.example.photoeditor;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class AddTextActivity extends Activity {
EditText text;
ImageView imgv;
Button ok_btn;
TextView tv;
Bitmap bitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_text);
		text = (EditText)findViewById(R.id.editText1);
		imgv = (ImageView)findViewById(R.id.imageView1);
		ok_btn = (Button)findViewById(R.id.ok);
		
		if(getIntent().hasExtra("text"))
		{
			bitmap  = (Bitmap)getIntent().getParcelableExtra("text");
			imgv.setImageBitmap(bitmap);
		}
	
		
		text.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable arg0) {
				String str = arg0.toString();
				
				Bitmap bit = addTextInImage(getApplicationContext(), bitmap, str);
				
				imgv.setImageBitmap(bit);
			}
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}
		});
		
		ok_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Bitmap b = ((BitmapDrawable)imgv.getDrawable()).getBitmap();
				Intent i = new Intent(getApplicationContext(), ImageCapture.class);
				i.putExtra("img", b);
				startActivity(i);
				
			}
		});
	}
	
	public Bitmap addTextInImage(Context context,Bitmap bitmap, String str)
	{
		Log.e("In","addTextInImage()");

         float scale = bitmap.getDensity();
         Log.e("scale"," :"+scale);
         Bitmap bitmap1 = bitmap;	
         Log.e("bitmap"," :"+bitmap1);
         android.graphics.Bitmap.Config bitmapConfig = bitmap1.getConfig();

         if(bitmapConfig == null) {
           bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
         }

         bitmap1 = bitmap1.copy(bitmapConfig, true);
         
		 Canvas canvas = new Canvas(bitmap1);
		 Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		 paint.setTextSize((int)15);
		 
		 paint.setColor(Color.BLACK);
		 Rect bounds = new Rect();
         paint.getTextBounds(str, 0, str.length(), bounds);
         
         int x = -1 * (bitmap.getWidth() - bounds.width())/6;
         int y = (bitmap.getHeight() + bounds.height())/5;
         Log.e("X"," :"+x);
         Log.e("Y"," :"+y);
         Log.e("XS"," :"+(x*scale));
         Log.e("YS"," :"+(y*scale));
         canvas.drawText(str,25,175, paint);
         
         return bitmap1;
	}
}
