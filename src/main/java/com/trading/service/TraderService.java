package com.trading.service;

import com.trading.model.Trader;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TraderService {

    Trader registerTrader(Trader trader);

    List<Trader> getAllTraders();

    Optional<Trader> getTraderByEmail(String email);

    Trader updateTraderName(String email, String newName);

    Trader addMoneyToTraderAccount(String email, BigDecimal amount);

    void deleteTraderById(Long id);
}
