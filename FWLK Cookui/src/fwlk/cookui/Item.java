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
public class Item {
    String name;
    ArrayList<String> options;
    public String toString(){
        String s = "";
        s = s + this.name;
        int len = options.size();
        int i = 0;
        while(i < len){
            s = s + options.get(i);
        }
        return s;
    }
}
