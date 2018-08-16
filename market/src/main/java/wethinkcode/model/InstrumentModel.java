package wethinkcode.model;

public class InstrumentModel
{
    protected Long _id;
    private static Long _idCounter = 0L;
    private int _price;
    private int _quantity;
    private String _instrumentName;

    public InstrumentModel(String instrumentname, int quantity, int price)
    {
        this._id = nextId();
        this._instrumentName = instrumentname;
        this._quantity  = quantity;
        this._price = price;
    }

    private Long nextId() 
    {
        return (++_idCounter);    
    }

    public Long get_id() {
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

    public String get_Name() {
        return _instrumentName;
    }
}
