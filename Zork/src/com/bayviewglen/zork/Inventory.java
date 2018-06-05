package com.bayviewglen.zork;
import java.util.ArrayList;

public class Inventory {

     private ArrayList<Items> bag; 
     //private  boolean isInventory; 
     private Items item;
     
     public Inventory(ArrayList<Items> bag) {
           this.bag = bag; 
           
           
           
     }
     
     public boolean isInInventory(Items anItem) {
       for (int i = 0; i < bag.size();) {
             if(bag.get(i).equals(anItem)) { 
              return true;
             }
             i++;
       }
       return false;
     }
     
     public void addItem(Items item) {
           bag.add(item); 
     }
     
     public void removeItem(Items item) {
           for (int i = 0; i < bag.size(); i++) {
                if (item.getItemName().equals(bag.get(i).getItemName()) && item.getItemType().equals(bag.get(i).getItemType()) && item.getWeight() == (bag.get(i).getWeight())) {
                     bag.remove(i); 
                }
           }
     }
     
     
     public void printInventory() {
           if (bag.size() == 0) {
                System.out.println("There is nothing in your inventory! :)"); 
           } else if(bag.size()>0){
           
                for (int i = 0; i < bag.size(); i++) { 
                System.out.println(bag.get(i).toString());
                }
           }else {
                System.out.println("There is nothing in your inventory! :)");
           }
     }
     
     public int numberOfItems() {
           return bag.size(); 
     }
} 

