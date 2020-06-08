package pl.mzlnk.erlchatter.ddd.network;

import com.ericsson.otp.erlang.*;
import pl.mzlnk.erlchatter.ddd.network.request.NetworkRequest;
import pl.mzlnk.erlchatter.ddd.network.response.*;
import pl.mzlnk.erlchatter.ddd.utils.OtpErlangObjectDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NetworkService {

    private static final String COOKIE = "erlchatter-ahHETnc1947HD981wQ";

    private final UUID nodeUuid;

    private OtpNode node;
    private OtpErlangPid pid;
    private OtpMbox mailBox;

    private List<NetworkResponseObserver> responseObservers = new ArrayList<>();
    private volatile boolean running;

    public NetworkService() {
        this.nodeUuid = UUID.randomUUID();
    }

    public void start() throws IOException {
        this.node = new OtpNode(this.nodeUuid.toString(), COOKIE);
        this.mailBox = this.node.createMbox();
        this.pid = this.mailBox.self();

        this.running = true;
        new Thread(new ResponseReceiveTask()).start();
    }

    public void stop() {
        this.running = false;
    }

    public void addResponseObserver(NetworkResponseObserver observer) {
        this.responseObservers.add(observer);
    }

    public void removeResponseObserver(NetworkResponseObserver observer) {
        this.responseObservers.remove(observer);
    }

    public void sendRequest(NetworkRequest request) {

    }

    private class ResponseReceiveTask implements Runnable {

        @Override
        public void run() {
            while(true) {
                if(!NetworkService.this.running) {
                    NetworkService.this.mailBox.close();
                    NetworkService.this.node.close();

                    return;
                }

                try {
                    OtpErlangObject response = mailBox.receive();

                    if(response instanceof OtpErlangTuple tuple) {
                        ResponseTypeEnum responseType = OtpErlangObjectDto.fromObject(tuple.elementAt(0)).getResponseTypeEnumValue();

                        switch (responseType) {
                            case ERROR -> responseObservers.forEach(o -> o.onResponse(ErrorResponse.fromTuple(tuple)));
                            case SIGN_IN -> responseObservers.forEach(o -> o.onResponse(SignInResponse.fromTuple(tuple)));
                            case SIGN_UP -> responseObservers.forEach(o -> o.onResponse(SignUpResponse.fromTuple(tuple)));
                            case SIGN_OUT -> responseObservers.forEach(o -> o.onResponse(new SignOutResponse()));
                            case MESSAGE_TO -> responseObservers.forEach(o -> o.onResponse(MessageToResponse.fromTuple(tuple)));
                            case MESSAGE_ALL -> responseObservers.forEach(o -> o.onResponse(MessageAllResponse.fromTuple(tuple)));
                        }
                    }
                } catch (OtpErlangExit | OtpErlangDecodeException otpErlangExit) {
                    otpErlangExit.printStackTrace();
                }
            }
        }

    }

}
