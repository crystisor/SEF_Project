public class Customer
{
    private String name;
    private String address;
    private String phoneNumber;
    private String ID;

    public Customer(String name, String address, String phoneNumber, String ID)
    {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.ID = ID;
    }
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }
}
