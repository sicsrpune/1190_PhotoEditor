package com.example.photoeditor;

import com.example.photoeditor.R.id;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.os.Build;

public class BrightnessContrastSaturation extends Activity {
ImageView img,imgviewdist;
SeekBar bright,contrast,saturation;
Button done;
Bitmap src,dist;
int PIC = 0;
int MAX_WIDTH=240;
int MAX_HEIGHT=240;

int imghight,imgwidth;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brightness_contrast_saturation);
		img = (ImageView)findViewById(R.id.imageView1);
		
		bright = (SeekBar)findViewById(R.id.seekBar1);
		contrast = (SeekBar)findViewById(R.id.seekBar2);
		saturation = (SeekBar)findViewById(R.id.seekBar3);
		
		done = (Button)findViewById(R.id.done);
		if(getIntent().hasExtra("Banc"))
		{
			//Toast.makeText(getApplicationContext(), "I got image.", Toast.LENGTH_SHORT).show();
			Bitmap bitmap  = (Bitmap)getIntent().getParcelableExtra("Banc");
			img.setImageBitmap(bitmap);
			
			src = bitmap;
		}
		Resources res = getBaseContext().getResources();
		//int photoid = R.id.imageView1;
		//src = BitmapFactory.decodeResource(res, photoid);
		imghight = src.getHeight();
		imgwidth = src.getWidth();
		Log.e("this","bitmap "+src);
		dist = Bitmap.createBitmap(imgwidth,imghight,Config.ARGB_8888);
		
		saturation.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
				Bitmap bmp = Bitmap.createBitmap(imgwidth,imghight,Config.ARGB_8888);
				ColorMatrix colMat = new ColorMatrix();
				colMat.setSaturation((float) (progress/100.0));
				Paint paint = new Paint();
				paint.setColorFilter(new ColorMatrixColorFilter(colMat));
				
				Canvas canvas = new Canvas(bmp);
				canvas.drawBitmap(src, 0,0, paint);
				img.setImageBitmap(bmp);
			}

			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {}
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {}
		});
		
		bright.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
				Log.e("Progress","BProgress "+progress);
				Bitmap bmp = Bitmap.createBitmap(imgwidth, imghight, Config.ARGB_8888);
				int brightness = progress - 127;
				Log.e("Brightness","Brightness "+brightness);
				ColorMatrix colmat =  new ColorMatrix();
				
				colmat.set(new float[]{1,0,0,0,brightness,
						0,1,0,0,brightness,
						0,0,1,0,brightness,
						0,0,0,1,0});
				Paint paint = new Paint();
				paint.setColorFilter(new ColorMatrixColorFilter(colmat));
				
				Canvas canvas = new Canvas(bmp);
				canvas.drawBitmap(src, 0,0, paint);
				img.setImageBitmap(bmp);
			}
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}
		});
		
		contrast.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				Log.e("Progress","BProgress "+progress);
				Bitmap bmp = Bitmap.createBitmap(imgwidth, imghight, Config.ARGB_8888);
				float cont = (float) ((progress + 64)/128);
				Log.e("Brightness","Brightness "+progress);
				ColorMatrix colmat =  new ColorMatrix();
				
				 colmat.set(new float[] { cont, 0, 0, 0, 0,
						 0,cont, 0, 0, 0,   
                         0, 0, cont, 0, 0,
                         0, 0, 0, 1, 0 });
				Paint paint = new Paint();
				paint.setColorFilter(new ColorMatrixColorFilter(colmat));
				
				Canvas canvas = new Canvas(bmp);
				canvas.drawBitmap(src, 0,0, paint);
				img.setImageBitmap(bmp);
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
		});
		
		done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Bitmap b = ((BitmapDrawable)img.getDrawable()).getBitmap();
				Intent i = new Intent(getApplicationContext(), ImageCapture.class);
				i.putExtra("img", b);
				startActivity(i);
			}
		});
	}
}
