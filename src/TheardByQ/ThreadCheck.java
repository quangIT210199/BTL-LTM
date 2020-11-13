
package TheardByQ;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Original Thread
 * @author hoangpt
 */
public class ThreadCheck implements Runnable {

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      System.out.println("quang " + i);
      try {
        Thread.sleep(2000);
      } catch (InterruptedException ex) {
      }
    }
  }
}
