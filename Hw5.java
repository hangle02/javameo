//package com.gradescope.cs201;
interface Hw5_interface {
    public boolean check_balanced_parenthesis_str(String s);
    public int estimate_visit_time(String[][] patient_procedures);
    public String[] find_word_list(String sentence);
}

public class Hw5 {
   public static boolean check_balanced_parenthesis_str(String s){
    int count = 0;
    for (int i=0; i<s.length(); i++){
        char c = s.charAt(i);
        if(c=='('){
            count++;
        }
        if(s.charAt(i)==')'){
            count--;
        }
        if(count<0){
            return false;
        }
        }
    if(count ==0){return true;}
    return false;
   } 

   public static int estimate_visit_time(String[][] patient_procedures){
    return 0;
   }

   public static String[] find_word_list(String sentence){
    String[] a = new String[0];
    return a;
   }

   public static void main(String[] args) {
   
}
}
