package common;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class CommonToolsClass {

  public static ChangeListener<String> getNumericFieldListener(TextField textField) {


    return new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observerable, String oldValue,
          String newValue) {
        if (!newValue.matches("\\d*")) {
          textField.setText(newValue.replaceAll("[^\\d]", ""));
        }

      }
    };
  }
}
