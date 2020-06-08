package pl.mzlnk.erlchatter.ddd.utils;

import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpErlangString;
import com.ericsson.otp.erlang.OtpErlangTuple;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ErlangTupleCreatorTest {

    @Test
    void toTupleTest() {
        // given
        String s1 = "s1";
        String s2 = "s2";
        String s3 = "s3";

        OtpErlangObject obj1 = new OtpErlangString(s1);
        OtpErlangObject obj2 = new OtpErlangString(s2);
        OtpErlangObject obj3 = new OtpErlangString(s3);

        OtpErlangTuple expectedTuple = new OtpErlangTuple(new OtpErlangObject[]{obj1, obj2, obj3});

        // when

        // then
        assertEquals(expectedTuple, ErlangTupleCreator.toTuple(Stream.concat(Stream.of(s1), Stream.of(s2, s3)).toArray()));
    }

}