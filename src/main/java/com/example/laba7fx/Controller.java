package com.example.laba7fx;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Controller {

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


    public void loadTable() {
        GetJson jsonGetter = new GetJson();
        GetJson.url = "https://www.breakingbadapi.com/api/quotes";
        jsonGetter.run();

        String jsonString = jsonGetter.jsonIn;
        if (!jsonString.equalsIgnoreCase("Api not found!")) {
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
                Quote newQuote = new Quote(quote, author, series, quoteID);
                quotes.add(newQuote);
            }

            quoteId.setCellValueFactory(new PropertyValueFactory<>("quoteId"));
            quote.setCellValueFactory(new PropertyValueFactory<>("quote"));
            author.setCellValueFactory(new PropertyValueFactory<>("author"));
            series.setCellValueFactory(new PropertyValueFactory<>("series"));


            tableId.setItems(quotes.getObsList());
            tableId.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            correctLabel.setText("Данные успешно загружены!");

        } else {
            correctLabel.setText("Данные не доступны!");
        }


    }

}