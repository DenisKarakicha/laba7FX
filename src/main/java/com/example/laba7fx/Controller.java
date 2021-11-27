package com.example.laba7fx;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private DataBase dataBase;
    private String[] columns = {"quoteID","quote","author","series"};
    @FXML
    private ChoiceBox<String> choiceBoxId;
    @FXML
    private TextField searchTextField;
    @FXML
    private CheckBox checkSql;
    @FXML
    private Label correctLabel;
    @FXML
    private TableView<Quote> tableId;
    @FXML
    private TableColumn<Quote, Long> quoteId;
    @FXML
    private TableColumn<Quote, String> quote;
    @FXML
    private TableColumn<Quote, String> author;
    @FXML
    private TableColumn<Quote, String> series;

    public boolean connectDataBase() {
        dataBase = new DataBase("quotes");
        return dataBase.Connect();
    }

    public void loadTable() {
        GetJson jsonGetter = new GetJson();
        GetJson.url = "https://www.breakingbadapi.com/api/quotes";
        jsonGetter.run();

        String jsonString = jsonGetter.jsonIn;
        if (!jsonString.equalsIgnoreCase("Api not found!")) {
            boolean correctConnect = connectDataBase();
            Object tempObj = null;
            try {
                tempObj = new JSONParser().parse(jsonString);
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            JSONArray jsonArray = (JSONArray) tempObj;
            QuotesObservableList quotes = new QuotesObservableList();
            assert jsonArray != null;
            for (Object jsonObject : jsonArray) {
                JSONObject getQuote = (JSONObject) jsonObject;
                String quote = (String) getQuote.get("quote");
                String author = (String) getQuote.get("author");
                String series = (String) getQuote.get("series");
                Long quoteID = (Long) getQuote.get("quote_id");
                if (checkSql.isSelected() && correctConnect) {
                    try {
                        dataBase.insert("INSERT INTO m_quotes(quoteID, quote, author, series) VALUES ('" + quoteID + "','" + quote.replace("'", "’") + "','" + author + "','" + series + "')");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                Quote newQuote = new Quote(quote, author, series, quoteID);
                quotes.add(newQuote);
            }

            quoteId.setCellValueFactory(new PropertyValueFactory<>("quoteId"));
            quote.setCellValueFactory(new PropertyValueFactory<>("quote"));
            author.setCellValueFactory(new PropertyValueFactory<>("author"));
            series.setCellValueFactory(new PropertyValueFactory<>("series"));


            tableId.setItems(quotes.getObsList());
            tableId.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            if (checkSql.isSelected() && !correctConnect)
                correctLabel.setText("Error, no database connection");
            else
                correctLabel.setText("Data loaded successfully");


        } else {
            correctLabel.setText("Data not available!");
        }

    }

    public void loadSearchQuery() throws SQLException {
        if (connectDataBase()) {
            String searchСolumn = choiceBoxId.getValue();
            ObservableList searchQuotes = dataBase.search(searchTextField.getText(), searchСolumn);
            tableId.setItems(searchQuotes);
            correctLabel.setText("All found requests!");
        }
        else
            correctLabel.setText("Error, no database connection");

    }

    public void onDeleteSql() throws SQLException {
        if (connectDataBase()) {
            dataBase.delete("TRUNCATE TABLE `m_quotes`");
            correctLabel.setText("The table is clean!");
        }
        else
            correctLabel.setText("Error, no database connection");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxId.getItems().addAll(columns);
    }
}