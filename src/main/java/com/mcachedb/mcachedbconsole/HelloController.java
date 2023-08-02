package com.mcachedb.mcachedbconsole;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HelloController {

    HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @FXML
    private Label welcomeText;
    @FXML
    private TextField portTextField;

    @FXML
    void checkConnection(ActionEvent event) throws IOException, InterruptedException {

        Scene scene = portTextField.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;


        String portString = portTextField.getText();
        HttpResponse<String> res ;
        HttpRequest r = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:"+portString+"/dbs"))
                .build();

        try{
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Connection Established !");
                alert.show();

                Parent root = FXMLLoader.load(HelloApplication.class.getResource("homepage.fxml"));
                Scene scene1 = new Scene(root);
                stage.setScene(scene1);
                stage.show();

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Something went wrong, MCacheDB is not responding");
                alert.show();

            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            System.out.println(e.toString());
//            alert.setContentText("Not able to connect to port " + portString + "\n" + "MCacheDB might be running on a different port number :\\");
            alert.show();
        }
    }
}