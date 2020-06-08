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

    private static final String COOKIE = "erlangcookie";
    private static final String SERVER_NAME = "erlchatter_gen_server";

    private final UUID nodeUuid;

//    private OtpNode node;
//    private OtpErlangPid pid;
//    private OtpMbox mailBox;

    private OtpSelf client;
    private OtpPeer server;
    private OtpConnection connection;

    private List<NetworkResponseObserver> responseObservers = new ArrayList<>();
    private volatile boolean running;

    public NetworkService() {
        this.nodeUuid = UUID.randomUUID();
    }

    public void start() throws IOException, OtpAuthException {
//        this.node = new OtpNode(this.nodeUuid.toString(), COOKIE);
//        this.mailBox = this.node.createMbox();
//        this.pid = this.mailBox.self();
//
//        if(this.node.ping(SERVER_NAME, 10000)) {
//            this.running = true;
//            new Thread(new ResponseReceiveTask()).start();
//
//            System.out.println("Connected to server");
//        } else {
//            System.out.println("Could not connect to server");
//        }

        client = new OtpSelf("chatClient", COOKIE);
        server = new OtpPeer("chatServer");
        connection = client.connect(server);

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
        try {
            connection.sendRPC("erlchatter_gen_server", request.getRequestType().toString().toLowerCase(), request.getArgs());
            System.out.println("Request sent");
        } catch (IOException e) {
            System.out.println("Could not send request");
        }
        // mailBox.send(SERVER_NAME, this.nodeUuid.toString(), request.toTuple());
    }

    public void sendTestRequest() {
        try {
            connection.sendRPC("erlchatter_gen_server", "test", new OtpErlangList(new OtpErlangObject[] {new OtpErlangString("test message")}));
        } catch (IOException e) {
            System.out.println("Could not send request");
        }
    }

    private class ResponseReceiveTask implements Runnable {

        @Override
        public void run() {
            while(true) {
                if(!NetworkService.this.running) {
                    connection.close();

                    return;
                }

                try {
                    OtpErlangObject response = ((OtpErlangTuple) connection.receiveMsg().getMsg()).elementAt(1);
                    System.out.println("Response: " + response);

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
                } catch (OtpErlangExit | IOException | OtpAuthException | OtpErlangDecodeException otpErlangExit) {
                    otpErlangExit.printStackTrace();
                }
            }
        }

    }

}
