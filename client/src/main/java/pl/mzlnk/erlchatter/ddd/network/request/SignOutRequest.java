package pl.mzlnk.erlchatter.ddd.network.request;

import pl.mzlnk.erlchatter.ddd.user.User;

public class SignOutRequest extends BaseAuthorizedNetworkRequest {

    public SignOutRequest(User user) {
        super(user, RequestTypeEnum.USER_SIGN_OUT);
    }

    @Override
    protected Object[] args() {
        return new Object[0];
    }

}
