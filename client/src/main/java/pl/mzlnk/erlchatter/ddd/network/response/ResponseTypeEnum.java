package pl.mzlnk.erlchatter.ddd.network.response;

import java.util.stream.Stream;

public enum ResponseTypeEnum {

    ERROR,
    MESSAGE_ALL,
    MESSAGE_TO,
    SIGN_IN,
    SIGN_UP,
    SIGN_OUT;

    public static boolean contains(String name) {
        return Stream.of(values())
                .anyMatch(e -> e.name().toLowerCase().equals(name));
    }

}
