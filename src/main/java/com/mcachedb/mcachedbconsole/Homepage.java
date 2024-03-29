package com.mcachedb.mcachedbconsole;

import com.mcachedb.mcachedbconsole.Request.GetDBsList;
import com.mcachedb.mcachedbconsole.System.Info;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import com.fasterxml.jackson.databind.ObjectMapper ;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Homepage {

    HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @FXML
    private ListView<String> dbList;


    @FXML
    private Label dbnameDisplay;

    @FXML
    private Button ProceedBtn;

    @FXML
    private TextField newDbName;

    public static Info inf = Info.Info() ;


    @FXML
    void restoreBackup(ActionEvent event) {
        HttpResponse<String> res ;
        HttpRequest r = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://"+inf.getHostAddress()+":"+inf.getPort()+"/restore/db"))
                .build();

        try{
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200){
                refreshDbList(event);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Database Restored Successfully");
                alert.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Something went wrong, MCacheDB is not responding");
                alert.show();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.toString());
            alert.setContentText("Not able to connect to port " + inf.getPort() + "\n" + "MCacheDB might be running on a different port number :\\");
            alert.show();
        }
    }


    @FXML
    void createDb(ActionEvent event) {

        HttpResponse<String> res ;
        HttpRequest r = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create("http://"+inf.getHostAddress()+":"+inf.getPort()+"/db/"+newDbName.getText()))
                .build();

        try{
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200){
                newDbName.setText("");
                refreshDbList(event);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("New Database Created !");
                alert.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Something went wrong");
                alert.show();

            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Not able to connect to port " + inf.getPort() + "\n" + "MCacheDB might be running on a different port number :\\");
            alert.show();
        }
    }

    @FXML
    void goBackToHome(ActionEvent event) {
        Scene scene = dbnameDisplay.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        Parent root = null;

        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        stage.show();
    }

    @FXML
    void refreshDbList(ActionEvent event) {

        HttpResponse<String> res ;
        HttpRequest r = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://"+inf.getHostAddress()+":"+inf.getPort()+"/dbs"))
                .build();

        try{
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200){
                ObjectMapper mapper = new ObjectMapper();
                System.out.println(res.body());
                String a = res.body() ;
                a = a.replace("[","");
                a = a.replace("]","");
                String[] arr = a.split(",");
                ObservableList<String> defarr = FXCollections.observableList(new ArrayList<>());
                dbList.setItems(defarr);
                for (int i = 0; i < arr.length; i++) {
                    dbList.getItems().add(arr[i].replace("\"",""));
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Something went wrong, MCacheDB is not responding");
                alert.show();

            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.toString());
            alert.setContentText("Not able to connect to port " + inf.getPort() + "\n" + "MCacheDB might be running on a different port number :\\");
            alert.show();
        }

    }

    @FXML
    void selectDbAndProceed(ActionEvent event) {

        if(dbnameDisplay.getText().contains("Database")){
            Scene scene = dbnameDisplay.getScene();
            Window window = scene.getWindow();
            Stage stage = (Stage) window;
            Parent root = null;
            try {
                root = FXMLLoader.load(HelloApplication.class.getResource("buckethome.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.show();
        }

        String selectedDB = dbList.getSelectionModel().getSelectedItem();
        inf.setSelectedDB(selectedDB);
        ProceedBtn.setText("Proceed with : " + selectedDB);
        dbnameDisplay.setText("Database Selected: "+selectedDB);
    }

}