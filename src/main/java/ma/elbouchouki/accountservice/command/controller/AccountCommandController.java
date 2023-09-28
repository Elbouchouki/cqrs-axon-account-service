package ma.elbouchouki.accountservice.command.controller;

import lombok.RequiredArgsConstructor;
import ma.elbouchouki.accountservice.common.commands.CreateAccountCommand;
import ma.elbouchouki.accountservice.common.commands.CreditAccountCommand;
import ma.elbouchouki.accountservice.common.commands.WithdrawAccountCommand;
import ma.elbouchouki.accountservice.common.dto.CreateAccountRequest;
import ma.elbouchouki.accountservice.common.dto.CreditAccountRequest;
import ma.elbouchouki.accountservice.common.dto.WithdrawAccountRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/command/accounts")
@RequiredArgsConstructor
public class AccountCommandController {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequest request) {
        return commandGateway.send(
                new CreateAccountCommand(
                        UUID.randomUUID().toString(),
                        request.currency(),
                        request.startingBalance()
                )
        );
    }

    @PostMapping("/credit/{id}")
    public CompletableFuture<String> creditAccount(@PathVariable String id, @RequestBody CreditAccountRequest request) {
        return commandGateway.send(
                new CreditAccountCommand(
                        id,
                        request.currency(),
                        request.amount()
                )
        );
    }

    @PostMapping("/withdraw/{id}")
    public CompletableFuture<String> withdrawAccount(@PathVariable String id, @RequestBody WithdrawAccountRequest request) {
        return commandGateway.send(
                new WithdrawAccountCommand(
                        id,
                        request.currency(),
                        request.amount()
                )
        );
    }

    @GetMapping("/eventStore/{id}")
    public Stream<? extends DomainEventMessage<?>> eventStore(@PathVariable String id) {
        return eventStore.readEvents(id).asStream();
    }

}
