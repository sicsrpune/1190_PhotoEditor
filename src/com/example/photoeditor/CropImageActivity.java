package com.example.photoeditor;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CropImageActivity extends Activity {
	public ImageView img;
	public Button btn_crop;
	public final int PIC_CROP = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("this","I m here in crop2.");
		setContentView(R.layout.activity_crop_image);
		img = (ImageView)findViewById(R.id.imageView1);
		btn_crop = (Button)findViewById(R.id.done1);
		if(getIntent().hasExtra("cropimg"))
		{
			Toast.makeText(getApplicationContext(), "image cropping", Toast.LENGTH_SHORT).show();
			Bitmap bitmap  = (Bitmap)getIntent().getParcelableExtra("cropimg");
			//Log.e("this",bitmap.toString());
			img.setImageBitmap(bitmap);
		}
		 try {

		        Intent cropIntent = new Intent("com.android.camera.action.CROP");
		        // indicate image type and Uri
		       
		     //   cropIntent.setDataAndType(img,"image");
		        // set crop properties
		        cropIntent.putExtra("crop", "true");
		        // indicate aspect of desired crop
		        cropIntent.putExtra("aspectX", 1);
		        cropIntent.putExtra("aspectY", 1);
		        // indicate output X and Y
		        cropIntent.putExtra("outputX", 128);
		        cropIntent.putExtra("outputY", 128);
		        // retrieve data on return
		        cropIntent.putExtra("return-data", true);
		        // start the activity - we handle returning in onActivityResult
		        startActivityForResult(cropIntent, PIC_CROP);
		    }
		 catch (ActivityNotFoundException anfe) {
		        // display an error message
		        String errorMessage = "Whoops - your device doesn't support the crop action!";
		        Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
		        toast.show();
		        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crop_image, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_crop_image,
					container, false);
			return rootView;
		}
	}

}
