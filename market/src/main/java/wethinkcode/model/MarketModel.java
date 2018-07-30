package wethinkcode.model;

public class MarketModel
{
    private int     _id;
    private int     _marketId;
    private String  _market;
    private String  _marketType;
    private String  _instrumentID;


    public int get_id() {
        return _id;
    }

    public int get_marketId() {
        return _marketId;
    }

    public String get_market() {
        return _market;
    }

    public void set_market(String market) {
        this._market = market;
    }

    public String get_marketType() {
        return _marketType;
    }

    public void set_marketType(String marketType) {
        this._marketType = marketType;
    }

    public String get_intrumentID() {
        return _instrumentID;
    }

    public void set_intrumentID(String instrumentID) {
        this._instrumentID = instrumentID;
    }
}
