//package com.gradescope.cs201;
import java.util.Arrays;

public class Poker_hand_hw2 {
    final String[] suit1 = {"H", "D", "C", "S"};
    final String[] rank1 = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    
    private String[] cards_;
    private int[] rankIndices = new int[5]; 
    private int[] suitIndices = new int[5]; 

    public Poker_hand_hw2(String[] cards2) {
        if (cards2 == null || cards2.length != 5) {
            throw new IllegalArgumentException();
        }
       
        this.cards_ = cards2;
        
        for(int i = 0; i < 5; i++){
            if(!parseAndStore(cards2[i], i)){
                throw new IllegalArgumentException();
            }
        }
        
        Arrays.sort(rankIndices);
    }

    private int search_index(String[] elements, String key) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    
    private boolean parseAndStore(String card, int index){
        if(card.length() < 2) return false;

        
        String s = card.substring(card.length() - 1);
        
        String r = card.substring(0, card.length() - 1);

        int rIdx = search_index(rank1, r);
        int sIdx = search_index(suit1, s);

        if(rIdx == -1 || sIdx == -1) return false;

        this.rankIndices[index] = rIdx; // 0..12
        this.suitIndices[index] = sIdx; // 0..3
        return true;
    }


    private int[] get_rank_counts(){
        int[] counts = new int[13]; // 13 ranks
        for(int r : rankIndices){
            counts[r]++;
        }
        return counts;
    }

    private boolean is_Flush(){
        int firstSuit = suitIndices[0];
        for(int i=1; i<5; i++){
            if(suitIndices[i] != firstSuit) return false;
        }
        return true;
    }

    private boolean is_Straight(){
        if(rankIndices[0]==0 && rankIndices[1]==1 && rankIndices[2]==2 && 
           rankIndices[3]==3 && rankIndices[4]==12) return true;

        
        for(int i=0; i<4; i++){
            if(rankIndices[i+1] != rankIndices[i] + 1) return false;
        }
        return true;
    }

    
    public int get_category() {
        boolean flush = is_Flush();
        boolean straight = is_Straight();
        
        if(flush && straight) return 9; // Straight Flush
        
        int[] counts = get_rank_counts();
        boolean four = false, three = false, two = false;
        int pairs = 0;

        for(int c : counts){
            if(c == 4) four = true;
            if(c == 3) three = true;
            if(c == 2) {
                two = true;
                pairs++;
            }
        }

        if(four) return 8; // Four of a kind
        if(three && two) return 7; // Full House
        if(flush) return 6; // Flush
        if(straight) return 5; // Straight
        if(three) return 4; // Three of a kind
        if(pairs == 2) return 3; // Two pairs
        if(pairs == 1) return 2; // One pair
        
        return 1; // High card
    }
    private int[] get_ordered_ranks() {
    int[] ordered = new int[5]; 
    int[] counts = new int[15]; 
    
    
    for (int r : rankIndices) {
        counts[r]++;
    }

    int index = 0;
    
    for (int c = 1; c <= 4; c++) { 
        for (int r = 0; r < 15; r++) { 
            if (counts[r] == c) {
                for (int k = 0; k < c; k++) {
                    ordered[index] = r;
                    index++;
                }
            }
        }
    }
    int cat = get_category();
    if (cat == 5 || cat == 9) { 
        if (ordered[0] == 0 && ordered[4] == 12) { 
             return new int[]{ -1, 0, 1, 2, 3 };
        }
    }
    
    return ordered;
}

    
    public int compare_to(Poker_hand_hw2 other) {
    int cat1 = this.get_category();
    int cat2 = other.get_category();
    if (cat1 > cat2) return 1;
    if (cat1 < cat2) return -1;

    int[] myRanks = this.get_ordered_ranks();
    int[] otherRanks = other.get_ordered_ranks();
    
    for (int i = 4; i >= 0; i--) {
        if (myRanks[i] > otherRanks[i]) return 1;
        if (myRanks[i] < otherRanks[i]) return -1;
    }

    return 0; 
}

    public static void main(String[] args) {
       
}
}