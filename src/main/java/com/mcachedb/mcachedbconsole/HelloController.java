package com.mcachedb.mcachedbconsole;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mcachedb.mcachedbconsole.System.Application;
import com.mcachedb.mcachedbconsole.System.Info;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import java.net.InetAddress;

public class HelloController implements Initializable {
    BufferedReader in = null;
    String ipAddress = "";
    HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
    @FXML
    private Button checkConBtn;

    @FXML
    private TextField hostStringTF;

    @FXML
    private Label ipaddress4;
    @FXML
    private Label welcomeText;

    @FXML
    private TextField portTextField;

    @FXML
    void serverStart(ActionEvent event) {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("Port:");
            dialog.setTitle("Enter Port Number");
            dialog.showAndWait().ifPresent(string -> {
                try {
                    if(Integer.parseInt(string) > 1024){
                        System.out.println(string);

                        Properties prop = new Properties();
                        OutputStream output = new FileOutputStream("application.properties");
                        InputStream input = new FileInputStream("application.properties");

                        String pwd = System.getProperty("user.dir") ;

                        prop.load(input);
                        prop.setProperty("server.port",string);
                        String ver = prop.getProperty("ver");
                        prop.store(output, null);


                        Process process = Runtime.getRuntime().exec( "cmd.exe /C start "+pwd+"\\MCacheDB"+ver+".exe " );
                        portTextField.setText(string);
                    }
                }catch (Exception e){
                    System.out.println(e);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Something went wrong");
                    alert.show();

                }
            });


    }

    String getIP(){
        try{
            InetAddress localhost = InetAddress.getLocalHost();
            return (localhost.getHostAddress()).trim();
        }catch (Exception e){
            return "127.0.0.1";
        }
    }

    @FXML
    void checkConnection(ActionEvent event) throws IOException, InterruptedException {

        Scene scene = checkConBtn.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;


        String portString = portTextField.getText();
        String hostString = hostStringTF.getText();
        HttpResponse<String> res ;
        HttpRequest r = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://"+hostString+":"+portString+"/dbs"))
                .build();

        try{
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Connection Established !");
                alert.show();

                Parent root = FXMLLoader.load(HelloApplication.class.getResource("homepage.fxml"));
                Scene scene1 = new Scene(root);
                Info inf = Info.Info();
                inf.setPort(Integer.parseInt(portString));
                inf.setHostAddress(hostString);
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
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ipaddress4.setText(getIP());
    }
}