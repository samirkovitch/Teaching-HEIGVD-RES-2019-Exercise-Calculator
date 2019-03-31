import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class Client{
    static final Logger LOG = Logger.getLogger(Client.class.getName());

    private int PortServer;
    private InetAddress AddressIpServer;
    private Socket soc;
    private BufferedReader reader;
    private PrintWriter writer;

    public boolean Connect(int PortServer, InetAddress AddressIpServer)
    {
        this.PortServer = PortServer;
        this.AddressIpServer = AddressIpServer;
        try {
            soc = new Socket(AddressIpServer, PortServer);
            return true;
        }
        catch(IOException e){
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public void makeOperation(char Op, String num1, String num2)
    {
        String req  = Op + " " + num1 + " " + num2 + "\n";
        try {
            reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            writer = new PrintWriter(soc.getOutputStream());


            String line;

            while((line = reader.readLine()).length() != 0)
            {
                System.out.println(line);
            }

            Scanner scan = new Scanner(System.in);
            req = scan.nextLine();
            writer.println(req);
            writer.flush();
            System.out.println(reader.readLine());
        }
        catch(IOException e)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
        }
        finally
        {
            writer.close();
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }



    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
        InetAddress ipServ = null;
        try {
            ipServ = InetAddress.getByName(args[0]);
        }
        catch(IOException e)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
        }
        Client Cli = new Client();
        Cli.Connect(6259,ipServ);
        Cli.makeOperation('+',"1","1");
    }

}