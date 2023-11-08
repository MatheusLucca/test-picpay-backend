package com.pipaysimplificado.services;

import com.pipaysimplificado.domain.user.User;
import com.pipaysimplificado.domain.user.UserType;
import com.pipaysimplificado.dtos.TransactionDTO;
import com.pipaysimplificado.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository repository;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When create transaction then return sucessufuly when everything is ok")
    void whenCreateTransactionThenReturnSucessufuly() throws Exception {
        User sender = new User(1L, "Matheus", "Teste", "12345678900", "matheus@gmail.com", "1234",
                new BigDecimal(10), UserType.COMMON);
        User receiver = new User(2L, "Lucca", "Teste", "23242", "lucca@gmail.com", "1234",
                new BigDecimal(10), UserType.COMMON);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);
        when(authorizationService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10),1L, 2L);

        transactionService.createTransaction(request);

        verify(repository, times(1)).save(any());
        sender.setBalance(new BigDecimal(0));
        verify(userService, times(1)).saveUser(sender);

        receiver.setBalance(new BigDecimal(20));
        verify(userService, times(1)).saveUser(receiver);

        verify(notificationService, times(1)).sendNotification(sender, "Transaction realized successfully");
        verify(notificationService, times(1)).sendNotification(receiver, "You received a transaction");


    }
    @Test
    @DisplayName("When create transaction and the transaaction is not allowed then throw exception")
    void whenCreateTransactionButTransactionIsNotAllowedThenThrowException() throws Exception {
        User sender = new User(1L, "Matheus", "Teste", "12345678900", "matheus@gmail.com", "1234",
                new BigDecimal(10), UserType.COMMON);
        User receiver = new User(2L, "Lucca", "Teste", "23242", "lucca@gmail.com", "1234",
                new BigDecimal(10), UserType.COMMON);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(authorizationService.authorizeTransaction(any(), any())).thenReturn(false);

        Exception thrown = assertThrows(Exception.class, () -> {
            TransactionDTO request = new TransactionDTO(new BigDecimal(10),1L, 2L);
            transactionService.createTransaction(request);
        });

        assertEquals("Transaction not authorized", thrown.getMessage());

    }
}