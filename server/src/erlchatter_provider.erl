-module(erlchatter_provider).
-author("mzlnk").

-include("erlchatter.hrl").

%% API
-export([
  userSignIn/4,
  userSignUp/3,
  userSignOut/2,
  listOnlineUsers/2,
  messageAll/3
]).

userSignUp(Login, Password, Chat) ->
  case erlchatter:userSignUp(Login, Password, Chat) of
    {error, Msg} -> {Chat, {error, Msg}};
    NewState -> {NewState, ok}
  end.

userSignIn(Login, Password, Pid, Chat) ->
  case erlchatter:userSignIn(Login, Password, Pid, Chat) of
    {error, Msg} -> {Chat, {error, Msg}};
    {Token, NewState} -> {NewState, {ok, Token}}
  end.

userSignOut(Token, Chat) ->
  case erlchatter:getUserLoginFromToken(Token, Chat) of
    {error, Msg} -> {Chat, {error, Msg}};
    Login ->
      case erlchatter:userSignOut(Login, Chat) of
        {error, Msg} -> {Chat, {error, Msg}};
        NewState -> {NewState, ok}
      end
  end.

listOnlineUsers(Token, Chat) ->
  case erlchatter:validateToken(Token, Chat) of
    invalid -> {Chat, {error, "Invalid token"}};
    valid -> {Chat, erlchatter:listOnlineUsers(Chat)}
  end.

messageAll(Token, Message, Chat) ->
  case erlchatter:getUserLoginFromToken(Token, Chat) of
    {error, Msg}-> {error, Msg};
    Login ->
      lists:foreach(
        fun(User) ->
          User#user.pid ! {message, Message, Login, calendar:local_time()} % todo: to inspect
        end,
        erlchatter:listOnlineUsers(Chat)
      )
  end.


