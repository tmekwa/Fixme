package wethinkcode.messages;

public class SellMessages
{
    private int     _brokerId;
    private int     _marketId;
    private int     _price;
    private int     _quantity;
    private String  _market;
    private String  _instrument;


    public SellMessages(int brokerId, int marketId, String market, String instrument, int quantity, int price){

    }

    public int get_brokerId() {
        return _brokerId;
    }

    public int get_marketId() {
        return _marketId;
    }

    public int get_price() {
        return _price;
    }

    public void set_price(int price) {
        this._price = price;
    }

    public int get_quantity() {
        return _quantity;
    }

    public void set_quantity(int quantity) {
        this._quantity = quantity;
    }

    public String get_market() {
        return _market;
    }

    public void set_market(String market) {
        this._market = market;
    }

    public String get_instrument() {
        return _instrument;
    }

    public void set_instrument(String instrument) {
        this._instrument = instrument;
    }
}
