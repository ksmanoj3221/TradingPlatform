package com.trading.controller;

import com.trading.exception.DuplicateTraderException;
import com.trading.model.AddMoneyRequest;
import com.trading.model.Trader;
import com.trading.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trading/traders")
public class TraderController {

    private final TraderService traderService;

    @Autowired
    public TraderController(TraderService traderService) {
        this.traderService = traderService;
    }

    // POST request to register a new trader
    @PostMapping("/register")
    public ResponseEntity<Trader> registerTrader(@RequestBody Trader trader) {
        try {
            Trader registeredTrader = traderService.registerTrader(trader);
            return new ResponseEntity<>(registeredTrader, HttpStatus.CREATED);
        } catch (DuplicateTraderException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // GET request to fetch all traders
    @GetMapping("/all")
    public ResponseEntity<List<Trader>> getAllTraders() {
        List<Trader> traders = traderService.getAllTraders();
        if (traders.isEmpty()) {
            System.out.println(traders);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // If no traders exist, return 204 No Content
        }
        return new ResponseEntity<>(traders, HttpStatus.OK); // Return 200 with traders list
    }

    // GET request to fetch a trader by email
    @GetMapping
    public ResponseEntity<Trader> getTraderByEmail(@RequestParam String email) {
        Optional<Trader> trader = traderService.getTraderByEmail(email);
        return trader.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    // PUT request to update a trader's name by email
    @PutMapping
    public ResponseEntity<Trader> updateTraderName(@RequestBody Trader trader) {
        try {
            Trader updatedTrader = traderService.updateTraderName(trader.getEmail(), trader.getName());
            return new ResponseEntity<>(updatedTrader, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // PUT request to add money to a trader's account by email
    @PutMapping("/add")
    public ResponseEntity<Trader> addMoneyToTraderAccount(@RequestBody AddMoneyRequest request) {
        try {
            Trader updatedTrader = traderService.addMoneyToTraderAccount(request.getEmail(), request.getAmount());
            return new ResponseEntity<>(updatedTrader, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE Request to delete trader from db
    @DeleteMapping("/delete")
    public ResponseEntity<Trader> deleteTrader(@RequestParam Long id){
        try{
            traderService.deleteTraderById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Exception handling
    @ExceptionHandler(DuplicateTraderException.class)
    public ResponseEntity<String> handleDuplicateTraderException(DuplicateTraderException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
