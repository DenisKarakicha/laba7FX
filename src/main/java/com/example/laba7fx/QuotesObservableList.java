package com.example.laba7fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class QuotesObservableList implements Serializable{

    ObservableList<Quote> quotes;

    public QuotesObservableList() {
        quotes = FXCollections.observableArrayList();
    }

    public javafx.collections.ObservableList<Quote> getObsList() {
        return quotes;
    }

    void add(Quote quote) {
        this.quotes.add(quote);
    }



}
