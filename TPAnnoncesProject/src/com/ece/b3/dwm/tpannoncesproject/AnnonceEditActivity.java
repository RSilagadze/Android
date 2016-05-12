package com.ece.b3.dwm.tpannoncesproject;

import java.util.ArrayList;
import com.ece.b3.dwm.tpannoncesproject.constants.AppPrefs;
import com.ece.b3.dwm.tpannoncesproject.constants.Debug;
import com.ece.b3.dwm.tpannoncesproject.constants.Extra;
import com.ece.b3.dwm.tpannoncesproject.constants.Http;
import com.ece.b3.dwm.tpannoncesproject.constants.ServiceCaller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AnnonceEditActivity extends CustomBaseActivity {
	
	private AnnonceEditActivity activityInstance = this;
	private EditText edtTitle;
	private EditText edtDesc;
	private EditText edtPrix;
	private EditText edtUserId;
	private ImageView imgLogo;
	private Button btnDwnLogo;
	private Button btnReset;
	private Button btnValidate;
	private int widthX;
	private int heightY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_annonce_insert);
		
    	Point p = Extra.getDisplaySize(this);
    	this.widthX = p.x;
    	this.heightY = p.y;
		
		
		edtTitle = (EditText)findViewById(R.id.editTitle);
		edtTitle.clearFocus();
		edtDesc = (EditText)findViewById(R.id.editDesc);
		edtDesc.clearFocus();
		edtPrix = (EditText)findViewById(R.id.editPrix);
		edtPrix.clearFocus();
		edtUserId = (EditText)findViewById(R.id.editIdUser);
		edtUserId.clearFocus();
		
		imgLogo = (ImageView)findViewById(R.id.imgViewIcon);
		imgLogo.getLayoutParams().width = this.widthX / 2;
		imgLogo.getLayoutParams().height = this.heightY / 4;
		
		btnDwnLogo = (Button) findViewById(R.id.btnUpload);
		btnReset = (Button)findViewById(R.id.btnReset);
		btnValidate = (Button)findViewById(R.id.btnValider);
		
		edtUserId.setVisibility(View.INVISIBLE);
		TextView tvId = (TextView)findViewById(R.id.tvUser);
		tvId.setVisibility(View.INVISIBLE);

		resetViews (this);
		initActions ();

	}
	
	private void initActions (){
		btnValidate.setOnClickListener(getValidateBtnClickListener());
		btnReset.setOnClickListener(getResetBtnClickListener());
		btnDwnLogo.setOnClickListener(getOnDwnLoadBtnClicked());
	}
	
	private OnClickListener getOnDwnLoadBtnClicked (){
		return new OnClickListener (){
			@Override
			public void onClick(View v) {
				//Not Implemented yet
			}
		};
	}
	
	private void resetViews (CustomBaseActivity activity){
		Intent intent = activity.getIntent();
		edtDesc.setText (intent.getStringExtra("description"));
		edtTitle.setText (intent.getStringExtra("title"));
		edtPrix.setText (intent.getFloatExtra("cost", -1) + "");
		String fileName = (intent.getStringExtra("imagePath"));
		if (Extra.existsFileWithinPath(AppPrefs.getSDAppDataLocation(), fileName)){
			Bitmap bitmap = BitmapFactory.decodeFile(AppPrefs.getSDAppDataLocation() +"/" + fileName);
			imgLogo.setImageBitmap(bitmap);
		}
		else imgLogo.setImageDrawable(getResources().getDrawable(R.drawable.logo_na)); 
	}
	
	private OnClickListener getResetBtnClickListener (){
		return new OnClickListener (){
			@Override
			public void onClick(View v) {
				resetViews (activityInstance);
			}
		};
	}
	
	private OnClickListener getValidateBtnClickListener (){
		return new OnClickListener (){
			@Override
			public void onClick(View v) {
				String description = edtDesc.getText().toString().trim();
				String title = edtTitle.getText().toString().trim();
				float prix = (float) -1;
				try {
					prix = Float.parseFloat(edtPrix.getText().toString());
				}
				catch (Exception e){
					Log.e(Debug.DEBUG_TAG, e.getMessage());
					Toast.makeText(activityInstance, "Entrez les numéros au format valide", Toast.LENGTH_LONG).show();
					return;
				}
				ArrayList<Pair<String,String>> lstParams = new ArrayList<Pair<String,String>>();
				
				if (description != null)
					lstParams.add(new Pair<String,String> ("Description",description));
				if (title != null)
					lstParams.add(new Pair<String,String> ("Libelle",title));
				if (prix != -1)
					lstParams.add(new Pair<String,String> ("Prix",prix + ""));
				
				int id = activityInstance.getIntent().getIntExtra("id", -1);
				ServiceCaller.startAnnonceService(activityInstance, "PUT", Http.getAnnonceByIdURL(id), lstParams);
			}
		};
	}

	@Override
	public void onCallBack(Bundle args) {
		if (args != null && !args.isEmpty()){
			int code = args.getInt(AppPrefs.getWSQueryTag());
			if (!Extra.isError(code)){
				Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
				setResult(Activity.RESULT_OK, new Intent());
				finish ();
				return;
			}
			else Toast.makeText(this, "Echec", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED, new Intent());
		finish ();
		super.onBackPressed();
		return;
	}
}
