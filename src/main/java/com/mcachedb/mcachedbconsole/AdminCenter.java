package com.mcachedb.mcachedbconsole;

import com.mcachedb.mcachedbconsole.System.Info;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AdminCenter {

    static HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    @FXML
    private Button backBtn;

    public static Info inf = Info.Info() ;


    @FXML
    void backUpDB(ActionEvent event) {
        HttpResponse<String> res ;
        HttpRequest r = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://"+inf.getHostAddress()+":"+inf.getPort()+"/backup/db"))
                .build();

        try{
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Database Stored Successfully");
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
    void gotoBucketHome(ActionEvent event) {
        Scene scene = backBtn.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;

        Parent root = null;
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("buckethome.fxml"));
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void restoreDB(ActionEvent event) {
        HttpResponse<String> res ;
        HttpRequest r = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://"+inf.getHostAddress()+":"+inf.getPort()+"/restore/db"))
                .build();

        try{
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200){
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

}
