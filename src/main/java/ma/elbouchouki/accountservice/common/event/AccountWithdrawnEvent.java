package ma.elbouchouki.accountservice.common.event;

import lombok.Getter;

@Getter
public class AccountWithdrawnEvent extends BaseEvent<String> {
    private final String currency;
    private final double amount;

    public AccountWithdrawnEvent(String id, String currency, double amount) {
        super(id);
        this.currency = currency;
        this.amount = amount;
    }
}
