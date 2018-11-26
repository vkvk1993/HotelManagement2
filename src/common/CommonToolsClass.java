package common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class CommonToolsClass {

  public static ChangeListener<String> getNumericFieldListener(TextField textField) {


    return new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observerable, String oldValue,
          String newValue) {

        if (!newValue.isEmpty()) {
          try {
            Double.parseDouble(newValue);
            textField.setText(newValue);
          } catch (Exception e) {
            textField.clear();
            textField.setText(oldValue);
          }
        }

      }
    };
  }

  public static String getDateYYYYMMDD() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    return dateFormat.format(date);
  }

  public static StringConverter<LocalDate> getDateYYYYMMDDStringConverter() {
    return new StringConverter<LocalDate>() {
      private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      @Override
      public String toString(LocalDate localDate) {
        if (localDate == null) return "";
        return dateTimeFormatter.format(localDate);
      }

      @Override
      public LocalDate fromString(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
          return null;
        }
        return LocalDate.parse(dateString, dateTimeFormatter);
      }
    };
  }
}
