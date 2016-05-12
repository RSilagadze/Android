package com.ece.b3.dwm.tpannoncesproject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.ece.b3.dwm.tpannoncesproject.constants.AppPrefs;
import com.ece.b3.dwm.tpannoncesproject.constants.Debug;
import com.ece.b3.dwm.tpannoncesproject.constants.Extra;
import com.ece.b3.dwm.tpannoncesproject.constants.Http;
import com.ece.b3.dwm.tpannoncesproject.constants.ServiceCaller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AnnonceInsertActivity extends CustomBaseActivity {
	
	private EditText edtTitle;
	private EditText edtDesc;
	private EditText edtPrix;
	private EditText edtUserId;
	private ImageView imgLogo;
	private Button btnDwnLogo;
	private Button btnReset;
	private Button btnValidate;
	
	private AnnonceInsertActivity activityInstance = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_annonce_insert);
		
		edtTitle = (EditText)findViewById(R.id.editTitle);
		edtTitle.clearFocus();
		edtDesc = (EditText)findViewById(R.id.editDesc);
		edtDesc.clearFocus();
		edtPrix = (EditText)findViewById(R.id.editPrix);
		edtPrix.clearFocus();
		edtUserId = (EditText)findViewById(R.id.editIdUser);
		edtUserId.clearFocus();
		
		imgLogo = (ImageView)findViewById(R.id.imgViewIcon);
		btnDwnLogo = (Button) findViewById(R.id.btnUpload);
		btnReset = (Button)findViewById(R.id.btnReset);
		btnValidate = (Button)findViewById(R.id.btnValider);
		
		initActions ();
	}
	
	private void initActions (){
		btnValidate.setOnClickListener(getValidateBtnClickListener());
		btnReset.setOnClickListener(getResetBtnClickListener());
		btnDwnLogo.setOnClickListener(getOnDwnLoadBtnClicked());
	}
	
	private OnClickListener getValidateBtnClickListener (){
		return new OnClickListener (){
			@Override
			public void onClick(View v) {
				String description = edtDesc.getText().toString().trim();
				String title = edtTitle.getText().toString().trim();
				float prix = (float) -1;
				int idUser = -1;
				try {
					prix = Float.parseFloat(edtPrix.getText().toString());
					idUser = Integer.parseInt(edtUserId.getText().toString());
				}
				catch (Exception e){
					Log.e(Debug.DEBUG_TAG, e.getMessage());
					Toast.makeText(activityInstance, "Entrez les numéros au format valide", Toast.LENGTH_LONG).show();
					return;
				}
				
				if (description != null && title != null && idUser != -1 && prix != -1){
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
					ArrayList<Pair<String,String>> lstParams = new ArrayList<Pair<String,String>>();
					lstParams.add(new Pair<String,String> ("Description",description));
					lstParams.add(new Pair<String,String> ("Libelle",title));
					lstParams.add(new Pair<String,String> ("Prix",prix + ""));
					lstParams.add(new Pair<String,String> ("Logo", "logo3.jpg"));
					lstParams.add(new Pair<String,String> ("idUser", idUser + ""));
					lstParams.add(new Pair<String,String> ("Date", dateFormat.format(new Date()) + ""));
					
					ServiceCaller.startAnnonceService(activityInstance, "POST", Http.getAnnoncesURL, lstParams);
				}
			}
		};
	}
	
	private OnClickListener getResetBtnClickListener (){
		return new OnClickListener (){
			@Override
			public void onClick(View v) {
				edtDesc.setText("");
				edtPrix.setText("");
				edtTitle.setText("");
				edtUserId.setText("");
				imgLogo.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.logo_na));
			}
		};
	}
	private OnClickListener getOnDwnLoadBtnClicked (){
		return new OnClickListener (){
			@Override
			public void onClick(View v) {
				//Not Implemented yet
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
