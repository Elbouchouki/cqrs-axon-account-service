package ma.elbouchouki.accountservice.common.commands;

import lombok.Getter;

@Getter
public class CreditAccountCommand extends BaseCommand<String> {

    private final String currency;
    private final double amount;

    public CreditAccountCommand(String id, String currency, double amount) {
        super(id);
        this.currency = currency;
        this.amount = amount;
    }
}
