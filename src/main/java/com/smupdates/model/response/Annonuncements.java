package com.smupdates.model.response;

/**
 * Created by srikanth on 17/10/17.
 */
public class Annonuncements{
    private String company;

    private String date;

    private String desc;

    private String link;

    private String  symbol;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Annonuncements{" +
                "company='" + company + '\'' +
                ", date='" + date + '\'' +
                ", desc='" + desc + '\'' +
                ", link='" + link + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Annonuncements that = (Annonuncements) o;

        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        return symbol != null ? symbol.equals(that.symbol) : that.symbol == null;
    }

    @Override
    public int hashCode() {
        int result = company != null ? company.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        return result;
    }

}
