package ma.elbouchouki.accountservice.common.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@RequiredArgsConstructor
public class BaseCommand<T> {
    @TargetAggregateIdentifier
    private final T id;
}
