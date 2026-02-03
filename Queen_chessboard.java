import java.util.Arrays;
public class Queen_chessboard {
// 1. Hàm đệ quy
    public static int solution_num=0;
    public static void solve_n_queen_puzzle (int n, int[] queen_position, int pos){
        //solve the nxn queen puzzle
        //n: chessboard size
        //queen_position[i] = is the row of ith col of the ith queen
        //pos = num of queen that already placed on the board
        if(pos == n){
                System.out.println(Arrays.toString(queen_position));
            solution_num++;
            System.exit(0);
        }
        else{//other wise
            //take the next decision: place the (pos +1)th queen on the board
            //available choice: n
            for (int row = 0; row < n; row++){
                //form a new partial candidate
                queen_position[pos] = row;
                //check
                boolean is_candidate_ok = true;
                for( int first_queen_col=0; first_queen_col<= pos; first_queen_col++){
                    for(int second_queen_col=first_queen_col+1; second_queen_col<= pos; second_queen_col++){
                        //check if 2 q on the same row
                        if(queen_position[first_queen_col]==queen_position[second_queen_col]){
                            is_candidate_ok = false;
                            break;
                        }
                        //check if 2q on the same diagonal
                        if((queen_position[first_queen_col]-first_queen_col)==(queen_position[second_queen_col]-second_queen_col)){
                            is_candidate_ok = false;
                            break;
                        }
                        //check if 2q on the same counter-diagonal
                        if((queen_position[first_queen_col]+first_queen_col)==(queen_position[second_queen_col]+second_queen_col)){
                            is_candidate_ok = false;
                            break;
                        }
                    }
                    if(!is_candidate_ok){ break;}
                }

                //if the new candidate is ok, then use recursion
                if(is_candidate_ok){
                    solve_n_queen_puzzle(n,queen_position, pos+1);
                }
            }

        }
    }

    // 2. Hàm gọi
    public static void solve_n_queen_puz(int n){
        int[] tmp = new int[n];
        solve_n_queen_puzzle(n, tmp, 0);
    }


    // 3. LỆNH CHẠY (Gõ dòng này thì nó mới in ra!)
    public static void main(String[] args) {
        solve_n_queen_puz(8);
        System.out.println(solution_num);
    }
    
}
