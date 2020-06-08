package pl.mzlnk.erlchatter.ddd.network.request;

import pl.mzlnk.erlchatter.ddd.user.User;

public class CommandRequest extends BaseAuthorizedNetworkRequest {

    private String command;

    public CommandRequest(String command, User user) {
        super(user, RequestTypeEnum.COMMAND);
        this.command = command;
    }

    @Override
    protected Object[] args() {
        return new Object[]{this.command};
    }

}
