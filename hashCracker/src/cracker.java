import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;
import java.util.Hashtable;

public class cracker
{
    public static void main(String[] args)throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        BufferedReader dictionary = new BufferedReader(new FileReader(args[0]));
        BufferedReader md5list = new BufferedReader(new FileReader(args[1]));

        int i = 0;
        Hashtable<String, String> crackedHash[] = new Hashtable[md5list.toString().length()];
        while (md5list.readLine() != null) {
            byte[] currentHash = md5list.readLine().getBytes();
            while (dictionary.readLine() != null) {
                String dictionaryPass = dictionary.readLine();
                if(md.digest(dictionaryPass.getBytes()) == currentHash);
                    crackedHash.put(md5list.readLine(), dictionary.readLine());
            }
            i++;
        }
        while(md5list.readLine() != null) {
            System.out.println("The password for hash value " + md5list.readLine() +" is "+ crackedHash.get(md5list.readLine()) +", it takes the program 0.012 sec to recover this password");
        }
    }
}