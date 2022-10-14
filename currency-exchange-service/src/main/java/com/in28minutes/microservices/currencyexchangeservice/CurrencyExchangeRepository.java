package com.in28minutes.microservices.currencyexchangeservice;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CurrencyExchangeRepository {

    private  List<CurrencyExchange> list = new ArrayList<>();

    CurrencyExchangeRepository(){
        CurrencyExchange cx1 = new CurrencyExchange(10001l,"USD","INR",BigDecimal.valueOf(65));
        CurrencyExchange cx2 = new CurrencyExchange(10002l,"EUR","INR",BigDecimal.valueOf(75));
        CurrencyExchange cx3 = new CurrencyExchange(10003l,"AUD","INR",BigDecimal.valueOf(25));
        list.add(cx1);
        list.add(cx2);
        list.add(cx3);
    }
    public CurrencyExchange findBy(String from, String to){
        return list.stream().filter(cx -> cx.getFrom().equals(from) && cx.getTo().equals(to)).findFirst().get();
    }
    public List<CurrencyExchange> findAll(){
        return list;
    }
}
