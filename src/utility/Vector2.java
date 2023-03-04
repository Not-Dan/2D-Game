package utility;


import java.util.Objects;

public class Vector2 <T>{

    public T x;
    public T y;
    private int hashCode;

    Vector2(){}
    public Vector2(T val1, T val2){
        x = val1;
        y = val2;
        hashCode = Objects.hash(x,y);
    }
    public String returnVectorString(){
        return x + ", " + y;
    }



    //These two methods are necessary for our map (Hashmap)
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vector2 that = (Vector2) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

}
