package ma.elbouchouki.accountservice.common.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BaseEvent<T> {
    private final T id;
}
