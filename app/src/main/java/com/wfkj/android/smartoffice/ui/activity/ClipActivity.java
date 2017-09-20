package com.wfkj.android.smartoffice.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.util.ImageTools;
import com.wfkj.android.smartoffice.util.view_util.ClipImageLayout;

import java.io.File;

public class ClipActivity extends Activity {
	private ClipImageLayout mClipImageLayout;
	private String path;
	private ProgressDialog loadingDialog;
	private TextView back;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clipimage);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		back= (TextView) findViewById(R.id.back);

        loadingDialog=new ProgressDialog(this);
        loadingDialog.setTitle("正在加载...");
		path=getIntent().getStringExtra("path");
		if(TextUtils.isEmpty(path)||!(new File(path).exists())){

			return;
		}
		Bitmap bitmap= ImageTools.convertToBitmap(path, 720,480);
		if(bitmap==null){
			Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
			return;
		}
		mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);

		mClipImageLayout.setBitmap(bitmap);
		((Button)findViewById(R.id.id_action_clip)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loadingDialog.show();
				new Thread(new Runnable() {
					@Override
					public void run() {
						//"/ClipHeadPhoto/cache/"
						Bitmap bitmap = mClipImageLayout.clip();
						String path= Environment.getExternalStorageDirectory()+"/jlmct/imageloader/Cache/";
						String name="zui"+ System.currentTimeMillis()+".png";
						ImageTools.savePhotoToSDCard(bitmap,path,name);
						loadingDialog.dismiss();
						Intent intent = new Intent();
						intent.putExtra("path",path+name);
						setResult(RESULT_OK, intent);
						finish();
					}
				}).start();
			}
		});

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		this.setContentView(R.layout.empty_view);
//		if (mam!=null){
//			mam.popOneActivity(ClipActivity.this);
//		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);


	}
}
