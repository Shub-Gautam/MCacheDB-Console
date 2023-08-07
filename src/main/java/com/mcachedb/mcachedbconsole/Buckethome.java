package com.mcachedb.mcachedbconsole;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mcachedb.mcachedbconsole.Request.DisplayRecord;
import com.mcachedb.mcachedbconsole.Request.GetAllKeyVal;
import com.mcachedb.mcachedbconsole.Request.GetBucketList;
import com.mcachedb.mcachedbconsole.Request.KeyVal;
import com.mcachedb.mcachedbconsole.System.Info;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Buckethome implements Initializable {

    static HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @FXML
    private Button addBktbh;

    @FXML
    private Button addRecordBtn;

    @FXML
    private Button adminbtnbh;

    @FXML
    private ListView<String> bucketListbh;

    @FXML
    private Label displaydblblbh;

    @FXML
    private TextField newRecKey;

    @FXML
    private TextField newRecVal;

    @FXML
    private TableView<DisplayRecord> keyValbh;

    @FXML
    private Button refreshBtnbh;


    @FXML
    private TableColumn<DisplayRecord, String> Value;


    @FXML
    private TableColumn<DisplayRecord, String> Key;


    @FXML
    private TextField newbktnamebh;

    List<String> bktList = null;

    public static Info inf = Info.Info() ;


    @FXML
    void addRecordIntoBucket(ActionEvent event) {
        String Key = newRecKey.getText();
        String Value = newRecVal.getText();
        HttpResponse<String> res ;

        Map<Object, Object> data = new HashMap<>();
        data.put("key", Key);
        data.put("value", Value);
        String bktName = bucketListbh.getSelectionModel().getSelectedItem();
        Gson gson = new Gson();
        HttpRequest r = HttpRequest.newBuilder()
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(data)))
                .uri(URI.create("http://localhost:"+inf.getPort()+"/db/"+inf.getSelectedDB()+"/bask/"+bktName+"/add"))
                .build();

        try{
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200){
                System.out.println(res.toString());
                newRecKey.setText("");
                newRecVal.setText("");
                refreshKV();
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
    void addBucket(ActionEvent event) {
        HttpResponse<String> res ;
        HttpRequest r = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create("http://localhost:"+inf.getPort()+"/bask/create/"+inf.getSelectedDB()+"/"+newbktnamebh.getText()))
                .build();

        try{
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200){
                System.out.println(res.toString());
                newbktnamebh.setText("");
                refreshBucketList(event);
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
    void displayKeyValueForBucket(MouseEvent event) {
        String bktName = bucketListbh.getSelectionModel().getSelectedItem();
        HttpResponse<String> res ;
        Gson gson = new Gson();


        HttpRequest r = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:"+inf.getPort()+"/db/"+inf.getSelectedDB()+"/bask/"+bucketListbh.getSelectionModel().getSelectedItem()+"/all"))
                .build();

        try{
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200){
                System.out.println("Executedd");
                GetAllKeyVal allKeyVal = gson.fromJson(res.body(), GetAllKeyVal.class);
                List<DisplayRecord> displayRecords = new ArrayList<>();
                for (KeyVal keyValList :
                        allKeyVal.getKeyValList()) {

                    DisplayRecord tep  = new DisplayRecord(keyValList.getRowId(),keyValList.getKeyValuesMap().getKey(),keyValList.getKeyValuesMap().getValue(),keyValList.getCreatedAt());
                    displayRecords.add(tep);
                }
                ObservableList<DisplayRecord> observableList = FXCollections.observableArrayList(displayRecords);
                keyValbh.setItems(observableList);
            }else {
                System.out.println(res.toString());
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
    void openAdminCenter(ActionEvent event) {
        Scene scene = adminbtnbh.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;

        Parent root = null;
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("admincenter.fxml"));
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void refreshKeyVal(ActionEvent event) {
        refreshKV();
    }

    @FXML
    void refreshBucketList(ActionEvent event) {
        fetchBkt(inf.getSelectedDB());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displaydblblbh.setText(inf.getSelectedDB());
        fetchBkt(inf.getSelectedDB());
        Key.setCellValueFactory(new PropertyValueFactory<DisplayRecord,String>("Key"));
        Value.setCellValueFactory(new PropertyValueFactory<DisplayRecord,String>("Value"));
    }

    public List<String> fetchBkt(String db){
        List<String> list = new ArrayList<>();
        HttpResponse<String> res ;
        HttpRequest r = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:"+inf.getPort()+"/bask/"+db))
                .build();

        try{
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200){
                ObjectMapper mapper = new ObjectMapper();
                Gson gson = new Gson();
                GetBucketList bucketList = gson.fromJson(res.body(),GetBucketList.class);
                System.out.println(bucketList.getBucketList());

                if(bucketList.getBucketList()!=null){
                    List<String> arr = bucketList.getBucketList();
                    ObservableList<String> defarr = FXCollections.observableList(new ArrayList<>());
                    bucketListbh.setItems(defarr);
                    for (int i = 0; i < arr.size(); i++) {
                        bucketListbh.getItems().add(arr.get(i));
                    }
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
        return list ;
    }



    void refreshKV(){
        String bktName = bucketListbh.getSelectionModel().getSelectedItem();
        HttpResponse<String> res ;
        Gson gson = new Gson();


        HttpRequest r = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:"+inf.getPort()+"/db/"+inf.getSelectedDB()+"/bask/"+bucketListbh.getSelectionModel().getSelectedItem()+"/all"))
                .build();

        try{
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200){
                System.out.println("Executedd");
                GetAllKeyVal allKeyVal = gson.fromJson(res.body(), GetAllKeyVal.class);
                List<DisplayRecord> displayRecords = new ArrayList<>();
                for (KeyVal keyValList :
                        allKeyVal.getKeyValList()) {

                    DisplayRecord tep  = new DisplayRecord(keyValList.getRowId(),keyValList.getKeyValuesMap().getKey(),keyValList.getKeyValuesMap().getValue(),keyValList.getCreatedAt());
                    displayRecords.add(tep);
                }
                ObservableList<DisplayRecord> observableList = FXCollections.observableArrayList(displayRecords);
                keyValbh.setItems(observableList);
            }else {
                System.out.println(res.toString());
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
