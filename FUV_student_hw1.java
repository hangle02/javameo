//package com.gradescope.cs201;
public class FUV_student_hw1 {
    private  String student_id, f_name, l_name;

    public FUV_student_hw1(String email){
    int len = email.length();
    String e_tail = email.substring((len-25));
    // student_id = email.substring(len-31, len-25);
    String prefix = email.substring(0, len-25);
    //Check for suffix
    if (!e_tail.equals( "@student.fulbright.edu.vn")){
        throw new IllegalArgumentException();
    }
    String[] parts = prefix.split("\\.");
    //Check if email has enough part
    if (parts.length !=3){
        throw new IllegalArgumentException();
    }
    String x = parts[0]; //cut for first name
    String y = parts[1]; //cut for last name
    String z = parts[2]; //cut for student id
    
    //check both first name and last name
    if(x.isEmpty() || y.isEmpty()){
        throw new IllegalArgumentException();
    }
    //Check for Student id using function
    if (!checkvalidStudentid(z)) {
        throw new IllegalArgumentException();
    }

    this.f_name = x;
    this.l_name = y;
    this.student_id = z;


    }
    //checking function for student id
    private boolean checkvalidStudentid(String stu_id){
        //Checking length (must be 6)
        if (stu_id.length() !=6){
            return false;
        }
        //Check if all char in student id is digit or not
        for (int i=0; i<stu_id.length(); i++){
            char c = stu_id.charAt(i);
            if (!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public String get_first_name(){
        return this.f_name;
    }
    public String get_last_name(){
        return this.l_name;
    }
    public String get_student_id(){
        return this.student_id;
    }
    

    public static void main(String[] args) {
    }

    
}
