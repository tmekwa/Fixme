package wethinkcode.model;

public class BrokerModel
{
    private int _id;
    private int _brokerId;
    private int _instrumentId;



    public int getId()
    {
        return _id;
    }

    public void setBrokerId(int brokerId)
    {
        _brokerId = brokerId;
    }

    public int getBrokerId()
    {
        return _brokerId;
    }


    public int get_instrumentId() {
        return _instrumentId;
    }

    public void set_instrumentId(int _instrumentId) {
        this._instrumentId = _instrumentId;
    }
}
