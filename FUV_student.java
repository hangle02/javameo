public class FUV_student{
    public String first_name; //fields or attributes
    public String last_name;
    public String student_id;
    FUV_student(String first_name, String  last_name, String  student_id){
        this.first_name = first_name;
        this.last_name = last_name;
        this.student_id = student_id;}

    public String get_FUV_email(){
        // Example format: firstname.lastname@student.fulbright.edu.vn
        return this.first_name + "." 
            + this.last_name +"."+ this.student_id  
            + "@student.fulbright.edu.vn";}
    
    public static void main(String[] args) {
        FUV_student s = new FUV_student("Khoi", "Bui", "123456");
        IO.print(s.get_FUV_email());
    }        
    }


