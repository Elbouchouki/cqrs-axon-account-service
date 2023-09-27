package ma.elbouchouki.accountservice.common.event;

import lombok.Getter;
import ma.elbouchouki.accountservice.common.enums.AccountStatus;

@Getter
public class AccountCreatedEvent extends BaseEvent<String> {
    private final String currency;
    private final double balance;
    private final AccountStatus status;

    public AccountCreatedEvent(String id, String currency, double balance, AccountStatus status) {
        super(id);
        this.currency = currency;
        this.balance = balance;
        this.status = status;
    }
}
