package com.ece.b3.dwm.tpannoncesproject.widgets;

import com.ece.b3.dwm.tpannoncesproject.R;
import com.ece.b3.dwm.tpannoncesproject.constants.AppPrefs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

public class NoConnectionDialog extends ConfirmDialog {
	
	public NoConnectionDialog(Activity sender) {
		super("Pas de connexion", "Voulez-vous ourvir le gestionnaire de connexion?", sender);
	}
	
 	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.titleBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.no_connection, 0, 0, 0);
	}

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		this.builder.setNegativeButton("Annuler", getNegativeButtonClick());
		this.builder.setPositiveButton("Oui", getPositiveButtonClick());
		
		return this.builder.create();
    }
	
	public void show() {
		this.show(sender.getFragmentManager(), "no_connection_dialog");
	}

	private DialogInterface.OnClickListener getNegativeButtonClick (){
		return new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		};
	}
	
	private DialogInterface.OnClickListener getPositiveButtonClick (){
		return new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				Bundle bundle = new Bundle ();
			   	bundle.putString(AppPrefs.getWifiIntentTag(), "-1");
				intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
				startActivity(intent);
				dialog.dismiss();
			}
		};
	}
}
