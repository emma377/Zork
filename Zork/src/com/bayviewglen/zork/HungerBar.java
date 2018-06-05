package com.bayviewglen.zork;

public class HungerBar {
     
     private static final int max = 20;
     private String[] hunger; 

     public HungerBar() {
           hunger = new String[max]; 

           for (int i = 0; i < max; i++){ 
                hunger[i] = "x"; 
           }
     }

     public boolean increase() {
           boolean a = false; 
           for (int i = 0; i < 100; i++) {
                for (int k = 0; k < max; k++) {
                     if (hunger[k].equals("Y")) {
                           hunger[k] = "x";
                           i = 100; 
                           a = true; 
                     }
                }
           }
           printHungerBar(); 
           return a; 

     } 
     
     public boolean decrease() {
           boolean a = true; 
           if (hunger[max-1].equals("x")) { 
                hunger[max-1] = "Y";
                return true; 
           }else{
                for(int i = 0; i < max; i++) {
                     if (hunger[i].equals("Y")) {
                           if (i != 0) {
                                hunger[i-1] = "Y"; 
                                a =  true; 
                                return a; 
                           }else {
                                a = false;
                                return a; 
                           }
                     } 
                } 
     hunger[0] = "Y"; 
     a = false; 
     return a; 
     }
                


     }
     
     public void printHungerBar() {
           System.out.println("Hunger Bar: "); 
           for (int i = 0; i <max; i++) {
                System.out.println("{" + hunger[i] + "}");
           }
           System.out.println("");
     }
}


