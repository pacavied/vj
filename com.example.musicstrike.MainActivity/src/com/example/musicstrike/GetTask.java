package com.example.musicstrike;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

class GetTask extends AsyncTask<Object, Void, String> {
	Context context;
	ProgressDialog mDialog;

	GetTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		mDialog = new ProgressDialog(context);
		mDialog.setMessage("Please wait...");
		mDialog.show();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		mDialog.dismiss();
	}

	@Override
	protected String doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}