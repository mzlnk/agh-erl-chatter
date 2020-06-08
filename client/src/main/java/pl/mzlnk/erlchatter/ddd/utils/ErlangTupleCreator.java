package pl.mzlnk.erlchatter.ddd.utils;

import com.ericsson.otp.erlang.*;

import java.util.stream.Stream;

public class ErlangTupleCreator {

    public static OtpErlangTuple toTuple(Object[] objects) {
        return new OtpErlangTuple(
                Stream.of(objects)
                        .map(obj -> {
                                    OtpErlangObject otpObject = null;

                                    if (obj instanceof String s) {
                                        otpObject = new OtpErlangString(s);
                                    }
                                    if (obj instanceof Long l) {
                                        otpObject = new OtpErlangLong(l);
                                    }
                                    if (obj instanceof Enum<?> e) {
                                        otpObject = new OtpErlangAtom(e.name().toLowerCase());
                                    }
                                    if (obj instanceof Double d) {
                                        otpObject = new OtpErlangDouble(d);
                                    }

                                    return otpObject;
                                }
                        )
                        .toArray(OtpErlangObject[]::new)

        );
    }

}
