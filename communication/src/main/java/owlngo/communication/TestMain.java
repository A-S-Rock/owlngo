package owlngo.communication;

import owlngo.communication.messages.ConnectionRequest;

/** Mock tests for fast testing. Later on, JUnit should be used for this. */
public class TestMain {

  public static void main(String[] args) {
    System.out.println("Testing client messages.");

    final ConnectionRequest connectionRequest = new ConnectionRequest("PLAYER");
    final String connectionRequestJson = MessageCoder.encodeToJson(connectionRequest);

    System.out.println(connectionRequestJson);
  }
}
