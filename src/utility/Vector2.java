package utility;


public class Vector2 <T>{

    public T x;
    public T y;

    Vector2(){}
    public Vector2(T val1, T val2){
        x = val1;
        y = val2;
    }
    public String returnVectorString(){
        return x + ", " + y;
    }


}
