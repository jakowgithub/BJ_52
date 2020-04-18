package oblik.in.ua.bj_52;

public class Card {

    private String nameCard;
    private String suitCard;
    private int valueCard;

    public Card(String nameCard, String suitCard, int valueCard) {
        this.nameCard = nameCard;
        this.suitCard = suitCard;
        this.valueCard = valueCard;
    }

    public int getValueCard() { return valueCard; }

    @Override
    public String toString() { return nameCard + suitCard; }

}
