package com.shepherdmoney.interviewproject.repository;

import com.shepherdmoney.interviewproject.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Crud repository to store credit cards
 */
@Repository("CreditCardRepo")
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    public Optional<CreditCard> findByCardNumber(String creditCardNumber);
    public boolean isPresent();
    public Integer addCreditCardToUser(Integer userId, Integer cardNumber);
    public List<CreditCardView> getAllCardOfUser(Integer userId);
    public void save(CreditCard card);
}
