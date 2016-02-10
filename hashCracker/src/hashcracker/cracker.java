/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hashcracker;

/**
 *
 * @author Will Ryan
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.out;
import static java.lang.System.err;

public class Cracker
{
    private static Thread t;
    private static String threadName;
    private static String hash;
    private static String password = "";
    private static ArrayList<String> commonPwds;
    private static Map<String,String> hash2unames;
    
            
    public static void RunnableHash (String digest){
        threadName = digest;
        System.out.print("Creating "+threadName);
    }
    public void run() {
        System.out.println("Running " +  threadName );
        try {
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // Let the thread sleep for a while.
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }

    public static void start (String common)
    {
        System.out.println("Starting " +  threadName );
        if (t == null)
        {
            PasswordDict d = new PasswordDict(password);
            //d.setCommonPwds(commonPwds);
            d.setHash2unames(hash2unames);
            d.setHash(hash);
            t = new Thread(d);
            t.start();
        }
    }
    private static ArrayList<String> loadPasswords(String filename)
            throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filename));
        ArrayList<String> pwds = new ArrayList<String>();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            pwds.add(line);
        }
        scan.close();
        return pwds;
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

    public static void main (String[] args) throws FileNotFoundException, NoSuchAlgorithmException {
        if (args.length != 2) {
            err.println("Usage: java edu.sjsu.crypto.Cracker <common passwords> <hashed passwords>");
        }

        out.println("Loading passwords");
        commonPwds = loadPasswords(args[0]);
        
        out.println("Loading hashes");
        hash2unames = loadHash2Username(args[1]);

//        for (String p : commonPwds) {
//            
//        }
        //start(args[0]);
        long start = getSec();
        for (String p : commonPwds) {
            hash = md5hash(p);
            if (hash2unames.containsKey(hash)) {
                String user = hash2unames.get(hash);
                long finish = getSec();
                long time = finish - start;
                out.println("The password for Hashkey " + user + " is " + p + "found in " + time);
            }
        }
    }
    static long getSec() {
        return System.currentTimeMillis() / 1000;
    }
}
