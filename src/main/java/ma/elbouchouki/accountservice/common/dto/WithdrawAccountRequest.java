package ma.elbouchouki.accountservice.common.dto;

import lombok.Builder;

@Builder
public record WithdrawAccountRequest(
        String currency,
        double amount
) {
}
