package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;

public class Controller {
    @FXML
    Button searchButton, calculateAnswer;
    @FXML
    TextField searchField;
    @FXML
    TextArea textArea;
    @FXML
    TextField rpmField, cidField, cfmField, answerField;

    //FileReading To TextArea Path and file Instantiated, Initialized on line 49-50 in getTextFromEvent() method
    String fileName;
    String pathName = "C:\\Users\\f22b2\\IdeaProjects\\Toyota_Top_Repair_App\\src\\sample\\TSBs and Repair Info\\";

    //Button from TSB Search Tab
    public void setSearchButton(Button searchButton) {
        this.searchButton = searchButton;
    }
    //TextAreas from TSB Search Tab
    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }
    //TextField from TSB Search Tab
    public void setSearchField(TextField searchField) {
        this.searchField = searchField;
    }
    //VE Calculator Tab Objects
    public void getAnswerField(Button calculateAnswer, TextField rpmField, TextField cfmField, TextField cidField, TextField answerField) {
        this.rpmField = rpmField;
        this.cidField = cidField;
        this.cfmField = cfmField;
        this.answerField = answerField;
        this.calculateAnswer = calculateAnswer;
    }

    //Event Method On Search Button Clicked
    public void getTextFromEvent(){
        searchButton.setOnAction(event -> {
            try {
                fileName = searchField.getText(); //Uses Input From TextField To Change fileName
                File file = new File(pathName+fileName);
                if(file.exists() == true){
                readFile(file);
                searchField.setText("");}
                    else
                    textArea.appendText("Error: File Not Found, Enter a New Search Term and Try Again" + "\n");
                    if(fileName.equals("")){
                        textArea.setText("");
                        textArea.appendText("Please Enter a Search Term to Continue...");}
                        else
                        if(fileName.equals("clear") || fileName.equals("Clear")){
                            textArea.setText("");
                            searchField.setText("");
                        }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        event.consume();});
    }
    //Read in File Method to TextArea
    public void readFile(File file) throws FileNotFoundException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        try {
            textArea.setText(""); //Clears TextArea Before Reading In File
            String line = bufferedReader.readLine();
            while(line != null){
                textArea.appendText(line + "\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void veCalculate(){
        calculateAnswer.setOnAction(event -> {
            try{
                float cfm = Integer.parseInt(cfmField.getText());       //parsing input for integers
                float cid = Integer.parseInt(cidField.getText());
                float rpm = Integer.parseInt(rpmField.getText());
                float answer = ((3456*cfm)/(cid*rpm))*100;
                int castAnswer = (int)(answer);                         //casting as int to trim decimal
                String answers = new String(String.valueOf(castAnswer));
                answerField.setText(castAnswer + "%");
                rpmField.setText("");                                   //clears textfields after calculating
                cidField.setText("");
                cfmField.setText("");
                event.consume();}
            catch(NumberFormatException s){                             //catching number format exception for non integer inputs
                s.getMessage();
            }
        });
    }
}
