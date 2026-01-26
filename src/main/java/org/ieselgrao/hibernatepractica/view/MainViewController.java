package org.ieselgrao.hibernatepractica.view;

import javafx.fxml.FXML;
import org.ieselgrao.hibernatepractica.UniGraoVerse;
import org.ieselgrao.hibernatepractica.model.DAOController;

import java.io.IOException;

public class MainViewController {

    @FXML
    public void newLoginMySQL() {
        try {
            DAOController.init("mysql-persistence-unit");
            UniGraoVerse.main.goScene("play");
        } catch (IOException e) {
            System.exit(1);
        }
    }

    @FXML
    public void newLoginSQLite() {
        try {
            DAOController.init("sqlite-persistence-unit");
            UniGraoVerse.main.goScene("play");
        } catch (IOException e) {
            System.exit(1);
        }
    }

    @FXML
    public void readCredits() {
        try {
            UniGraoVerse.main.goScene("credits");
        } catch (IOException e) {
            System.exit(1);
        }
    }
}