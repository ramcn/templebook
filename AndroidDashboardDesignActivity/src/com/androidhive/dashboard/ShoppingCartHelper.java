package com.androidhive.dashboard;

import androidhive.dashboard.R;

 
import java.util.List;
import java.util.Vector;
 
import android.content.res.Resources;
 
public class ShoppingCartHelper {
  
 public static final String PRODUCT_INDEX = "PRODUCT_INDEX";
  
 private static List<Product> catalog;
 private static List<Product> cart;
  
 public static List<Product> getCatalog(Resources res){
  if(catalog == null) {
   catalog = new Vector<Product>();
   catalog.add(new Product("Shringeri Temple", res.getDrawable(R.drawable.shringeri),
     "Shringeri temple description", 29.99));
   catalog.add(new Product("Golden Temple", res.getDrawable(R.drawable.goldentemple),
     "Goldent temple description", 24.99));
   catalog.add(new Product("Kerala Temple", res.getDrawable(R.drawable.keralatemple),
     "Kerala temple descrition", 14.99));
  }
   
  return catalog;
 }
  
 public static List<Product> getCart() {
  if(cart == null) {
   cart = new Vector<Product>();
  }
   
  return cart;
 }
 
}