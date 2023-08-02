package com.mcachedb.mcachedbconsole;

import com.mcachedb.mcachedbconsole.Request.GetDBsList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import com.fasterxml.jackson.databind.ObjectMapper ;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

public class Homepage {

    HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    int portString = 8080 ;

    @FXML
    private ListView<String> dbList;

    @FXML
    private TextField newDbName;

    @FXML
    void createDb(ActionEvent event) {
        HttpResponse<String> res ;
        HttpRequest r = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create("http://localhost:"+portString+"/db/"+newDbName.getText()))
                .build();

        try{
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200){
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
            alert.setContentText("Not able to connect to port " + portString + "\n" + "MCacheDB might be running on a different port number :\\");
            alert.show();
        }
    }

    @FXML
    void refreshDbList(ActionEvent event) {
        HttpResponse<String> res ;
        HttpRequest r = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:"+portString+"/dbs"))
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
//                ArrayList<String> myList = new ArrayList<String>(Arrays.asList(arr));
//                GetDBsList dBsList = mapper.readValue(res.body(), GetDBsList.class);
                ObservableList<String> defarr = FXCollections.observableList(new ArrayList<>());
                dbList.setItems(defarr);
//                ObservableList<String> oList = FXCollections.observableArrayList(a);
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
            alert.setContentText("Not able to connect to port " + portString + "\n" + "MCacheDB might be running on a different port number :\\");
            alert.show();
        }

    }

}