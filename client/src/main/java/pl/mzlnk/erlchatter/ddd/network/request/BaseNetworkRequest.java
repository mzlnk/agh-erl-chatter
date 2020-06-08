package pl.mzlnk.erlchatter.ddd.network.request;

import com.ericsson.otp.erlang.OtpErlangTuple;
import lombok.AllArgsConstructor;
import pl.mzlnk.erlchatter.ddd.utils.ErlangTupleCreator;

import java.util.stream.Stream;

@AllArgsConstructor
public abstract class BaseNetworkRequest implements NetworkRequest {

    private RequestTypeEnum atom;

    protected abstract Object[] args();

    @Override
    public OtpErlangTuple toTuple() {
        return ErlangTupleCreator.toTuple(
                Stream.concat(
                        Stream.of(this.atom),
                        Stream.of(args())
                ).toArray()
        );
    }

}
