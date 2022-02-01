package owlngo.gui.data;

import owlngo.communication.Connection;

/** Class storing the client socket for further use by JavaFX controllers. */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings({
  "EI_EXPOSE_REP2",
  "EI_EXPOSE_REP",
  "MS_EXPOSE_REP"
})
public final class CommunicationManager {

  private static CommunicationManager instance;
  private String username;
  private Connection connection;

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
   * Sets the connected client's username.
   *
   * @param username the username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns the connection from the client.
   *
   * @return the connection
   */
  public Connection getConnection() {
    return connection;
  }

  /**
   * Sets the connection of the client.
   *
   * @param connection the connection
   */
  public void setConnection(Connection connection) {
    this.connection = connection;
  }
}
