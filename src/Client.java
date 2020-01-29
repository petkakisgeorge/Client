import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main (String[] args) {
        // arguments supply message and hostname
        Socket s = null;
        int aa=0;
        String input;
        try{
            //int serverPort = Integer.parseInt(args[1]);
          //  s = new Socket(args[0], serverPort);
            int serverPort = 7896;
            s = new Socket("127.0.0.1", serverPort);
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            PrintWriter out = new PrintWriter(s.getOutputStream());
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            printingServer();
            printingMainMenu();
            input=stdIn.readLine();
            String input2="";
            while(input != null){
                out.println(input);
                out.flush();
                if(input.equals("LogIn")) //for log in
                {
                    do {
                            printingServer();
                            System.out.println("Type your username");
                            input = stdIn.readLine();
                            out.println(input);
                            out.flush();
                            printingServer();
                            System.out.println("Type your password");
                            input = stdIn.readLine();
                            out.println(input);
                            out.flush();
                        String User  = bf.readLine();
                        if (!User.equals("Wrong")) {
                            printingServer();
                            System.out.println("Welcome back "+User+"!");
                            printingSubMenu();
                            do{
                                input= stdIn.readLine();
                                out.println(input);
                                out.flush();
                                if(input.equals("NewEmail"))
                                {
                                    do {
                                        System.out.println("Type the receiver");
                                        input = stdIn.readLine();
                                        out.println(input);
                                        out.flush();
                                        input2=bf.readLine();
                                        if(input2.equals("IsNotOk")) {
                                            System.out.println("The Receiver doesn't exist! \nTry again.");
                                            wait(1);
                                        }
                                    }while(!input2.equals("IsOk"));
                                    System.out.println("Type the Subject");
                                    input= stdIn.readLine();
                                    out.println(input);
                                    out.flush();
                                    System.out.println("Type the mainbody");
                                    input= stdIn.readLine();
                                    out.println(input);
                                    out.flush();
                                    System.out.println("Message was sent successfully");
                                    wait(1);
                                }
                                else if(input.equals("ShowEmails"))
                                {
                                    int k = Integer.parseInt(bf.readLine()); //reads the number of emails
                                    String Subjects = bf.readLine();
                                    String Senders = bf.readLine();
                                    String IfIsNew = bf.readLine();
                                    String[] a = Subjects.split("~");
                                    String[] b = Senders.split("~");
                                    String[] c = IfIsNew.split("~");
                                    boolean[] cc = new boolean[k];
                                    for(int i=0;i<k;i++)
                                    {
                                        cc[i]= c[i].equals("true");
                                    }
                                    if(k!=0)
                                        showEmails(k,b,a,cc);
                                    else
                                        System.out.println("There are no Emails");
                                    wait(3);
                                }
                                else if(input.equals("ReadEmail"))
                                {
                                    System.out.println("Give the Email's Id");
                                    input = stdIn.readLine();
                                    out.println(input);
                                    out.flush();
                                    String MainBody = bf.readLine();
                                    if(MainBody.equals("Bad"))
                                        System.out.println("Wrong Email id");
                                    else
                                        System.out.println(MainBody);
                                    wait(1);
                                }
                                else if(input.equals("DeleteEmail"))
                                {
                                    System.out.println("Give the Email's Id");
                                    input = stdIn.readLine();
                                    out.println(input);
                                    out.flush();
                                    input2 = bf.readLine();
                                    if(input2.equals("Bad"))
                                        System.out.println("Wrong Email id");
                                    else
                                        System.out.println("Deleted Successfully");
                                    wait(1);
                                }
                                else if(input.equals("Exit"))
                                {
                                    input2="retry";
                                    aa=1;
                                    break;
                                }
                                if(!input.equals("LogOut"))
                                { printingServer();
                                printingSubMenu();}
                                else
                                    input2="retry";
                            }while(!input.equals("LogOut"));
                        } else {
                            printingServer();
                            System.out.println("Wrong Username Or Password");
                            System.out.println("Try again.");
                            wait(1);
                        }
                    }while(!input2.equals("retry"));
                }
                else if(input.equals("Register")) //creates a new user
                {
                        printingServer();
                        System.out.println("Type a username");
                        input = stdIn.readLine();
                        out.println(input);
                        out.flush();
                        printingServer();
                        System.out.println("Type a password");
                        input = stdIn.readLine();
                        out.println(input);
                        out.flush();
                    System.out.println("User Created Successfully");
                }
                if(input.equals("Exit")) //exits
                {
                    printingServer();
                    System.out.println("Exiting");
                    wait(1);
                    break;
                }
                if(aa==1)
                    break;
                wait(1);
                printingServer();
                printingMainMenu();
                input=stdIn.readLine();
            }
            out.close();
            bf.close();
            s.close();
        }catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
        }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        }catch (IOException e){System.out.println("readline:"+e.getMessage());
        }finally {if(s!=null) try {s.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
    }
    static void printingServer()
    {
        System.out.println("-----------");
        System.out.println("MailServer:");
        System.out.println("-----------");
    }
    static void printingMainMenu()
    {
        System.out.println("> Register");
        System.out.println("> LogIn");
        System.out.println("> Exit");
    }
    static void printingSubMenu()
    {
        System.out.println("================");
        System.out.println("> NewEmail");
        System.out.println("> ShowEmails");
        System.out.println("> ReadEmail");
        System.out.println("> DeleteEmail");
        System.out.println("> LogOut");
        System.out.println("> Exit");
        System.out.println("================");
    }
    static public void showEmails(int size,String[] sender,String[] Subject,boolean[] arr)
    {
        System.out.println("Id        From            Subject");
        for(int i=0;i<size;i++)
        {
            System.out.print((i+1)+". ");
            if(arr[i])
                System.out.print("[New]  ");
            else
                System.out.print("       ");
            System.out.print(sender[i]);
            System.out.print("           ");
            System.out.println(Subject[i]);
        }
    }
    public static void wait (int k){
        long time0, time1;
        time0 = System.currentTimeMillis();
        do{
            time1 = System.currentTimeMillis();
        }while((time1 - time0) < k * 1000);
    }
}
