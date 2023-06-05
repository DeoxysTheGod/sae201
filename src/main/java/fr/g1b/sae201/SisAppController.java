package fr.g1b.sae201;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SisAppController {
    @FXML
    private Pane hiddenMenuContainer = new Pane();

    private boolean booleanMenu;


    public SisAppController() {
        booleanMenu = false;
    }

    public void initialize() {
        System.out.println("App launched successfuly !");
    }

    public void hideAndShowMenu() {
        booleanMenu = booleanMenu ? false : true;
        hiddenMenuContainer.setManaged(booleanMenu);
        hiddenMenuContainer.setVisible(booleanMenu);
    }
}
