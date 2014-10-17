package com.example.photoeditor;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	int flag=0;
	ImageButton gal,capt;
	private static int RESULT_LOAD_IMAGE = 1;
	final int REQUEST_IMAGE_CAPTURE = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		gal=(ImageButton)findViewById(R.id.imageButton1);
		capt=(ImageButton)findViewById(R.id.imageButton2);
		
		gal.setOnClickListener(new View.OnClickListener() {	
	
			@Override
			public void onClick(View arg0) {
				//gallery open code
				flag=1;
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE); 
			}
		});
		
		capt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				flag = 0;
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			}
		});

	}

	@Override
protected void onActivityResult(int requestCode,int resultCode,Intent data){
		
		super.onActivityResult(requestCode, resultCode, data);
		
		Bitmap thumbnail = null;
		
		if(flag == 0) {
		    if (requestCode == REQUEST_IMAGE_CAPTURE) {
	
		        if (resultCode == RESULT_OK) {
	
		            thumbnail = (Bitmap) data.getExtras().get("data");
		            
		            Intent i = new Intent(this, ImageCapture.class);
		            i.putExtra("img", thumbnail);
		            startActivity(i);
		        }
		    }
		}
		else {
			if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
				
				
				Uri selectedImage = data.getData();
				
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				
				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();
				
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);

//				thumbnail = BitmapFactory.decodeFile(picturePath);
//				
//				// imgView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//				
//				cursor.close();
				
				Intent i = new Intent(this, ImageCapture.class);
				i.putExtra("gallery", picturePath);
	            startActivity(i);
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_about) {
			Intent intent = new Intent(this,AboutAction.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
