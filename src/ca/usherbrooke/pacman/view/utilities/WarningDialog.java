package ca.usherbrooke.pacman.view.utilities;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class WarningDialog {
  private WarningDialog() {

  }

  public static void displayWarningDialog(String warningMessage) {
    JOptionPane.showMessageDialog(null, warningMessage, "Error!", JOptionPane.ERROR_MESSAGE);
  }

  public static void display(String warningMessage, Exception e) {
    Logger.getGlobal().log(Level.WARNING, warningMessage, e);
    displayWarningDialog(warningMessage);
  }
}
