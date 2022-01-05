package owlngo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main server class that offers a socket to connect a client. This code is derived from the PEEGS
 * Task3 (highlowcardgame). When connected a thread is opend and all further handling is deligated
 * to {@link ScConnection}.
 */
public class Server {

  private static final int DEFAULT_PORT = 4441;

  /**
   * Main method for the server.
   *
   * @param args Commandline arguments
   */
  public static void main(final String[] args) {
    int port = DEFAULT_PORT;
    for (int i = 0; i < args.length; ++i) {
      switch (args[i]) {
        case "--port":
          if (isLastArgument(i, args)) {
            printErrorMessage("Please specify the port number.");
            return;
          }
          try {
            i++;
            port = Integer.parseInt(args[i]);
          } catch (NumberFormatException e) {
            printErrorMessage("Invalid port number: " + args[i]);
            return;
          }
          if (!isValidPort(port)) {
            printErrorMessage("The port number should be in the range of 1024~65535.");
            return;
          }
          break;
        case "--help":
        default:
          printHelpMessage();
          return;
      }
    }

    try (ServerSocket socket = new ServerSocket(port)) {
      Server server = new Server();
      server.start(socket);
    } catch (IOException e) {
      System.out.println("Connection lost. Shutting down: " + e.getMessage());
    }
  }

  private static boolean isLastArgument(int i, final String[] args) {
    return i == args.length - 1;
  }

  private static boolean isValidPort(int port) {
    return port >= 1024 && port <= 65535;
  }

  private static void printHelpMessage() {
    System.out.println("java Server [--port <int>] [--help]");
  }

  private static void printErrorMessage(String str) {
    System.out.println("Error! " + str);
  }

  /**
   * Listens to a socket for establishing a client-server-connection and handling this.
   *
   * @param socket is the socket, the server listens to
   * @throws IOException is thrown, when something goes wrong with the connection via the socket
   */
  public void start(ServerSocket socket) throws IOException {
    int count = 0;
    System.out.println("Server is Started ....");

    while (count != -1) {
      count++;
      Socket acceptedSocket = socket.accept();
      ScConnection scc = new ScConnection(acceptedSocket);
      new Thread(scc).start();
    }
  }
}
