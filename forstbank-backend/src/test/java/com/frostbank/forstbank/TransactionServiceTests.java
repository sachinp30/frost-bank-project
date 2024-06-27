package com.frostbank.forstbank;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
//import static org.postgresql.jdbc.PgConnection.ReadOnlyBehavior.transaction;

import com.frostbank.forstbank.dto.TransactionDto;
import com.frostbank.forstbank.dto.TransferRequest;
import com.frostbank.forstbank.entity.Account;
import com.frostbank.forstbank.entity.Customer;
import com.frostbank.forstbank.entity.Transaction;
import com.frostbank.forstbank.enums.AccountType;
import com.frostbank.forstbank.enums.TypeOfTransaction;
import com.frostbank.forstbank.exception.ResourceNotFoundException;
import com.frostbank.forstbank.mapper.AccountMapper;
import com.frostbank.forstbank.mapper.CustomerMapper;
import com.frostbank.forstbank.repository.AccountRepository;
import com.frostbank.forstbank.repository.TransactionRepository;
import com.frostbank.forstbank.service.AccountService;
import com.frostbank.forstbank.service.CustomerService;
import com.frostbank.forstbank.service.TransactionService;
import com.frostbank.forstbank.service.impl.TransactionImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


public class TransactionServiceTests {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;
    private AccountRepository accountRepository;
    private CustomerService customerService;

//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    public void testGetTransaction_Success() {
        // Mock data
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setAmount(100.0); // Set other fields as needed

        transactionRepository = mock(TransactionRepository.class);
        accountRepository = mock(AccountRepository.class);
        customerService = mock(CustomerService.class);
        accountService = mock(AccountService.class);
        transactionService = new TransactionImpl(transactionRepository, accountRepository, accountService, customerService);
        // Mock repository method to return the transaction
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        //when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        // Mock mapper method
        TransactionDto expectedDto = new TransactionDto();
        expectedDto.setTransactionId(transaction.getTransactionId());
        expectedDto.setAmount(transaction.getAmount()); // Map other fields as needed

        // Call the service method
        TransactionDto result = transactionService.getTransaction(transactionId);

        // Verify the result
        assertEquals(expectedDto.getTransactionId(), result.getTransactionId());
        assertEquals(expectedDto.getAmount(), result.getAmount()); // Assert other fields

        // Verify repository method was called
        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    public void testGetTransaction_NotFound() {
        // Mock data
        UUID transactionId = UUID.randomUUID();

        transactionRepository = mock(TransactionRepository.class);
        accountRepository = mock(AccountRepository.class);
        customerService = mock(CustomerService.class);
        accountService = mock(AccountService.class);
        transactionService = new TransactionImpl(transactionRepository, accountRepository, accountService, customerService);

        // Mock repository method to return empty optional
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        // Call the service method and expect ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.getTransaction(transactionId);
        });

        // Verify repository method was called
        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    public void processTransaction_Success() {
        // Mock data
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setAmount(100.0);
        transaction.setSuccess(true);
        transaction.setType(TypeOfTransaction.DEPOSIT);

        transactionRepository = mock(TransactionRepository.class);
        accountRepository = mock(AccountRepository.class);
        customerService = mock(CustomerService.class);
        accountService = mock(AccountService.class);
        transactionService = new TransactionImpl(transactionRepository, accountRepository, accountService, customerService);

        // Mock repository save method
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // Mock mapper method
        TransactionDto expectedDto = new TransactionDto();
        expectedDto.setTransactionId(transaction.getTransactionId());
        expectedDto.setAmount(transaction.getAmount());
        expectedDto.setSuccess(transaction.isSuccess());
        expectedDto.setType(transaction.getType());

        TransactionDto result = transactionService.processTransaction(expectedDto);

        // Verify the result
        assertEquals(expectedDto.getTransactionId(), transaction.getTransactionId());
        assertEquals(expectedDto.getAmount(), transaction.getAmount());
        assertEquals(expectedDto.isSuccess(), transaction.isSuccess());
        assertEquals(expectedDto.getType(), transaction.getType());
    }

    @Test
    public void moneyTransfer_Success() {
        Customer customer = new Customer(UUID.randomUUID(),
                "Sachin", "Patel", "abc@zyc.com", "1234567890");
        Account account = new Account(UUID.randomUUID(), AccountType.CURRENT,
                true, 100, "USD", customer);

        Customer customerReceiver = new Customer(UUID.randomUUID(),
                "Sachin", "Patel", "abc@zyc.com", "2345678901");
        Account accountReciever = new Account(UUID.randomUUID(), AccountType.CURRENT,
                true, 100, "USD", customer);

        TransferRequest request = new TransferRequest(50, "1234567890", "2345678901",
                LocalDateTime.of(2024, 6, 27, 2, 30));

        transactionRepository = mock(TransactionRepository.class);
        accountRepository = mock(AccountRepository.class);
        customerService = mock(CustomerService.class);
        accountService = mock(AccountService.class);
        transactionService = new TransactionImpl(transactionRepository, accountRepository, accountService, customerService);

        when(customerService.findByMobileNumber(customer.getMobileNumber())).thenReturn(CustomerMapper.mapToCustomerDto(customer));
        when(accountService.findByCustomerId(customer.getCustomerId())).thenReturn(AccountMapper.mapToAccountDto(account));
        when(customerService.findByMobileNumber(customerReceiver.getMobileNumber())).thenReturn(CustomerMapper.mapToCustomerDto(customerReceiver));
        when(accountService.findByCustomerId(customerReceiver.getCustomerId())).thenReturn(AccountMapper.mapToAccountDto(accountReciever));

        when(accountRepository.findById(account.getAccountNumber())).thenReturn(Optional.of(account));
        when(accountRepository.findById(accountReciever.getAccountNumber())).thenReturn(Optional.of(accountReciever));

        Transaction senderDto = new Transaction();
        //senderDto.setTransactionId(transaction.getTransactionId());
        senderDto.setAmount(account.getBalance() - request.getAmount());
        senderDto.setType(TypeOfTransaction.WITHDRAW);

        Transaction receiverDto = new Transaction();
        receiverDto.setAmount(accountReciever.getBalance() + request.getAmount());
        receiverDto.setType(TypeOfTransaction.DEPOSIT);

        when(transactionRepository.save(senderDto)).thenReturn(senderDto);
        when(transactionRepository.save(receiverDto)).thenReturn(receiverDto);
        UUID transactionId = UUID.randomUUID();
        transactionService.transferMoney(request, transactionId);


        verify(accountRepository, times(1)).findById(account.getAccountNumber());
        verify(accountRepository, times(1)).findById(accountReciever.getAccountNumber());
        verify(accountRepository, times(1)).save(account);
        verify(accountRepository, times(1)).save(accountReciever);
        verify(customerService, times(1)).findByMobileNumber(customer.getMobileNumber());
        verify(customerService, times(1)).findByMobileNumber(customerReceiver.getMobileNumber());

    }
}
