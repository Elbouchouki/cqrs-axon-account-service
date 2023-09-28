package ma.elbouchouki.accountservice.common.dto;

import lombok.Builder;

@Builder
public record CreditAccountRequest(
        String currency,
        double amount
) {
}
