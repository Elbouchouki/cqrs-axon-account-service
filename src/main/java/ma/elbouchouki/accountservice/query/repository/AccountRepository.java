package ma.elbouchouki.accountservice.query.repository;

import ma.elbouchouki.accountservice.query.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
