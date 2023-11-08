package com.pipaysimplificado.controllers;

import com.pipaysimplificado.domain.transaction.Transaction;
import com.pipaysimplificado.dtos.TransactionDTO;
import com.pipaysimplificado.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transaction) throws Exception {
        System.out.println("Creating transaction");
        return new ResponseEntity<>(this.transactionService.createTransaction(transaction), HttpStatus.CREATED);
    }
}
