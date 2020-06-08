package pl.mzlnk.erlchatter.ddd.network.response;

import com.ericsson.otp.erlang.OtpErlangTuple;
import lombok.Getter;
import pl.mzlnk.erlchatter.ddd.utils.OtpErlangObjectDto;

import java.util.Calendar;

@Getter
public class MessageAllResponse extends  BaseNetworkResponse {

    public static MessageAllResponse fromTuple(OtpErlangTuple tuple) {
        String from = OtpErlangObjectDto.fromObject(tuple.elementAt(1)).getStringValue();
        String message = OtpErlangObjectDto.fromObject(tuple.elementAt(2)).getStringValue();

        Calendar date = Calendar.getInstance();

        OtpErlangTuple dateTuple = (OtpErlangTuple) ((OtpErlangTuple) tuple.elementAt(3)).elementAt(0);
        OtpErlangTuple timeTuple = (OtpErlangTuple) ((OtpErlangTuple) tuple.elementAt(3)).elementAt(1);

        date.set(Calendar.YEAR, OtpErlangObjectDto.fromObject(dateTuple.elementAt(0)).getIntValue());
        date.set(Calendar.MONTH, OtpErlangObjectDto.fromObject(dateTuple.elementAt(1)).getIntValue());
        date.set(Calendar.DAY_OF_MONTH, OtpErlangObjectDto.fromObject(dateTuple.elementAt(2)).getIntValue());

        date.set(Calendar.HOUR, OtpErlangObjectDto.fromObject(timeTuple.elementAt(0)).getIntValue());
        date.set(Calendar.MINUTE, OtpErlangObjectDto.fromObject(timeTuple.elementAt(1)).getIntValue());
        date.set(Calendar.SECOND, OtpErlangObjectDto.fromObject(timeTuple.elementAt(2)).getIntValue());

        return new MessageAllResponse(from, message, date);
    }

    private String from;
    private String message;
    private Calendar date;

    public MessageAllResponse(String from, String message, Calendar date) {
        super(ResponseTypeEnum.MESSAGE_ALL);
        this.from = from;
        this.message = message;
        this.date = date;
    }

}
