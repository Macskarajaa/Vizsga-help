https://github.com/vrolandd/javafx
% lekerdezes sqlben ES where join: app.get("/api/search/:nev",(req,resp)=>{
    const {nev} = req.params
    const sql = "SELECT kategoriak.nev as kategNev, aruk.* from aruk, kategoriak WHERE aruk.kategoriaId = kategoriak.id and aruk.nev  LIKE ?;"
    const values=[`%${nev}%`]
    db.query(sql,values,(err,res)=>{
       if(err){
            console.log(err);
            return resp.status(500).json({error:"Adatbázis hiba!"})
        }
        if(res.length==0){
            return resp.status(404).json({error:"Nincs ilyen virág!"})
        }
        return resp.status(200).send(res)
    })
})

PUT(en fele): app.put("/viragupdate",(req,resp)=>{
    const {szam,id} = req.body
    const sql = "UPDATE aruk set aruk.keszlet = ? WHERE id = ?;"
    const values = [szam,id]
    db.query(sql,values,(err,res)=>{
        if(err){
            console.log(err);
            return resp.status(500).json({error:"Adatbázis hiba!"})
        }
        if(res.affectedRows==0){
            return resp.status(400).json({error:"Nem létező ID!"})
        }else{
            return resp.status(200).send(res)
        }
    })
})

Random valami egy Listbol(Java)
Random random = new Random();
    Repulok randomRepulo = repulok.get(random.nextInt(repulok.size()));


ha minden adat kene a bal oldali listaba, nem csak mondjuk a neve vagy ilyesmi:

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

    // CHANGED: The left list view now holds the full Repulok objects
    @FXML
    public ListView<Repulok> leftListView; 
    
    // The right list view remains String to display the different stats lines
    @FXML
    public ListView<String> rightListView;
    
    @FXML
    public MenuItem Megnyitas;
    
    @FXML
    public BorderPane controller;

    private final FileChooser fc = new FileChooser();
    private final List<Repulok> repulokLista = new ArrayList<>();

    @FXML
    public void initialize() {
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "repulok.csv"));

        // Elegant fix: Automatically update details whenever an airplane is selected
        leftListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedPlane) -> {
            fillRight(selectedPlane);
        });
    }

    @FXML
    public void onMegnyitasClick(ActionEvent actionEvent) {
        File fbe = fc.showOpenDialog(controller.getScene().getWindow());

        if (fbe != null) {
            leftListView.getItems().clear();
            rightListView.getItems().clear();

            readData(fbe);
            System.out.println("beolvasott adatok: " + repulokLista.size());

            // FIX: Populating the left list with EVERY plane object loaded from the file
            leftListView.getItems().addAll(repulokLista);

            // Auto-select the first plane if the list isn't empty
            if (!leftListView.getItems().isEmpty()) {
                leftListView.getSelectionModel().select(0);
            }
        }
    }

    private void readData(File file) {
        repulokLista.clear();
        try (Scanner sc = new Scanner(file)) {
            if (sc.hasNextLine()) sc.nextLine(); // Skip CSV header

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

    // Overloaded/Adapted method to map out all properties of the selected plane
    private void fillRight(Repulok plane) {
        if (plane == null) {
            rightListView.getItems().clear();
            return;
        }

        // Create an explicit list of all internal specifications
        List<String> details = new ArrayList<>();
        details.add("Típus: " + plane.getTipus());
        details.add("Hossz: " + plane.getHossz() + " m");
        details.add("Súly: " + plane.getSuly() + " kg");
        details.add("Férőhelyek: " + plane.getFerohelyek());
        details.add("Üzemanyagtank: " + plane.getUzemanyagtank() + " L");

        // Push properties straight to the right list UI
        rightListView.setItems(FXCollections.observableList(details));
    }

    @FXML
    public void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Névjegy");
        alert.setHeaderText(null);
        alert.setContentText("Repülők v1.0.0\n(C) Kandó");
        alert.showAndWait();
    }
}


overrideolni kell hozza a toStringet is:
@Override
public String toString() {
    // Tell JavaFX to display the 'Tipus' property in the ListView
    return this.Tipus; 
}


mas modon a beolvasas:
if (fbe != null) {
            leftListView.getItems().clear();
            rightListView.getItems().clear();

            readData(fbe);
            System.out.println("beolvasott adatok: " + diafilmLista.size());

            // FIX: Populating the left list with EVERY plane object loaded from the file
            for (DiaFilmek film : diafilmLista) {
                leftListView.getItems().add(film.getCim());
            }
            // Auto-select the first plane if the list isn't empty
            if (!leftListView.getItems().isEmpty()) {
                leftListView.getSelectionModel().select(0);
            }
        }
    }
