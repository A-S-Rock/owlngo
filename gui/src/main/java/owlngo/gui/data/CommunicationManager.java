package owlngo.gui.data;

import java.net.Socket;

/** Class storing the client socket for further use by JavaFX controllers. */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings({
  "EI_EXPOSE_REP2",
  "EI_EXPOSE_REP",
  "MS_EXPOSE_REP"
})
public final class CommunicationManager {

  private static CommunicationManager instance;
  private String username;
  private Socket socket;

  /**
   * Instantiates the single instance of the CommunicationManager or returns it if already existant.
   *
   * @return the CommunicationManager's instance
   */
  public static synchronized CommunicationManager getInstance() {
    if (instance == null) {
      instance = new CommunicationManager();
    }
    return instance;
  }

  /**
   * Returns the connected client's username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Returns the client's socket connection.
   *
   * @return the socket
   */
  public Socket getSocket() {
    return socket;
  }

  /**
   * Sets the connected client's username.
   *
   * @param username the username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Sets the connected client's socket.
   *
   * @param socket the client's socket
   */
  public void setSocket(Socket socket) {
    this.socket = socket;
  }
}
