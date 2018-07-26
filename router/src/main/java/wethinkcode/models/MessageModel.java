package wethinkcode.models;

public class MessageModel
{
    private String _to;
    private String _from;
    private String _message = null;

    public MessageModel(String from, String to)
    {
        this._from = from;
        this._to = to;
    }

    public String getTo() { return (this._to); };

    public String getFrom() { return (this._from); };

    public String getMessage() { return (this._message); }

    public void setMessage(String message)
    {
        this._message = message;
    }
}