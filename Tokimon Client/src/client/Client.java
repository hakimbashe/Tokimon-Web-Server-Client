package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Hakim Bashe 301390647 hbashe@sfu.ca
 * CMPT 213 Assignment 5
 * Client Class
 * This class allows the user to interact with the server through a visual interface
 * It lets the user choose from 6 buttons, allow them to post data get data, or have the tokis visually displayed
 */
public class Client extends Application {
    public static void main(String[] args) {
        // Launch the application.
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /************************** BUTTONS GET DISPLACED WHEN FIRST 2 OPTIONS ARE SELECTED, PLEASE REFER TO CODE FOR THE NAMES, BUTTONS ARE STILL FUNCTIONAL *****/
        Label vbox = new Label();
        //Different button options
        Button submit = new Button("  List all Tokis  ");
        Button submit2 = new Button("  Get a specific Toki  ");
        Button submit3 = new Button("  Add new Toki  ");
        Button submit4 = new Button("  Edit a Toki  ");
        Button submit5 = new Button("  Delete a Toki  ");
        Button submit6 = new Button("  Display all Tokis  ");
        GridPane gridpane = new GridPane();
        //Add them all to the grid
        gridpane.add(submit, 1, 7);
        gridpane.add(submit2, 2, 7);
        gridpane.add(submit3, 3, 7);
        gridpane.add(submit4, 4, 7);
        gridpane.add(submit5, 5, 7);
        gridpane.add(submit6, 6, 7);
        gridpane.setVgap(20);
        gridpane.setHgap(20);
        gridpane.setAlignment(Pos.CENTER);

        //There are 6 different event handlers, some of the code is partially based on the demo code from the lecture on december 4th

        //Event handler one, list all tokis
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    //This gets all tokis in a string from the server
                    URL url = new URL("http://localhost:8080/tokimon/all");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    //Read everything in a string
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    System.out.println(connection.getResponseCode());
                    String result = "List of Tokis: ";
                    String output;
                    //Print it out
                    while ((output = br.readLine()) != null) {
                        result += output;
                    }
                    Label everything = new Label();
                    everything.setText(result);
                    gridpane.add(everything,1,8);
                    gridpane.setAlignment(Pos.CENTER);
                    connection.disconnect();
                } catch (IOException e) {
                }
            }
        });

        //Event handler 2, get a specific tokimon
        submit2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Create a new page
                Stage idPrompt = new Stage();
                GridPane tempGrid = new GridPane();
                //Ask for id
                Label idLabel = new Label("Please enter the Id of a specific Tokimon (Numbers only): ");
                idLabel.setPrefWidth(300);
                TextField idField = new TextField();
                Button idSubmit = new Button("  Submit  ");
                tempGrid.add(idLabel, 0, 0);
                tempGrid.add(idField, 1, 0);
                tempGrid.add(idSubmit, 2, 0);
                Scene newScene = new Scene(tempGrid, 600, 600); // (parent, hor, vert)
                idPrompt.setScene(newScene);
                idPrompt.setTitle("Tokimon Id Prompt");
                idPrompt.show();
                //Once submitted
                idSubmit.setOnAction(actionEvent1 -> {
                    try {
                        String id = idField.getText();
                        //Get the value
                        URL url = new URL("http://localhost:8080/tokimon/" + id);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.connect();

                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        System.out.println(connection.getResponseCode());
                        String result = "Specific Toki: ";
                        String output;
                        //Print val
                        while ((output = br.readLine()) != null) {
                            result += output;
                        }
                        Label specificVal = new Label();
                        specificVal.setText(result);
                        gridpane.add(specificVal,1,9);
                        gridpane.setAlignment(Pos.CENTER);
                        connection.disconnect();
                        idPrompt.close();
                    } catch (IOException e) {
                    }
                });
            }
        });

        //Event Handler 3, add a tokimon
        submit3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    //Ping the server to post
                    URL url = new URL("http://localhost:8080/tokimon/add");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");
                    OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                    //Create a new stage
                    Stage newStage = new Stage();
                    newStage.setTitle("Add a Tokimon (Fill out all fields then submit");
                    GridPane tempGrid = new GridPane();
                    //Add all the labels and textfields to fill in the data for a new tokimon
                    Label nameLabel = new Label("Name: ");
                    nameLabel.setPrefWidth(100);
                    nameLabel.setPadding(new Insets(0, 0, 0, 30));
                    Label weightLabel = new Label("Weight: ");
                    weightLabel.setPadding(new Insets(0, 0, 0, 30));
                    Label heightLabel = new Label("Height: ");
                    heightLabel.setPadding(new Insets(0, 0, 0, 30));
                    Label abilityLabel = new Label("Ability: ");
                    abilityLabel.setPadding(new Insets(0, 0, 0, 30));
                    Label strengthLabel = new Label("Strength: ");
                    strengthLabel.setPadding(new Insets(0, 0, 0, 30));
                    Label colourLabel = new Label("Colour: ");
                    colourLabel.setPadding(new Insets(0, 0, 0, 30));
                    TextField nameField = new TextField();
                    TextField weightField = new TextField();
                    TextField heightField = new TextField();
                    TextField abilityField = new TextField();
                    TextField strengthField = new TextField();
                    TextField colourField = new TextField();
                    Button addSubmit = new Button("  Submit  ");
                    tempGrid.add(nameLabel, 0, 0);
                    tempGrid.add(weightLabel, 0, 1);
                    tempGrid.add(heightLabel, 0, 2);
                    tempGrid.add(abilityLabel, 0, 3);
                    tempGrid.add(strengthLabel, 0, 4);
                    tempGrid.add(colourLabel, 0, 5);
                    tempGrid.add(nameField, 1, 0);
                    tempGrid.add(weightField, 1, 1);
                    tempGrid.add(heightField, 1, 2);
                    tempGrid.add(abilityField, 1, 3);
                    tempGrid.add(strengthField, 1, 4);
                    tempGrid.add(colourField, 1, 5);
                    tempGrid.add(addSubmit, 0, 6);
                    tempGrid.setAlignment(Pos.CENTER);
                    tempGrid.setVgap(20);
                    tempGrid.setHgap(20);
                    Scene newScene = new Scene(tempGrid, 800, 800);
                    newStage.setScene(newScene);
                    newStage.show();
                    //On submit
                    addSubmit.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {

                                //Write out the new data to the json
                                wr.write("{" +
                                        "\"name\":" + "\"" + nameField.getText() + "\"" + "," +
                                        "\"weight\":" + weightField.getText() + "," +
                                        "\"height\":" + heightField.getText() + "," +
                                        "\"ability\":" + "\"" + abilityField.getText() + "\"" + "," +
                                        "\"strength\":" + strengthField.getText() + "," +
                                        "\"colour\":" + "\"" + colourField.getText() + "\"" + "}"
                                );
                                wr.flush();
                                wr.close();
                                connection.connect();
                                System.out.println(connection.getResponseCode());
                                connection.disconnect();
                                newStage.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    //Close the connection
                } catch (IOException ignored) {

                }


            }
        });

        //Event handler 4, replace a tokimon
        submit4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Create a new stage
                Stage newStage = new Stage();
                newStage.setTitle("Replace a Tokimon (Type in the Id you would like to replace, then add the new fields!");
                GridPane tempGrid = new GridPane();
                //Prompt for id and toki categories
                Label idLabel = new Label("Id: ");
                idLabel.setPrefWidth(100);
                idLabel.setPadding(new Insets(0, 0, 0, 30));
                Label nameLabel = new Label("Name: ");
                nameLabel.setPrefWidth(100);
                nameLabel.setPadding(new Insets(0, 0, 0, 30));
                Label weightLabel = new Label("Weight: ");
                weightLabel.setPrefWidth(100);
                weightLabel.setPadding(new Insets(0, 0, 0, 30));
                Label heightLabel = new Label("Height: ");
                heightLabel.setPrefWidth(100);
                heightLabel.setPadding(new Insets(0, 0, 0, 30));
                Label abilityLabel = new Label("Ability: ");
                abilityLabel.setPrefWidth(100);
                abilityLabel.setPadding(new Insets(0, 0, 0, 30));
                Label strengthLabel = new Label("Strength: ");
                strengthLabel.setPrefWidth(100);
                strengthLabel.setPadding(new Insets(0, 0, 0, 30));
                Label colourLabel = new Label("Colour: ");
                colourLabel.setPrefWidth(100);
                colourLabel.setPadding(new Insets(0, 0, 0, 30));
                TextField idField = new TextField();
                TextField nameField = new TextField();
                TextField weightField = new TextField();
                TextField heightField = new TextField();
                TextField abilityField = new TextField();
                TextField strengthField = new TextField();
                TextField colourField = new TextField();
                Button addSubmit = new Button("  Submit  ");
                tempGrid.add(idLabel, 0, 0);
                tempGrid.add(nameLabel, 0, 1);
                tempGrid.add(weightLabel, 0, 2);
                tempGrid.add(heightLabel, 0, 3);
                tempGrid.add(abilityLabel, 0, 4);
                tempGrid.add(strengthLabel, 0, 5);
                tempGrid.add(colourLabel, 0, 6);
                tempGrid.add(idField, 1, 0);
                tempGrid.add(nameField, 1, 1);
                tempGrid.add(weightField, 1, 2);
                tempGrid.add(heightField, 1, 3);
                tempGrid.add(abilityField, 1, 4);
                tempGrid.add(strengthField, 1, 5);
                tempGrid.add(colourField, 1, 6);
                tempGrid.add(addSubmit, 0, 7);
                tempGrid.setAlignment(Pos.CENTER);
                tempGrid.setVgap(20);
                tempGrid.setHgap(20);
                Scene newScene = new Scene(tempGrid, 800, 800);
                newStage.setScene(newScene);
                newStage.show();

                //On Submit
                addSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        try {
                            //Upload the data to the server
                            URL url = new URL("http://localhost:8080/tokimon/change/" + idField.getText());
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setDoOutput(true);
                            connection.setRequestProperty("Content-Type", "application/json");
                            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                            //Write the data
                            wr.write("{" +
                                    "\"name\":" + "\"" + nameField.getText() + "\"" + "," +
                                    "\"weight\":" + weightField.getText() + "," +
                                    "\"height\":" + heightField.getText() + "," +
                                    "\"ability\":" + "\"" + abilityField.getText() + "\"" + "," +
                                    "\"strength\":" + strengthField.getText() + "," +
                                    "\"colour\":" + "\"" + colourField.getText() + "\"" + "}"
                            );
                            wr.flush();
                            wr.close();
                            connection.connect();
                            System.out.println(connection.getResponseCode());
                            connection.disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        newStage.close();
                    }
                });
            }
        });

        //Event handler 5, delete a toki
        submit5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Create a new stage
                Stage idPrompt = new Stage();
                GridPane tempGrid = new GridPane();
                Label idLabel = new Label("Please enter the Id of the Tokimon you would like to delete(Numbers only): ");
                idLabel.setPrefWidth(300);
                TextField idField = new TextField();
                Button idSubmit = new Button("  Submit  ");
                tempGrid.add(idLabel, 0, 0);
                tempGrid.add(idField, 1, 0);
                tempGrid.add(idSubmit, 2, 0);
                Scene newScene = new Scene(tempGrid, 800, 800);
                idPrompt.setScene(newScene);
                idPrompt.setTitle("Delete a Tokimon");
                idPrompt.show();
                tempGrid.setAlignment(Pos.CENTER);
                tempGrid.setVgap(20);
                tempGrid.setHgap(20);
                idSubmit.setOnAction(actionEvent1 -> {
                    try {
                        //Delete the data from the server
                        URL url = new URL("http://localhost:8080/tokimon/" + idField.getText());
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("DELETE");
                        connection.connect();
                        Label deleteLabel = new Label();
                        deleteLabel.setText("Tokimon id: " + idField.getText() + " was deleted");
                        deleteLabel.setAlignment(Pos.CENTER);
                        System.out.println(connection.getResponseCode());
                        connection.disconnect();
                        idPrompt.close();
                    } catch (IOException e) {
                    }
                });
            }
        });

        //Event handler 6, display all tokis visually
        submit6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    //get all the tokis from the server
                    URL url = new URL("http://localhost:8080/tokimon/all");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    System.out.println(connection.getResponseCode());
                    String result = "";
                    String output = "";

                    while ((result = br.readLine()) != null) {
                        output += result;
                    }
                    ArrayList<Display> data = new ArrayList<>();
                    String temp = "";
                    String words1 = "";
                    String words2 = "";
                    String words3 = "";
                    //System.out.println(output.length());
                    if (output.length() > 2) {
                        //Loop through all the data char by char
                        for (int i = 7; i < output.length(); i++) {
                            temp += output.charAt(i);

                            //If the word name is seen, grab the name
                            if (output.charAt(i) == ':' && output.charAt(i - 1) == '"' && output.charAt(i - 2) == 'e') {
                                i++;
                                for (int j = i; j < output.length(); j++) {
                                    if (output.charAt(j) == ',') {
                                        break;
                                    }
                                    words1 += output.charAt(j);

                                }
                            }

                            //If the word weight is seen, grab the weight
                            if (output.charAt(i) == ':' && output.charAt(i - 1) == '"' && output.charAt(i - 7) == 'w') {
                                i++;
                                for (int j = i; j < output.length(); j++) {
                                    if (output.charAt(j) == ',') {
                                        break;
                                    }
                                    words2 += output.charAt(j);
                                }
                            }

                            //If the word height is seen, grab the height
                            if (output.charAt(i) == ':' && output.charAt(i - 1) == '"' && output.charAt(i - 7) == 'h') {
                                i++;
                                for (int j = i; j < output.length(); j++) {
                                    if (output.charAt(j) == ',') {
                                        break;
                                    }
                                    words3 += output.charAt(j);
                                }
                            }

                            //Add the weight name and height when the end of each toki is seen, the curly brace shows end of a toki
                            if (output.charAt(i) == '}') {
                                //Add them to the array
                                Display newDisplayObj = new Display(words1, Double.parseDouble(words2), Double.parseDouble(words3));
                                data.add(newDisplayObj);
                                words1 = words2 = words3 = "";
                                temp = "";
                            }
                        }

                        //Create a new window
                        Stage idPrompt = new Stage();
                        GridPane tempGrid = new GridPane();
                        for (int i = 0; i < data.size(); i++) {
                            //Create a rectangle the size of each toki, width = width and height=height
                            Rectangle newRect = new Rectangle();
                            newRect.setWidth(data.get(i).getWeight());
                            newRect.setHeight(data.get(i).getHeight());
                            tempGrid.add(newRect, i, 0);
                            tempGrid.setVgap(20);
                            tempGrid.setHgap(20);
                            //Add the toki name
                            Label recLabel = new Label(data.get(i).getName());
                            tempGrid.add(recLabel, i, 1);
                            tempGrid.setAlignment(Pos.CENTER);
                        }
                        Scene newScene = new Scene(tempGrid, 800, 800);
                        idPrompt.setScene(newScene);
                        idPrompt.setTitle("Display all Tokimons");
                        idPrompt.show();
                        connection.disconnect();
                    }
                } catch (IOException e) {
                }
            }
        });

        //Print out the client
        VBox temp = new VBox(200, gridpane, vbox);
        Scene scene = new Scene(temp, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tokimon User Interface");
        primaryStage.show();
    }
}