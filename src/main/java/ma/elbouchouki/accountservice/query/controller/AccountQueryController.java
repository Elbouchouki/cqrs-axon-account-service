package ma.elbouchouki.accountservice.query.controller;

import lombok.RequiredArgsConstructor;
import ma.elbouchouki.accountservice.query.entity.Account;
import ma.elbouchouki.accountservice.query.entity.AccountTransaction;
import ma.elbouchouki.accountservice.query.queries.GetAccountQuery;
import ma.elbouchouki.accountservice.query.queries.GetAccountTransactionsQuery;
import ma.elbouchouki.accountservice.query.queries.GetAllAccountsQuery;
import ma.elbouchouki.accountservice.query.queries.GetTransactionQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/accounts")
@RequiredArgsConstructor
public class AccountQueryController {

    private final QueryGateway queryGateway;

    @GetMapping("/get-all")
    public CompletableFuture<Page<Account>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return queryGateway.query(
                new GetAllAccountsQuery(page, size),
                ResponseTypes.instanceOf(Page.class)
        ).thenApply(this::castToPageOfAccount);
    }

    @GetMapping("/get-account/{id}")
    public CompletableFuture<Account> getAccount(
            String id
    ) {
        return queryGateway.query(
                new GetAccountQuery(id),
                ResponseTypes.instanceOf(Account.class)
        );
    }

    @GetMapping("/get-account/{id}/transactions")
    public CompletableFuture<List<AccountTransaction>> getAccountTransactions(
            String id
    ) {
        return queryGateway.query(
                new GetAccountTransactionsQuery(id),
                ResponseTypes.multipleInstancesOf(AccountTransaction.class)
        );
    }

    @GetMapping("/get-transaction/{id}")
    public CompletableFuture<AccountTransaction> getTransaction(
            String id
    ) {
        return queryGateway.query(
                new GetTransactionQuery(id),
                ResponseTypes.instanceOf(AccountTransaction.class)
        );
    }

    @SuppressWarnings("unchecked")
    private <T> Page<T> castToPageOfAccount(Page<?> page) {
        return (Page<T>) page;
    }
}
