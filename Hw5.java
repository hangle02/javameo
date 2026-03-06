//package com.gradescope.cs201;
interface Hw5_interface {
    public boolean check_balanced_parenthesis_str(String s);
    public int estimate_visit_time(String[][] patient_procedures);
    public String[] find_word_list(String sentence);
}

public class Hw5 {
   public static boolean check_balanced_parenthesis_str(String s){
    return true;
   } 

   public static int estimate_visit_time(String[][] patient_procedures){
    return 0;
   }

   public static String[] find_word_list(String sentence){
    String[] a = new String[0];
    return a;
   }
   
   public static void main(String[] args) {
    String s = "((()))";
    System.out.println(check_balanced_parenthesis_str(s));
    String[][] patient_procedures = {{"X-ray", "MRI"}, {"Blood Test"}, {"Ultrasound", "CT Scan"}};
    System.out.println(estimate_visit_time(patient_procedures));
    String sentence = "Hello world! This is a test.";
    String[] word_list = find_word_list(sentence);
    for (String word : word_list) {
        System.out.println(word);
    }
   }
}
