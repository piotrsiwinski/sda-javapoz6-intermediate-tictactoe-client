package pl.sda.poznan.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import pl.sda.poznan.viewmodel.ConnectionDialogViewModel;

public class MainWindowController {

  private final Logger logger = Logger.getLogger(getClass().getName());

  public void handleClick(MouseEvent mouseEvent) {
    Label source = (Label) mouseEvent.getSource();
    System.out.println(source.getId());
  }

  /**
   * Metoda wykonujaca sie przy klinieciu przycisku polacz do serwera z Menu Game
   */
  public void connectToServer(ActionEvent actionEvent) {
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("ConnectionDialogWindow.fxml"));

    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Połącz do serwera");
    dialog.setHeaderText("Uzupelnij dane");

    try {
      dialog.getDialogPane().setContent(fxmlLoader.load());
    } catch (IOException e) {
      return;
    }
    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

    Optional<ButtonType> optionalType = dialog.showAndWait();
    optionalType.ifPresent(buttonType -> {
      ConnectionDialogController controller = fxmlLoader.getController();
      ConnectionDialogViewModel connectionDetails = controller.getConnectionDetails();
      connectToServer(connectionDetails);
    });
  }

  public void connectToServer(ConnectionDialogViewModel viewModel) {
    logger.log(Level.INFO, String.format(
        "Trying to connect to server at address %s with username %s", viewModel.getServerAddress(),
        viewModel.getPlayerName()));
  }

}
