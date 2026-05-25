package main.repulokgui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RepulokController {
    // public static ArrayList<TODO> TODOList = new ArrayList<>();


    @FXML
    public ListView<String> leftListView, rightListView;
    public MenuItem Megnyitas;
    public BorderPane controller;
    @FXML


    private FileChooser fc = new FileChooser();

    private List<Repulok> repulokLista = new ArrayList<>();

    public void initialize(){
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok","repulok.csv"));
    }

    @FXML
    public void onMegnyitasClick(ActionEvent actionEvent) {
        File fbe = fc.showOpenDialog(controller.getScene().getWindow());

        if (fbe != null) {
            // Clear lists before loading new data
            leftListView.getItems().clear();
            rightListView.getItems().clear();

            readData(fbe);
            System.out.println("beolvasott adatok: " + repulokLista.size());

            repulokLista.stream().map(repulok1 -> repulok1
                    .getTipus()
                    .split(" ")[0]).distinct().sorted()
                    .forEach(tipus -> leftListView.getItems().add(tipus));

            if (!leftListView.getItems().isEmpty()) {
                leftListView.getSelectionModel().select(0);
            }
        }
    }

    private void readData(File file) {
        repulokLista.clear();
        try (Scanner sc = new Scanner(file)) {
            // Skip header line if your CSV has one (uncomment if needed)
            if(sc.hasNextLine()) sc.nextLine();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!line.trim().isEmpty()) {
                    repulokLista.add(new Repulok(line));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("A fájl nem található: " + e.getMessage());
        }
    }

    @FXML
    public void exitPlatform() {
        Platform.exit();
    }

    @FXML
    public void fillLeft() {

    }

    @FXML
    public void fillRight() {
        rightListView.setItems(FXCollections.observableList(repulokLista.stream()
                .map(Repulok::getTipus).filter(tipus -> tipus.split(" ")[0].equals(leftListView.getSelectionModel().getSelectedItem())).toList()
        ));

    }

    @FXML
    public void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Névjegy");
        alert.setHeaderText("");
        alert.setContentText("Repülők v1.0.0\n(C) Kandó");
        alert.showAndWait();
    }


}