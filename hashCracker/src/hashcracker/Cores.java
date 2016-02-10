/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcracker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author Ryan Darnell and William Paddock
 * 
 */
public class Cores {
    ArrayList<String> passes;

    public Cores() {
        passes = new ArrayList<>();
    }
    
    public void add(String e){
        passes.add(e);
    }
    
    public ArrayList<String> getList(){
        return passes;
        
    }
}
