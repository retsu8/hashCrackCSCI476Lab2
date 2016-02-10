/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hashcracker;

/**
 *
 * @author Will and Ryan
 */

import java.lang.Runnable;

public class PasswordDict implements Runnable {
    private String password;
    private String dict;

    public PasswordDict(String password, String dict) {
        this.password = password;
        this.dict = dict;
    }
    
    public void run(){
        
    }
    
}
