package pl.mzlnk.erlchatter.ddd.network.request;

import com.ericsson.otp.erlang.OtpErlangList;
import pl.mzlnk.erlchatter.ddd.user.User;
import pl.mzlnk.erlchatter.ddd.utils.OtpErlangListCreator;

public abstract class BaseAuthorizedNetworkRequest extends BaseNetworkRequest {

    private User user;

    public BaseAuthorizedNetworkRequest(User user, RequestTypeEnum atom) {
        super(atom);
        this.user = user;
    }

    @Override
    public OtpErlangList getArgs() {
        return OtpErlangListCreator.toList(args());
    }
}
