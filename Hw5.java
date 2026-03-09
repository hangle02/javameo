//package com.gradescope.cs201;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
interface Hw5_interface {
    public boolean check_balanced_parenthesis_str(String s);
    public int estimate_visit_time(String[][] patient_procedures);
    public String[] find_word_list(String sentence);
    
}

public class Hw5 implements Hw5_interface {
   public boolean check_balanced_parenthesis_str(String s){
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

   private static int getRoomId(String proc) {
    //helper function to quickly get room priority
        if (proc.equals("PrimaryCare_1")) return 0;
        if (proc.equals("PrimaryCare_2")) return 1;
        if (proc.equals("X-ray"))         return 2;
        if (proc.equals("Lab test"))      return 3;
        if (proc.equals("Pharmacy"))      return 4;
        return -1;
    }

   public int estimate_visit_time(String[][] patient_procedures){
        PriorityQueue<Integer>[] roomQueues = new PriorityQueue[5];

        int[] fix_duration ={10,10,4,8,7};
        
        int n = patient_procedures.length;

        //Patient status check
        int[] queueArrivalTime = new int[n];
        boolean[] isDone = new boolean[n];
        int[] nextFreeTime = new int[n]; 
        int[] step = new int[n];        
        int[] prevRoom = new int[n];     
        for (int i = 0; i < n; i++) {prevRoom[i] = 5; }

        for (int i = 0; i < 5; i++) {
            roomQueues[i] = new PriorityQueue<>((p1, p2) -> {
                if (queueArrivalTime[p1] != queueArrivalTime[p2]) {
                    return Integer.compare(queueArrivalTime[p1], queueArrivalTime[p2]);
                }
                if (prevRoom[p1] != prevRoom[p2]) {
                    //room visited priority
                    return Integer.compare(prevRoom[p1], prevRoom[p2]); 
                }
                // Time arrived priority
                return Integer.compare(p1, p2); 
            });
        }

        int[] roomFreeTime = new int[5]; 
        
        int time = 0;          //total time
        int doneCount = 0;      // Number of patients that finished all the check
        int lastPatientTime = 0; 

        while (doneCount<n) {
            for(int patient = 0; patient<n; patient++){
                if (!isDone[patient] && nextFreeTime[patient] ==time) {
                    //check if finished 6 step or all the remaining step = ""
                    if(step[patient]>=6 || patient_procedures[patient][step[patient]].equals("")){
                        isDone[patient]=true;
                        doneCount++;
                        if (patient==n-1){ lastPatientTime=time;}
                    }
                    else{
                        String room = patient_procedures[patient][step[patient]];
                        int neededRoom = getRoomId(room);
                        queueArrivalTime[patient] = time;
                        roomQueues[neededRoom].offer(patient);
                        nextFreeTime[patient] = Integer.MAX_VALUE;
                    }
                }
            } 
            for (int r = 0; r < 5; r++) {
                if (roomFreeTime[r] <= time && !roomQueues[r].isEmpty()) {
                    
                    int patientToServe = roomQueues[r].poll(); 
                    
                    // Update  status
                    roomFreeTime[r] = time + fix_duration[r];         
                    nextFreeTime[patientToServe] = time + fix_duration[r];
                    prevRoom[patientToServe] = r;                     
                    step[patientToServe]++;                           
                }
            }

            time++;
        }
       

        return lastPatientTime;
    
   }

   public String[] find_word_list(String sentence){
    String[] s = sentence.split(" ");
    Queue<String> words = new LinkedList<>();
    for(String word: s){
        if(!words.contains(word)){
            words.offer(word);}
    }
    String[] r = new String[words.size()];
    for(int i =0; i< r.length; i++){
        r[i]=words.poll();

    }
    return r;
   }
  

   public static void main(String[] args) {
//     Hw5 tmp = new Hw5();
//     String[][] patients_4 = {
// {"PrimaryCare_1", "X-ray", "PrimaryCare_1", "Pharmacy", "", ""},
// {"PrimaryCare_2", "Pharmacy", "", "", "", ""},
// {"PrimaryCare_2", "Lab test", "PrimaryCare_2", "Pharmacy", "", ""},
// {"PrimaryCare_2", "Lab test", "X-ray", "PrimaryCare_2", "Pharmacy", ""},
// {"PrimaryCare_1", "Pharmacy", "", "", "", ""},
// {"PrimaryCare_1", "Lab test", "PrimaryCare_1", "Pharmacy", "", ""}};
// System.out.println(tmp.estimate_visit_time(patients_4)); // returns 61
    }
}