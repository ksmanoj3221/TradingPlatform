package com.trading.service;

import com.trading.exception.DuplicateTraderException;
import com.trading.model.Trader;
import com.trading.repository.TraderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class TraderServiceImpl implements TraderService {

    private final TraderRepository traderRepository;

    @Autowired
    public TraderServiceImpl(TraderRepository traderRepository) {
        this.traderRepository = traderRepository;
    }

    @Override
    public Trader registerTrader(Trader trader) {
        // Check if a trader with the same email already exists
        if (traderRepository.findByEmail(trader.getEmail()).isPresent()) {
            throw new DuplicateTraderException("Trader with email " + trader.getEmail() + " already exists.");
        }

        // Set current timestamp for createdAt and updatedAt
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        trader.setCreatedAt(currentTimestamp);
        trader.setUpdatedAt(currentTimestamp); // Initially, updatedAt should be the same as createdAt

        // Save the trader to the repository
        return traderRepository.save(trader);
    }

    @Override
    public List<Trader> getAllTraders() {
        return traderRepository.findAll();
    }

    @Override
    public Optional<Trader> getTraderByEmail(String email) {
        return traderRepository.findByEmail(email);
    }

    @Override
    public Trader updateTraderName(String email, String newName) {
        // Fetch the trader by email
        Trader trader = traderRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Trader not found with email: " + email));

        // Update the name and set the updatedAt timestamp to the current time
        trader.setName(newName);
        trader.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Update the updatedAt timestamp

        // Save the updated trader back to the repository
        return traderRepository.save(trader);
    }

    @Override
    public Trader addMoneyToTraderAccount(String email, BigDecimal amount) {
        // Ensure the amount is positive
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount to add must be positive");
        }

        // Fetch the trader by email
        Trader trader = traderRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Trader not found with email: " + email));

        // Add the amount to the balance and update the updatedAt timestamp
        trader.setBalance(trader.getBalance().add(amount));
        trader.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Update the updatedAt timestamp

        // Save the updated trader
        return traderRepository.save(trader);
    }

    @Override
    public void deleteTraderById(Long id) {
        // Fetch the trader by email
        Trader trader = traderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No Trader was found for id "+ id));
        traderRepository.delete(trader);
    }


}
