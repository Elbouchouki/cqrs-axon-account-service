package ma.elbouchouki.accountservice.query.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAllAccountsQuery {
    private int page;
    private int size;
}
