package owlngo.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.application.Application;
import owlngo.communication.Connection;
import owlngo.gui.data.CommunicationManager;
import owlngo.gui.gamefield.WelcomeScreen;

/**
 * Network client for the owlngo game. The connection-establishing code was derived from PEEGS-Task3
 * (highlowcardgame).
 */
public final class Client {

  private static final int USERNAME_MAX_LENGTH = 50;
  private static final int DEFAULT_PORT = 4441;
  private static final String DEFAULT_ADDRESS = "localhost";
  private static final String DEFAULT_USERNAME = System.getProperty("user.name");

  /**
   * Entry to <code>Client</code>.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    // Parse arguments
    String username = DEFAULT_USERNAME;
    String serverAddress = DEFAULT_ADDRESS;
    int port = DEFAULT_PORT;
    for (int i = 0; i < args.length; ++i) {
      switch (args[i]) {
        case "--username":
          if (isLastArgument(i, args)) {
            printErrorMessage("Please specify the username.");
            return;
          }
          ++i;
          username = args[i];
          if (username.length() > USERNAME_MAX_LENGTH) {
            printErrorMessage("Username too long! Please choose a shorter one.");
            return;
          }
          break;
        case "--address":
          if (isLastArgument(i, args)) {
            printErrorMessage("Please specify the server address.");
            return;
          }
          ++i;
          serverAddress = args[i];
          break;
        case "--port":
          if (isLastArgument(i, args)) {
            printErrorMessage("Please specify the port number.");
            return;
          }
          try {
            ++i;
            port = Integer.parseInt(args[i]);
          } catch (NumberFormatException e) {
            printErrorMessage("Invalid port number: " + args[i]);
            return;
          }
          break;
        case "--help":
          printHelpMessage();
          break;
        default:
          printHelpMessage();
          return;
      }
    }

    // Check validity
    if (!isValidName(username)) {
      printErrorMessage("Invalid username: " + username);
      return;
    }

    InetAddress inetAddress; // = null;
    try {
      inetAddress = InetAddress.getByName(serverAddress);
    } catch (UnknownHostException e) {
      printErrorMessage("Invalid server address: " + serverAddress);
      return;
    }
    assert inetAddress != null;

    if (!isValidPort(port)) {
      printErrorMessage("The port number should be in the range of 1024~65535.");
      return;
    }

    // Start a client
    InetSocketAddress address = new InetSocketAddress(inetAddress, port);

    Client client = new Client();
    try (Socket socket = new Socket(address.getAddress(), address.getPort())) {
      client.start(username, socket);
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

  private static boolean isValidName(String username) {
    return username != null && !username.isBlank();
  }

  public static void printHelpMessage() {
    System.out.println(
        "java Client [--username <String>] [--address <String>] [--port <int>] [--help]");
  }

  private static void printErrorMessage(String str) {
    System.out.println("Error! " + str);
  }

  /**
   * This start method handles all the incoming and outgoing messages to/from the corresponding
   * SCConnection class.
   *
   * @param username is the name for the registered user of this client
   * @param socket is the socket (of the server), the client connects to
   * @throws IOException collecting all problems when receiving/sending a message
   */
  public void start(String username, Socket socket) throws IOException {

    CommunicationManager.getInstance().setUsername(username);
    CommunicationManager.getInstance()
        .setConnection(new Connection(socket.getOutputStream(), socket.getInputStream()));
    // starting GUI
    Application.launch(WelcomeScreen.class);
  }
}
