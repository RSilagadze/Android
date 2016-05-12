package com.ece.b3.dwm.tpannoncesproject.widgets;

import com.ece.b3.dwm.tpannoncesproject.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

public class ConfirmDialog extends DialogFragment {
	
	protected TextView msgBox;
	protected TextView titleBox;
	protected String title;
	protected String message;
	protected AlertDialog.Builder builder; 
	protected Activity sender;
	
	public ConfirmDialog (String title, String message, Activity sender){
		this.title = title;
		this.message = message;
		this.sender = sender;
		this.builder = new AlertDialog.Builder(this.sender); 
	}
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.msgBox = new TextView (this.sender);
		this.msgBox.setGravity(Gravity.CENTER);
		this.msgBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
		this.msgBox.setText(message);
		
		this.titleBox = new TextView (this.sender);
		this.titleBox.setGravity(Gravity.START);
		this.titleBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f);
		this.titleBox.setText(title);
		this.titleBox.setTextColor(this.getResources().getColor(R.color.lblue));

		this.titleBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.questionmark, 0, 0, 0);
	}

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        builder.setView(this.msgBox);
        builder.setCustomTitle(this.titleBox);
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
	public void show() {
		this.show(sender.getFragmentManager(), "confirm_dialog");
	}
	
	public void setOnClickPositiveButton(String text, OnClickListener click){
		this.builder.setPositiveButton(text, click);
	}
	public void setOnClickNegativeButton(String text, OnClickListener click){
		this.builder.setNegativeButton(text, click);
	}
}