package ma.elbouchouki.accountservice.query.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.elbouchouki.accountservice.common.enums.AccountTransactionType;
import ma.elbouchouki.accountservice.common.event.AccountCreatedEvent;
import ma.elbouchouki.accountservice.common.event.AccountCreditedEvent;
import ma.elbouchouki.accountservice.common.event.AccountWithdrawnEvent;
import ma.elbouchouki.accountservice.query.entity.Account;
import ma.elbouchouki.accountservice.query.entity.AccountTransaction;
import ma.elbouchouki.accountservice.query.queries.GetAccountQuery;
import ma.elbouchouki.accountservice.query.queries.GetAccountTransactionsQuery;
import ma.elbouchouki.accountservice.query.queries.GetAllAccountsQuery;
import ma.elbouchouki.accountservice.query.queries.GetTransactionQuery;
import ma.elbouchouki.accountservice.query.repository.AccountRepository;
import ma.elbouchouki.accountservice.query.repository.AccountTransactionRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AccountEventHandlerService {
    public final AccountRepository accountRepository;
    public final AccountTransactionRepository accountTransactionRepository;

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage<AccountCreatedEvent> eventMessage) {
        log.info("AccountCreatedEvent received: {}", event);
        Account account = Account.builder()
                .id(event.getId())
                .createdAt(eventMessage.getTimestamp())
                .status(event.getStatus())
                .currency(event.getCurrency())
                .balance(event.getBalance())
                .build();
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage<AccountCreditedEvent> eventMessage) {
        log.info("AccountCreditedEvent received: {}", event);
        Account account = accountRepository.findById(event.getId()).get();
        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);

        AccountTransaction accountTransaction = AccountTransaction.builder()
                .account(account)
                .amount(event.getAmount())
                .currency(event.getCurrency())
                .createdAt(eventMessage.getTimestamp())
                .type(AccountTransactionType.CREDIT)
                .build();
        accountTransactionRepository.save(accountTransaction);
    }

    @EventHandler
    public void on(AccountWithdrawnEvent event, EventMessage<AccountWithdrawnEvent> eventMessage) {
        log.info("AccountWithdrawnEvent received: {}", event);
        Account account = accountRepository.findById(event.getId()).get();
        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);

        AccountTransaction accountTransaction = AccountTransaction.builder()
                .account(account)
                .amount(event.getAmount())
                .currency(event.getCurrency())
                .createdAt(eventMessage.getTimestamp())
                .type(AccountTransactionType.WITHDRAWAL)
                .build();
        accountTransactionRepository.save(accountTransaction);
    }

    @QueryHandler
    public Page on(GetAllAccountsQuery query) {
        return accountRepository.findAll(
                PageRequest.of(
                        query.getPage(),
                        query.getSize()
                )
        );
    }

    @QueryHandler
    public Account on(GetAccountQuery query) {
        return accountRepository.findById(query.getId()).get();
    }

    @QueryHandler
    public List<AccountTransaction> on(GetAccountTransactionsQuery query) {
        return accountTransactionRepository.findByAccountId(query.getId());
    }

    @QueryHandler
    public AccountTransaction on(GetTransactionQuery query) {
        return accountTransactionRepository.findById(query.getId()).get();
    }

}
