package wethinkcode.interfaces;

import wethinkcode.enums.MessageTypes;

public interface IMessage
{
    public void setType(MessageTypes type);
    public MessageTypes getType();
}
