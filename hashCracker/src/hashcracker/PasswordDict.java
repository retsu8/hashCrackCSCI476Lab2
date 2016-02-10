/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hashcracker;

/**
 *
 * @author Ryan Darnell and William Paddock
 */

import static java.lang.System.out;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordDict implements Runnable {
    private ArrayList<String> commonPwds;
    Map<String,String> hash2unames;
    private String hash;
    private long start;

    public void setStart(long start) {
        this.start = start;
    }
    

    public void setCommonPwds(ArrayList<String> commonPwds) {
        this.commonPwds = commonPwds;
    }
    
    public void setHash2unames(Map<String, String> hash2unames) {
        this.hash2unames = hash2unames;
    }   
      
    @Override
    public void run() {
        for (String p : commonPwds) {
            try {
                hash = Cracker.md5hash(p);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(PasswordDict.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (hash2unames.containsKey(hash)) {
                double timed = (System.nanoTime() - start) / Math.pow(10, 9);
                String times = String.format("%.2f", timed);
                out.println("The password for hashkey " + hash2unames.get(hash) + " is " + p + "\nwhich took " + times + " seconds to find.");
            }
        }
    }
}
