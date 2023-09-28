package ma.elbouchouki.accountservice.query.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.elbouchouki.accountservice.common.enums.AccountTransactionType;

import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currency;
    private double amount;
    @Enumerated(EnumType.STRING)
    private AccountTransactionType type;
    private Instant createdAt;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}
