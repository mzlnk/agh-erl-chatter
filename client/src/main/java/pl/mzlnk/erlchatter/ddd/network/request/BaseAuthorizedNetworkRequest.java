package pl.mzlnk.erlchatter.ddd.network.request;

import com.ericsson.otp.erlang.OtpErlangTuple;
import pl.mzlnk.erlchatter.ddd.user.User;
import pl.mzlnk.erlchatter.ddd.utils.ErlangTupleCreator;

import java.util.stream.Stream;

public abstract class BaseAuthorizedNetworkRequest extends BaseNetworkRequest {

    private User user;
    private RequestTypeEnum atom;

    public BaseAuthorizedNetworkRequest(User user, RequestTypeEnum atom) {
        super(atom);
        this.user = user;
    }

    @Override
    public OtpErlangTuple toTuple() {
        return ErlangTupleCreator.toTuple(
                Stream.concat(
                        Stream.of(this.atom, this.user.getToken()),
                        Stream.of(args())
                ).toArray()
        );
    }
}
