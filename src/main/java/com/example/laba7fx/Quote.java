package com.example.laba7fx;

import java.io.Serializable;

public class Quote implements Serializable {

    private Long quoteId;
    private String quote;
    private String author;
    private String series;


    public Quote( String quote, String author, String series ,Long quoteId) {
        this.quoteId = quoteId;
        this.quote = quote;
        this.series = series;
        this.author = author;
    }

    public Quote() {

    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

}
