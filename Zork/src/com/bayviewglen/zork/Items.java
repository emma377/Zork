package com.bayviewglen.zork;

import java.util.ArrayList;

public class Items {
     
    private String itemName;
    private double weight;
    private String itemType; 
    private boolean isWeapon;
    private boolean isMedicine; 
    private boolean isFood; 
    
    public Items(String itemName) {
      this.itemName = itemName; 
      this.itemType = this.itemType; 
        this.weight = this.weight;
    }
    
    public Items(String itemName,String itemType, double weight) {
    super();
        this.itemName = itemName;
        this.itemType = itemType; 
        this.weight = weight;
        
        isWeapon = itemType.equals("weapon"); 
        isMedicine = itemType.equals("medicine"); 
        isFood = itemType.equals("food"); 
    }
    
    public String getItemName() {
           return itemName;
     }
    
    public String getItemType() {
           return itemType;
     }
    
    public double getWeight() {
           return weight;
     }
    
    public void removeItem(Items item, ArrayList<Items> roomItems) {
      for(int i=0;i<roomItems.size();i++) {
             if(roomItems.get(i).equals(item))
                   roomItems.remove(i);
      }
    }
    
    public Items stringToItem(String aString) {
      Items item;
      if(!isItem(aString)) {
             return null;
      }else {
             item = new Items(aString, itemType, weight);
             return item;
      }
    }
    
    public boolean isItem(String aString) {
      if(aString.equals(itemName))
             return true;
      return false;
    }


}


