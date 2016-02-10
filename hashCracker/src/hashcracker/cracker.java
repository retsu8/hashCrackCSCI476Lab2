/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hashcracker;

/**
 *
 * @author William Paddock and Ryan Darnell
 */
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import static java.lang.System.out;
import static java.lang.System.err;

public class Cracker
{
    private static Cores[] commonPwds;
    private static Map<String,String> hash2unames;
    private static long timer;
    
    //Implement Multithreading...
    public static void start (Cores next, int num)
    {
        System.out.println("Starting core " + num + "...");

            PasswordDict d = new PasswordDict();
            d.setCommonPwds(next.getList());
            d.setHash2unames(hash2unames);
            d.setStart(timer);
            Thread t = new Thread(d);
            t.start();
           
    }
    private static Cores[] loadPasswords(String filename) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filename));
        Set<String> pwds = new HashSet();
        int cores = Runtime.getRuntime().availableProcessors() - 1; //main() thread already running
        if (cores < 1) //for single core processors
            cores = 1;
        Cores[] core= new Cores[cores];
        for (int i = 0; i < cores; i++) {
            core[i] = new Cores();
        }
        int j = 0;
        while (scan.hasNextLine()) {
            core[j%cores].add(scan.nextLine());
            j++;
            //pwds.add(line);
           
        }
        scan.close();
        return core;
    }
    

    /**
     * Reads in a whitespace-delimited file of usernames and hashes,
     * returning a map of hashes to usernames.
     */
    private static Map<String,String> loadHash2Username(String filename)
            throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filename));
        Map<String,String> hashes = new HashMap<String,String>();
        while (scan.hasNext()) {
            String hash = scan.next();
            hashes.put(hash,hash);
        }
        scan.close();
        return hashes;
    }

    /**
     * Calculates out the md5 hash value of the specified String,
     * returning the hash as a 32 char hex string.
     */
    public static String md5hash(String s) throws NoSuchAlgorithmException {
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

    public static void main (String[] args) throws FileNotFoundException, NoSuchAlgorithmException {
        
        
        
        if (args.length != 2) {
            err.println("Usage: java edu.sjsu.crypto.Cracker <common passwords> <hashed passwords>");
        }

        out.println("Loading passwords");
        commonPwds = loadPasswords(args[0]);
        
        out.println("Loading hashes");
        hash2unames = loadHash2Username(args[1]);

       
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of cores = " + cores);
        
        timer = System.nanoTime(); //Start the timer to measure how long the code takes to find a match
        //execute new threads for the unused cores
        int num = Runtime.getRuntime().availableProcessors() - 1; 
        if (num < 1) //for single core processors
            num = 1;
        for (int i = 0; i < num; i++) {
            start(commonPwds[i], i+1);
        }
        
    }

}
