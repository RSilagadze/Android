package com.ece.b3.dwm.tpannoncesproject.widgets;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class ErrorDialog extends AlertDialog {
	
	private AlertDialog.Builder builder;
	
	public ErrorDialog (Activity sender){
		super(sender);
		this.builder = new AlertDialog.Builder(sender);
	}

 	@Override
	public void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
		this.builder.setTitle("Erreur");
		this.builder.setMessage("Une erreur du service s'est réproduite");
		this.builder.setNeutralButton("Retour", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dismiss();
			}
		});
	}

	@Override
	public void show() {
		super.show();
		this.builder.show();
	}
}
