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

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Runnable;
import static java.lang.System.out;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PasswordDict implements Runnable {
    private String password;
    private String dict;
    private Set<String> commonPwds;
    Map<String,String> hash2unames;
    static String hash;
    
    public PasswordDict(String password) {
        //this.password = password;
        this.dict = dict;
    }

    public void setCommonPwds(Set<String> commonPwds) {
        this.commonPwds = commonPwds;
    }
    
    public void setHash2unames(Map<String, String> hash2unames) {
        this.hash2unames = hash2unames;
    }

    public static void setHash(String hash) {
        PasswordDict.hash = hash;
    }
    
    
    
    
    
    public void run() {
        
        for (String p : commonPwds) {
            
            if (hash2unames.containsKey(hash)) {
                String user = hash2unames.get(hash);
                out.println("The password for " + user + " is " + p);
            }
        }
        
    }
    
    private static Set<String> loadPasswords(String filename)
            throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filename));
        Set<String> pwds = new HashSet<String>();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            pwds.add(line);
        }
        scan.close();
        return pwds;
    }
    
    private static String md5hash(String s) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("md5");
        md5.update(s.getBytes());
        byte[] md5Bytes = md5.digest();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<md5Bytes.length; i++) {
            String hex = Integer.toHexString(0xff & md5Bytes[i]);
            if (hex.length() == 1) { sb.append('0'); }
            sb.append(hex);
        }
        return sb.toString();
    }
    
}
