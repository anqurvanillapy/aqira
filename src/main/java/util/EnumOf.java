package util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EnumOf<T> {
    T getValue();

    @Nullable
    static <E extends Enum<E> & EnumOf<T>, T> E enumOf(@NotNull Class<E> e, T v) {
        for (E kind : e.getEnumConstants()) {
            if (kind.getValue() == v) {
                return kind;
            }
        }
        return null;
    }
}
