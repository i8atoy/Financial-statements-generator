package com.financialgenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import static java.lang.Float.max;


@JacksonXmlRootElement(localName = "DataSet", namespace = "http://www.bnr.ro/xsd")
@JsonIgnoreProperties(ignoreUnknown = true)
record BnrDataSet(
        @JacksonXmlProperty(localName = "Body")
        BnrBody body
) {}


@JsonIgnoreProperties(ignoreUnknown = true)
record BnrBody(
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "Cube")
        List<BnrCube> cubes


) {
public BnrCube getCubeForDate(LocalDate date){
    for (BnrCube cube : cubes) {
        if(cube.date().equals(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))){
            return cube;
        }
    }
    return null;
}
}


@JsonIgnoreProperties(ignoreUnknown = true)
record BnrCube(
        @JacksonXmlProperty(isAttribute = true)
        String date,

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "Rate")
        List<BnrRate> rates
) {}


@JsonIgnoreProperties(ignoreUnknown = true)
class BnrRate {
    @JacksonXmlProperty(isAttribute = true)
    public String currency;

    @JacksonXmlText
    public String value;

    @Override
    public String toString() {
        return "[currency=" + currency + ", value=" + value + "]";
    }
}

record ExchangeRate(String currency, LocalDate date, Float rate) {}


public class ExchangeRateClient {
    RestClient restClient;

    public ExchangeRateClient(RestClient restClient) {
        this.restClient = restClient;

    }
    public List<ExchangeRate> getExchangeRateBetweenDates(String currency, LocalDate start, LocalDate end){
        BnrDataSet dataSet = restClient.get().uri("https://curs.bnr.ro/files/xml/years/nbrfxrates{year}.xml", start.getYear()).retrieve().body(BnrDataSet.class);
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        for (LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
            BnrCube cube = dataSet.body().getCubeForDate(date);

            List<BnrRate> rates = cube.rates();

            LocalDate currentDate = date;

            List<ExchangeRate> filteredRates = rates.stream()
                    .filter(rate -> rate.currency.equalsIgnoreCase(currency))
                    .map(rate -> new ExchangeRate(
                            rate.currency,
                            currentDate,
                            Float.parseFloat(rate.value)
                    ))
                    .toList();

            exchangeRates.addAll(filteredRates);

        }

        System.out.println(exchangeRates);
        return exchangeRates;
    }

    public Float computeCorrectExchangeRate(List<ExchangeRate> exchangeRates){
        return exchangeRates.stream().
                map(exchangeRate -> exchangeRate.rate()).
                min(Float::compareTo).
                orElse(0f);
    }


    public static void main(String[] args) {

        RestClient restClient = RestClient.builder().build();
        ExchangeRateClient client = new ExchangeRateClient(restClient);

        LocalDate start = LocalDate.of(2026, 1, 13);
        LocalDate end = LocalDate.of(2026, 1, 15);

        List<ExchangeRate> rates = client.getExchangeRateBetweenDates("EUR", start, end);
        System.out.println(client.computeCorrectExchangeRate(rates));

    }
}
