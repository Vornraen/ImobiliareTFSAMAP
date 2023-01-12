package Model;

public class Property {
    private int id;
    private int price;
    private int roomNumber;
    private int size;
    private boolean sold;
    private int agentId;

    @Override
    public String toString() {
        return id  + ". " +
                 + price + "$ " +
                ", it has " + roomNumber + " rooms" +
                ", " + size + " square meters , is " +
                showSold() +
                ", in '" + neighbourhood + '\'' ;
    }

    private String neighbourhood;

    public Property(int id, int price, int roomNumber, int size, boolean sold, String neighbourhood,int agentId) {
        this.id = id;
        this.price = price;
        this.roomNumber = roomNumber;
        this.size = size;
        this.sold = sold;
        this.neighbourhood = neighbourhood;
        this.agentId=agentId;
    }

    public Property(int price, int roomNumber, int size, boolean sold, String neighbourhood,int agentId) {
        this.price = price;
        this.roomNumber = roomNumber;
        this.size = size;
        this.sold = sold;
        this.neighbourhood = neighbourhood;
        this.agentId=agentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String showSold(){
        if(this.sold==true){
            return "is sold";
        }
        else
        {
            return "not sold";
        }
    }
}