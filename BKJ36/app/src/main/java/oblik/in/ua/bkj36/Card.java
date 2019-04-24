package oblik.in.ua.bkj36;

public class Card {

    private String nameCard;
    private String suitCard;
    private int valueCard;

    public Card(String nameCard, String suitCard, int valueCard) {
        this.nameCard = nameCard;
        this.suitCard = suitCard;
        this.valueCard = valueCard;
    }
    public Card() { }

    public String getNameCard() { return nameCard; }
    public void setNameCard(String nameCard) { this.nameCard = nameCard; }

    public String getSuitCard() { return suitCard; }
    public void setSuitCard(String suitCard) { this.suitCard = suitCard; }

    public int getValueCard() { return valueCard; }
    public void setValueCard(int valueCard) { this.valueCard = valueCard; }

    @Override
    public String toString() { return " "+ nameCard + suitCard; }
}
