package com.androidhive.dashboard;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import androidhive.dashboard.R;

public class CatalogActivity extends Activity {

 private List<Product> mProductList;
 
 public static String GET(String url){
     InputStream inputStream = null;
     String result = "";
     try {
         // create HttpClient
         HttpClient httpclient = new DefaultHttpClient();
         // make GET request to the given URL
         HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
         // receive response as inputStream
         inputStream = httpResponse.getEntity().getContent();
         // convert inputstream to string
         if(inputStream != null)
             result = convertInputStreamToString(inputStream);
         else
             result = "Did not work!";
     } catch (Exception e) {
         Log.d("InputStream", e.getLocalizedMessage());
     }
     return result;
 }
 private static String convertInputStreamToString(InputStream inputStream) throws IOException{

     BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
     String line = "";
     String result = "";
     while((line = bufferedReader.readLine()) != null)
         result += line;
     inputStream.close();
     return result;

 }
 public boolean isConnected(){
     ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
         if (networkInfo != null && networkInfo.isConnected()) 
             return true;
         else
             return false;   
 }
 private class HttpAsyncTask extends AsyncTask<String, Void, String> {
     @Override
     protected String doInBackground(String... urls) {
         return GET(urls[0]);
     }
     // onPostExecute displays the results of the AsyncTask.
     @Override
     protected void onPostExecute(String result) {
    	 Log.d("InputStream", result);
    }
 }
 /** Called when the activity is first created. */
 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.catalog);

  // Obtain a reference to the product catalog
  mProductList = ShoppingCartHelper.getCatalog(getResources());
  new HttpAsyncTask().execute("http://192.168.56.1:8080/pooja/temples.htm");
  
  // Create the list
  ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
  listViewCatalog.setAdapter(new ProductAdapter(mProductList, getLayoutInflater(), false));
  
  listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

   @Override
   public void onItemClick(AdapterView<?> parent, View view, int position,
     long id) {
    Intent productDetailsIntent = new Intent(getBaseContext(),ProductDetailsActivity.class);
    productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
    startActivity(productDetailsIntent);
   }
  });
  
  Button viewShoppingCart = (Button) findViewById(R.id.ButtonViewCart);
  viewShoppingCart.setOnClickListener(new OnClickListener() {
   
   @Override
   public void onClick(View v) {
    Intent viewShoppingCartIntent = new Intent(getBaseContext(), ShoppingCartActivity.class);
    startActivity(viewShoppingCartIntent);
   }
  });

 }
}
