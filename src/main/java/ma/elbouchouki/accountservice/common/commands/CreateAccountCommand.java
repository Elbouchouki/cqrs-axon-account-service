package ma.elbouchouki.accountservice.common.commands;

import lombok.Getter;

@Getter
public class CreateAccountCommand extends BaseCommand<String> {

    private final String currency;

    private final double startingBalance;

    public CreateAccountCommand(String id, String currency, double startingBalance) {
        super(id);
        this.currency = currency;
        this.startingBalance = startingBalance;
    }
}
