package com.petmate.fcfm.petmate.utilidades;

import android.os.AsyncTask;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
public class DescargaServicio extends AsyncTask<String, Void, String>{
	private onDowloadList dList;
	public DescargaServicio (onDowloadList dList){
		this.dList = dList;
	}
	@Override
	protected String doInBackground(String... params) {
		InputStream is = null;
		String result = "";
		try{
			HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		}catch(Exception e){
            Log.e("Error servicio", "" + e.toString());
			e.printStackTrace();
		}
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
		} catch(Exception e){
			e.printStackTrace();
			Log.i("Error", e.toString());
		}
		if(result == null) {
            return "";
        }
		return result;
	}
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		dList.estaDescarga(result);
	}
	public interface onDowloadList{
		public void estaDescarga(String string);
	}
}