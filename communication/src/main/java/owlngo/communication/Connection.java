package owlngo.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import owlngo.communication.messages.Message;

/**
 * Contains a socket's streams. The class serves as a container for 'interfaces' to an established
 * connection. Taken and adjusted from Task 3.
 */
public final class Connection implements AutoCloseable {
  private final PrintStream writer;
  private final BufferedReader reader;

  /**
   * Creates a new Connection object holding the given streams.
   *
   * @param outputStream The output stream of the socket
   * @param inputStream The input stream of the socket
   */
  public Connection(OutputStream outputStream, InputStream inputStream) {
    writer = new PrintStream(outputStream, true, StandardCharsets.UTF_8);
    reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
  }

  /**
   * Reads a message on the socket stream.
   *
   * @return The message
   * @throws IOException If there are connection errors.
   */
  public Message read() throws IOException {
    synchronized (reader) {
      String input = reader.readLine();
      if (input == null) {
        throw new IOException("Cannot read from input stream");
      }
      return MessageCoder.decodeFromJson(input);
    }
  }

  /**
   * Writes a message on the socket stream.
   *
   * @param message The message to write
   */
  public void write(Message message) {
    synchronized (writer) {
      writer.println(MessageCoder.encodeToJson(message));
    }
  }

  @Override
  public void close() {
    try {
      writer.close();
      reader.close();
    } catch (IOException e) {
      // When this try fails, there is nothing else we can do.
      System.err.println("Could not close connection");
      e.printStackTrace();
    }
  }
}
