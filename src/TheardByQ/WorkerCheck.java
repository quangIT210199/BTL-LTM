package TheardByQ;

import java.util.List;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

/**
 * @author hoangpt
 */
public class WorkerCheck extends SwingWorker<Integer, String> {

  private javax.swing.JLabel txtInfo;

  public WorkerCheck(JLabel txtInfo) {
    this.txtInfo = txtInfo;
  }

  @Override
  protected Integer doInBackground() throws Exception {
    //start
    publish("Start");
    setProgress(1);

    //do count
    for (int i = 0; i < 20; i++) {
      System.out.println(i);
      Thread.sleep(1000);
      publish("Restarting in " + (20 - i) + "s...");
    }

    publish("Complete");
    setProgress(100);

    return 1;
  }

  @Override
  protected void process(List< String> chunks) {
    for (final String string : chunks) {
      txtInfo.setText(string);
    }
  }
}
