package pl.mzlnk.erlchatter.ddd.network.request;

import com.ericsson.otp.erlang.OtpErlangTuple;

public interface NetworkRequest {

    OtpErlangTuple toTuple();

}
