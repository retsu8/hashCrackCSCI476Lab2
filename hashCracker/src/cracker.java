import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;
import java.util.Hashtable;

public class cracker
{
    private Thread t;
    private String threadName;
    RunnableHash (String digest){
        threadName = hash;
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

    public void start ()
    {
        System.out.println("Starting " +  threadName );
        if (t == null)
        {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
    public static void main(String[] args)throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        BufferedReader dictionary = new BufferedReader(new FileReader(args[0]));
        BufferedReader md5list = new BufferedReader(new FileReader(args[1]));

        int i = 0;
        Hashtable<String, String> crackedHash[] = new Hashtable[md5list.toString().length()];
        while (md5list.readLine() != null) {
            //still needs a timer and to be checked.
            long oldTime = getSec();
            byte[] currentHash = md5list.readLine().getBytes();
            while (dictionary.readLine() != null) {
                String dictionaryPass = dictionary.readLine();
                if(md.digest(dictionaryPass.getBytes()) == currentHash);
                    crackedHash.put(md5list.readLine(), dictionary.readLine());
            }
            long newtime = getSec();
            i++;
        }
        while(md5list.readLine() != null) {
            System.out.println("The password for hash value " + md5list.readLine() +" is "+ crackedHash.get(md5list.readLine()) +", it takes the program 0.012 sec to recover this password");
        }
    }
    static long getSec() {
        return System.currentTimeMillis() / 1000;
    }
}
