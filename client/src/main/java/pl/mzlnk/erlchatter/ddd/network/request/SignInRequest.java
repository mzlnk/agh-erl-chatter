package pl.mzlnk.erlchatter.ddd.network.request;

public class SignInRequest extends BaseNetworkRequest {

    private final String login;
    private final String password;

    public SignInRequest(String login, String password) {
        super(RequestTypeEnum.USER_SIGN_IN);
        this.login = login;
        this.password = password;
    }

    @Override
    protected Object[] args() {
        return new Object[]{this.login, this.password};
    }

}
