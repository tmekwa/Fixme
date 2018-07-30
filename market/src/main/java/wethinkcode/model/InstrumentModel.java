package wethinkcode.model;

public class InstrumentModel
{
    private int _id;
    private int _price;
    private int _quantity;
    private int _instrumentType;

    public int get_id() {
        return _id;
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

    public int get_instrumentType() {
        return _instrumentType;
    }

    public void set_instrumentType(int instrumentType) {
        this._instrumentType = instrumentType;
    }
}
