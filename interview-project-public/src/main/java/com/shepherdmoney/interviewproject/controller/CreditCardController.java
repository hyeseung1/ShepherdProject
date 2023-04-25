package com.shepherdmoney.interviewproject.controller;

import com.shepherdmoney.interviewproject.vo.request.AddCreditCardToUserPayload;
import com.shepherdmoney.interviewproject.vo.request.UpdateBalancePayload;
import com.shepherdmoney.interviewproject.vo.response.CreditCardView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import com.sheperdmoney.interviewproject.repository.*;


@RestController
public class CreditCardController {
    
    private final CreditCardRepository creditCardRepository;
    // TODO: wire in CreditCard repository here (~1 line)
    public CreditCardController(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @PostMapping("/credit-card")
    public ResponseEntity<Integer> addCreditCardToUser(@RequestBody AddCreditCardToUserPayload payload) {
        // TODO: Create a credit card entity, and then associate that credit card with user with given userId
        //       Return 200 OK with the credit card id if the user exists and credit card is successfully associated with the user
        //       Return other appropriate response code for other exception cases
        //       Do not worry about validating the card number, assume card number could be any arbitrary format and length
        Integer creditCardId = creditCardRepository.addCreditCardToUser(payload.getUserId(), payload.getCardNumber());
        if (creditCardId != null) {
            return ResponseEntity.ok(creditCardId);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/credit-card:all")
    public ResponseEntity<List<CreditCardView>> getAllCardOfUser(@RequestParam int userId) {
        // TODO: return a list of all credit card associated with the given userId, using CreditCardView class
        //       if the user has no credit card, return empty list, never return null
        List<CreditCardView> creditCards = creditCardRepository.getAllCardOfUser(userId);
        return ResponseEntity.ok(creditCards);
    }

    @GetMapping("/credit-card:user-id")
    public ResponseEntity<Integer> getUserIdForCreditCard(@RequestParam String creditCardNumber) {
        // TODO: Given a credit card number, efficiently find whether there is a user associated with the credit card
        //       If so, return the user id in a 200 OK response. If no such user exists, return 400 Bad Request
       Optional<CreditCard> creditCard = creditCardRepository.findByCardNumber(creditCardNumber);
       if (creditCard.isPresent()) {
           return ResponseEntity.ok(creditCard.get().getUser().getId());
       } else {
           return ResponseEntity.badRequest().build();
       }
    }

    @PostMapping("/credit-card:update-balance")
    public SomeEnityData postMethodName(@RequestBody UpdateBalancePayload[] payload) {
        //TODO: Given a list of transactions, update credit cards' balance history.
        //      For example: if today is 4/12, a credit card's balanceHistory is [{date: 4/12, balance: 110}, {date: 4/10, balance: 100}],
        //      Given a transaction of {date: 4/10, amount: 10}, the new balanceHistory is
        //      [{date: 4/12, balance: 120}, {date: 4/11, balance: 110}, {date: 4/10, balance: 110}]
        //      Return 200 OK if update is done and successful, 400 Bad Request if the given card number
        //        is not associated with a card.
        for (UpdateBalancePayload transaction : payload) {
            Optional<CreditCard> creditCard = creditCardRepository.findByCardNumber(transaction.getCardNumber());
            if (creditCard.isPresent()) {
                CreditCard card = creditCard.get();
                List<BalanceHistory> balanceHistory = card.getBalanceHistory();
                LocalDate date = LocalDate.parse(transaction.getDate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                BigDecimal balance = card.getCurrentBalance();
                balance = balance.add(transaction.getAmount());
                balanceHistory.add(new BalanceHistory(date, balance));
                card.setBalanceHistory(balanceHistory);
                card.setCurrentBalance(balance);
                creditCardRepository.save(card);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok().build();
    }
    
}
