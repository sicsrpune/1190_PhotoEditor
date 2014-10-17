package com.example.photoeditor;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnClickListener;


public class ImageCapture extends Activity {
	protected static final int REQUEST_IMAGE_CAPTURE = 0;
	ImageView img;
	ImageButton cropbtn;
	ImageButton rotatebtn;
	ImageButton brt_cont;
	ImageButton addtextbtn;
	ImageButton save_btn;
	ImageButton close_btn;
	Bitmap bitmap;
	String path = null;
	
	//Button capture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_main_menu);

		//capture = (Button)findViewById(R.id.button1);
		img=(ImageView)findViewById(R.id.imageView1);
			if(getIntent().hasExtra("img")) {
			
			bitmap = getIntent().getExtras().getParcelable("img");
			//Log.e("this",bitmap.toString());
			img.setImageBitmap(bitmap);
		}
		
		
		
		if(getIntent().hasExtra("gallery")) {
			path = getIntent().getStringExtra("gallery");
			bitmap = BitmapFactory.decodeFile(path);
			img.setImageBitmap(bitmap);
		}
		
		
		
				
		rotatebtn = (ImageButton)findViewById(R.id.rotatebtn);
		rotatebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(getApplicationContext(), "Image Button Clicked", Toast.LENGTH_SHORT).show();
				Bitmap rotateImg = ((BitmapDrawable)img.getDrawable()).getBitmap();
				
				Matrix mat = new Matrix();
				mat.postRotate(90);
				
				Bitmap newRotateImg = Bitmap.createBitmap(rotateImg, 0, 0, rotateImg.getWidth(), rotateImg.getHeight(), mat, true);
				img.setImageBitmap(newRotateImg);
			}
		});
		
		brt_cont = (ImageButton)findViewById(R.id.banc);
		brt_cont.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Bitmap b = ((BitmapDrawable)img.getDrawable()).getBitmap();
				Intent i = new Intent(getApplicationContext(),BrightnessContrastSaturation.class);
				i.putExtra("Banc", b);
				Toast.makeText(getApplicationContext(), "Sending image to the activity.", Toast.LENGTH_SHORT).show();
				startActivity(i);
			}
		});
		
		addtextbtn = (ImageButton)findViewById(R.id.text);
		addtextbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.e("add", "TEXT");
				Bitmap b = ((BitmapDrawable)img.getDrawable()).getBitmap();
				Intent i = new Intent(getApplicationContext(),AddTextActivity.class);
				i.putExtra("text", b);
				startActivity(i);
			}
		});
		save_btn = (ImageButton)findViewById(R.id.save);
		save_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
	            Toast.makeText(getApplicationContext(), "Inside save image", Toast.LENGTH_SHORT).show();
				String path = Environment.getExternalStorageDirectory().toString();
		        
		        File directory = new File(path+"/PhotoEditor");
		        
		        if(!directory.exists())
		        {
		        	directory.mkdirs();
		        }
		        else
		        	Toast.makeText(getApplicationContext(), "Directory exists", Toast.LENGTH_SHORT).show();
				Date d = new Date();
				int day = d.getDay();
				int mon = d.getMonth();
				int year = d.getYear();
				int hr = d.getHours();
				int mn = d.getMinutes();
				String date1 = Integer.toString(day)+Integer.toString(mon)+Integer.toString(year)
						+Integer.toString(hr)+Integer.toString(mn);
				File mypath=new File(directory,"IM"+date1+".jpg");
				 
				FileOutputStream fos = null;
				 
				 try {           
					 Toast.makeText(getApplicationContext(), "inside try", Toast.LENGTH_SHORT).show();
			            fos = new FileOutputStream(mypath);
			            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			            Toast.makeText(getApplicationContext(), "Image saved", Toast.LENGTH_SHORT).show();
			            fos.close();
			        } 
				 	catch (Exception e) 
				 	{
				 		Toast.makeText(getApplicationContext(), "Exception is raised."+e.toString(), Toast.LENGTH_LONG).show();
			            e.printStackTrace();
			        }
			}	});
			
			close_btn = (ImageButton)findViewById(R.id.close);
			close_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(getApplicationContext(),MainActivity.class);
					startActivity(i);
				}
			});
	}
}
