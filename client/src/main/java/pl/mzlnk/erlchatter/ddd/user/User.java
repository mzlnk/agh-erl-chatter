package pl.mzlnk.erlchatter.ddd.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

    private String login;
    private String token;

}
