package com.tan.oldphone.common;

import com.tan.oldphone.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.Gravity;
import android.widget.Toast;

public class AndroidUtils {

	public static void createToast_Short(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void createPNDialog(Context context, String title,
			String message, OnClickListener ponClickListener,
			OnClickListener nonClickListener) {
		Dialog dialog = new AlertDialog.Builder(context).setTitle(title)
				.setMessage(message)
				.setPositiveButton(R.string.common_sure, ponClickListener)
				.setNegativeButton(R.string.common_cancel, nonClickListener)
				.create();
		dialog.show();
	}

	public static void createNDialog(Context context, String title,
			String message, OnClickListener nonClickListener) {
		Dialog dialog = new AlertDialog.Builder(context).setTitle(title)
				.setMessage(message)
				.setNegativeButton(R.string.common_good, nonClickListener)
				.create();
		dialog.show();
	}
	
	public static void createNDialog(Context context,
			String message, OnClickListener nonClickListener) {
		Dialog dialog = new AlertDialog.Builder(context).setTitle(null)
				.setMessage(message)
				.setNegativeButton(R.string.common_sure, nonClickListener)
				.create();
		dialog.show();
	}
}
