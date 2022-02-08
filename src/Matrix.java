import java.util.InputMismatchException;

public class Matrix {
    double[] values;

    public Matrix(double[] values){
        if(Math.sqrt(values.length) != (int) Math.sqrt(values.length)){
            throw new InputMismatchException();
        }

        this.values = values;
    }
}
