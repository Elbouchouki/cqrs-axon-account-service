package ma.elbouchouki.accountservice.command.aggregate;

import lombok.NoArgsConstructor;
import ma.elbouchouki.accountservice.common.commands.CreateAccountCommand;
import ma.elbouchouki.accountservice.common.enums.AccountStatus;
import ma.elbouchouki.accountservice.common.event.AccountCreatedEvent;
import ma.elbouchouki.accountservice.common.exception.NegativeStartingBalanceException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private String currency;
    private double balance;
    private AccountStatus status;

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        if (command.getStartingBalance() < 0) {
            throw new NegativeStartingBalanceException("Starting balance cannot be negative");
        }
        AggregateLifecycle.apply(
                new AccountCreatedEvent(
                        command.getId(),
                        command.getCurrency(),
                        command.getStartingBalance(),
                        AccountStatus.CREATED
                )
        );
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getId();
        this.currency = event.getCurrency();
        this.balance = event.getBalance();
        this.status = event.getStatus();
    }
}
