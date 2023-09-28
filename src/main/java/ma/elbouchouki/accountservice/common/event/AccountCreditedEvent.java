package ma.elbouchouki.accountservice.common.event;

import lombok.Getter;

@Getter
public class AccountCreditedEvent extends BaseEvent<String> {
    private final String currency;
    private final double amount;

    public AccountCreditedEvent(String id, String currency, double amount) {
        super(id);
        this.currency = currency;
        this.amount = amount;
    }
}
