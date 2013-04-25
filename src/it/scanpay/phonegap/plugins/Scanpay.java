package it.scanpay.phonegap.plugins;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import scanpay.it.ScanPayActivity;
import scanpay.it.config.ScanPay;
import scanpay.it.model.SPCreditCard;
import android.app.Activity;
import android.content.Intent;

public class Scanpay extends CordovaPlugin
{
	private CallbackContext _callback;
	
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException
	{
		if (action.equals("startScanpay"))
		{
			_callback = callbackContext;
			this.startScanpay(args, callbackContext);
			return true;
		}
		return false;
	}

	private void startScanpay(JSONArray args, CallbackContext callbackContext)
	{
		String token = null;
		boolean customConfirmation = true;
		try
		{
			token = args.getString(0);
			customConfirmation = args.getBoolean(1);
		}
		catch (Exception e) {}
		
		if (token == null)
		{
			callbackContext.error("No Token specified. You must create an account on scanpay.it to use this feature");
			return;
		}
		
		Intent spPhotoPickerActivity = new Intent(this.cordova.getActivity(), ScanPayActivity.class);
		spPhotoPickerActivity.putExtra(ScanPay.EXTRA_TOKEN, token);
		spPhotoPickerActivity.putExtra(ScanPay.EXTRA_MODE, ScanPay.SCANPAY_MODE_SCAN);
		spPhotoPickerActivity.putExtra(ScanPay.EXTRA_USE_CUSTOM_CONFIRMATION_VIEW, !customConfirmation);
		
		this.cordova.setActivityResultCallback(this);
		this.cordova.getActivity().startActivityForResult(spPhotoPickerActivity, ScanPay.RESULT_SCANPAY_ACTIVITY);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	  super.onActivityResult(requestCode, resultCode, data);
	  if (requestCode == ScanPay.RESULT_SCANPAY_ACTIVITY && resultCode == Activity.RESULT_OK)
	  {
	    SPCreditCard creditCard = (SPCreditCard) data.getParcelableExtra(ScanPay.EXTRA_CREDITCARD);
	    JSONObject dict = new JSONObject();
	    try
	    {
	    	dict.put("cardNumber", creditCard.getNumber());
	    	dict.put("cardMonth", creditCard.month == null ? "" : creditCard.month);
	    	dict.put("cardYear", creditCard.year == null ? "" : creditCard.year);
	    	dict.put("cardCVC", creditCard.pictogram == null ? "" : creditCard.pictogram);
	    }
	    catch (Exception e) { }
	    _callback.success(dict);
	  }
	  else
		  _callback.error("userDidCancel");
	}
}
