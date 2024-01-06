public class winnerReturnObject {
    boolean check;
    char symb;

    public winnerReturnObject(boolean c, char s){
        check = c;
        symb = s;
    }

    public winnerReturnObject(winnerReturnObject temp){
        check = temp.getCheck();
        symb = temp.getSymb();
    }

    public void setCheck(boolean c){
        check = c;
    }

    public void setSymb(char s){
        symb = s; 
    }

    public boolean getCheck(){
        return check;
    }

    public char getSymb(){
        return symb;
    }
}
