package ma.elbouchouki.accountservice.common.commands;

import lombok.Getter;

@Getter
public class WithdrawAccountCommand extends BaseCommand<String> {

    private final String currency;
    private final double amount;

    public WithdrawAccountCommand(String id, String currency, double amount) {
        super(id);
        this.currency = currency;
        this.amount = amount;
    }
}
