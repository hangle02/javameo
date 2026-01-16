//package com.gradescope.cs201;
public class FUV_student_hw1 {
    public String email, student_id, e_tail, f_name, l_name;

    public FUV_student_hw1(String email){
    int len = email.length();
    e_tail = email.substring((len-25));
    student_id = email.substring(len-31, len-25);
    if(e_tail.equals( "@student.fulbright.edu.vn")){
        if (true) {
            
        }
    }
    else{IO.print("NO");}
        this.email = email;

    }
    public String get_first_name(){
        return this.email;
    }
    public static void main(String[] args) {
        
    }
}
