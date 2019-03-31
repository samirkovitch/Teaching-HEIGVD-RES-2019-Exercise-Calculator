
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

/**
 * A very simple example of TCP server. When the server starts, it binds a
 * server socket on any of the available network interfaces and on port 2205. It
 * then waits until one (only one!) client makes a connection request. When the
 * client arrives, the server does not even check if the client sends data. It
 * simply writes the current time, every second, during 15 seconds.
 *
 * To test the server, simply open a terminal, do a "telnet localhost 2205" and
 * see what you get back. Use Wireshark to have a look at the transmitted TCP
 * segments.
 *
 * @author Olivier Liechti
 */
public class Server {

    static final Logger LOG = Logger.getLogger(Server.class.getName());
    private final int listenPort = 6259;

    /**
     * This method does the entire processing.
     */
    public void start() {
        System.out.println("Starting server...");

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;

        String line;
        String[] splitLine;
        int firstNumber;
        int secondNumber;
        boolean shouldRun = true;

        while (true) {
            try {
                LOG.log(Level.INFO, "Creating a server socket and binding it on any of the available network interfaces and on port {0}", new Object[]{Integer.toString(listenPort)});
                serverSocket = new ServerSocket(listenPort, 50, InetAddress.getLocalHost());
                logServerSocketAddress(serverSocket);

                LOG.log(Level.INFO, "Waiting (blocking) for a connection request on {0} : {1}", new Object[]{serverSocket.getInetAddress(), Integer.toString(serverSocket.getLocalPort())});
                clientSocket = serverSocket.accept();

                LOG.log(Level.INFO, "A client has arrived. We now have a client socket with following attributes:");
                logSocketAddress(clientSocket);

                LOG.log(Level.INFO, "Getting a Reader and a Writer connected to the client socket...");
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream());

                ////// DEBUT
                writer.println("Welcome to the Exercise-Calculator\n" +
                        "You can send me some operation int the format: [operation] [first number] [second number]\n" +
                        "The 2 numbers have to be integer\n" +
                        "Operation available: + - * /\n");

                writer.flush();

                line = reader.readLine();
                LOG.log(Level.INFO, line);

                splitLine = line.split("[\\s]");

                if (splitLine.length > 3) {
                    writer.println("Too many arguments or wrong splter. Your operation and yours numbers must be seperated by a single space" +
                            "And Restpect the format [operation] [first number] [second number]\n");
                } else if (splitLine.length < 3) {
                    writer.println("Not enough arguments or wrong splter. Your operation and yours numbers must be seperated by a single space" +
                            "And Restpect the format [operation] [first number] [second number]\n");

                } else {
                    try {
                        firstNumber = new Integer(splitLine[1]).intValue();
                        secondNumber = new Integer(splitLine[2]).intValue();

                        if (splitLine[0].equals("+")) {
                            writer.println("Resultat: " + firstNumber + " + " + secondNumber + " = " + (firstNumber + secondNumber) + "\n");
                        } else if (splitLine[0].equals("-")) {
                            writer.println("Resultat: " + firstNumber + " - " + secondNumber + " = " + (firstNumber - secondNumber) + "\n");
                        } else if (splitLine[0].equals("*")) {
                            writer.println("Resultat: " + firstNumber + " * " + secondNumber + " = " + (firstNumber * secondNumber) + "\n");
                        } else if (splitLine[0].equals("/")) {
                            writer.println("Resultat: " + firstNumber + " / " + secondNumber + " = " + (firstNumber / secondNumber) + "\n");
                        } else {
                            writer.println("Wrong operation");
                        }
                    } catch (NumberFormatException e) {
                        writer.println("One or the two number are not integer");
                    }
                }
                writer.flush();

                ////// FIN;

            } catch (IOException ex /*| InterruptedException ex*/) {
                LOG.log(Level.SEVERE, ex.getMessage());
            } finally {
                LOG.log(Level.INFO, "We are done. Cleaning up resources, closing streams and sockets...");
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                writer.close();
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    /**
     * A utility method to print server socket information
     *
     * @param serverSocket the socket that we want to log
     */
    private void logServerSocketAddress(ServerSocket serverSocket) {
        LOG.log(Level.INFO, "       Local IP address: {0}", new Object[]{serverSocket.getLocalSocketAddress()});
        LOG.log(Level.INFO, "             Local port: {0}", new Object[]{Integer.toString(serverSocket.getLocalPort())});
        LOG.log(Level.INFO, "               is bound: {0}", new Object[]{serverSocket.isBound()});
    }

    /**
     * A utility method to print socket information
     *
     * @param clientSocket the socket that we want to log
     */
    private void logSocketAddress(Socket clientSocket) {
        LOG.log(Level.INFO, "       Local IP address: {0}", new Object[]{clientSocket.getLocalAddress()});
        LOG.log(Level.INFO, "             Local port: {0}", new Object[]{Integer.toString(clientSocket.getLocalPort())});
        LOG.log(Level.INFO, "  Remote Socket address: {0}", new Object[]{clientSocket.getRemoteSocketAddress()});
        LOG.log(Level.INFO, "            Remote port: {0}", new Object[]{Integer.toString(clientSocket.getPort())});
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");

        Server server = new Server();
        server.start();
    }

}
