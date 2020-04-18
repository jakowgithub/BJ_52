package oblik.in.ua.gt36;

public class Card {

    private String nameCard;
    private String suitCard;
    private int valueCard;
    private int valueCardGT36;

    public Card(String nameCard, String suitCard, int valueCard, int valueCardGT36) {
        this.nameCard = nameCard;
        this.suitCard = suitCard;
        this.valueCard = valueCard;
        this.valueCardGT36=valueCardGT36;
    }
    public Card() { }

    public String getNameCard() { return nameCard; }
    public void setNameCard(String nameCard) { this.nameCard = nameCard; }

    public String getSuitCard() { return suitCard; }
    public void setSuitCard(String suitCard) { this.suitCard = suitCard; }

    public int getValueCard() { return valueCard; }
    public void setValueCard(int valueCard) { this.valueCard = valueCard; }

    public int getValueCardGT36() { return valueCardGT36; }
    public void setValueCardGT36(int valueCardGT36) { this.valueCardGT36 = valueCardGT36; }

    @Override
    public String toString() { return nameCard + suitCard; }
}

