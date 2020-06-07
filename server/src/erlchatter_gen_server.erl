-module(erlchatter_gen_server).
-author("mzlnk").

-behavior(gen_server).

-define(SERVER, ?MODULE).

%% API
-export([
  start_link/0,
  init/1,
  handle_call/3,
  handle_cast/2,
  handle_info/2,
  terminate/2,
  code_change/3
]).

start_link() ->
  gen_server:start_link({local, erlchatter_gen_server}, ?MODULE, [], []).

init([]) ->
  {ok, erlchatter:initChat()}.

handle_call({userSignUp, Login, Password}, _From, State) ->
  {UpdatedState, Reply} = erlchatter_provider:userSignUp(Login, Password, State),
  {reply, Reply, UpdatedState};

handle_call({userSignIn, Login, Password}, _From, State) ->
  {UpdatedState, Reply} = erlchatter_provider:userSignIn(Login, Password, State),
  {reply, Reply, UpdatedState};

handle_call({userSignOut, Token}, _From, State) ->
  {UpdatedState, Reply} = erlchatter_provider:userSignOut(Token, State),
  {reply, Reply, UpdatedState};

handle_call({listOnlineUsers, Token}, _From, State) ->
  {UpdatedState, Reply} = erlchatter_provider:listOnlineUsers(Token, State),
  {reply, Reply, UpdatedState}.

handle_cast({message, Token, Message}, State) ->
  erlchatter_provider:messageAll(Token, Message, State),
  {noreply, State}.

handle_info(_Info, State) ->
  {noreply, State}.

terminate(Reason, State) ->
  io:format("Server terminated. Reason: ~p~n", [Reason]),
  Reason.

code_change(_OldVsn, State, _Extra) ->
  {ok, State}.
