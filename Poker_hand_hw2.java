
public class Poker_hand_hw2{
    private String[] suit1 = {"H", "D", "C", "S"};
    private String[] rank1 = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private String[] cards_= new String[5]; 

    public Poker_hand_hw2(String[] cards){
        if (cards==null || cards.length != 5) {
            throw new IllegalArgumentException() ;        
        }
        if (!checkvalidcard(cards, suit1, rank1)){
            throw new IllegalArgumentException() ;
        }
        this.cards_ = cards;

    }
    private int search_index(String[] elements, String key){
        for (int i = 0; i<elements.length; i++){
            if(elements[i].equals(key)){
                return i;
            }
        }
        return -1;
    }
    private boolean checkvalidcard(String[] cards_set, String[] suit, String[] rank){
        int len = cards_set.length;
        for (int i=0; i< len; i++){
            String card = cards_set[i];
            if(search_index(rank, card.substring(0, 1))==-1){
                return false;
            }
            if(search_index(suit, card.substring(1))==-1){
                return false;
            } 
        }
        return true;
    }

    public int get_category(){

        return 1;
    }
    public static void main(String[] args) {
        
    }

} 