/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fwlk.cookui;
import java.util.*;
/**
 *
 * @author breeze
 */
public class Order {
    ArrayList<Item> items;
    
    public String toString() {
        int l = 0;
        for (int i = 0; i < this.items.size(); i++){
            System.out.printf(items.get(i).toString());
        }
        return("");
    }
}
