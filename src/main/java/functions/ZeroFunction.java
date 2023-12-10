package functions;

@SimpleFunction(name="Нулевая функция", order=11)
public class ZeroFunction extends ConstantFunction
{
    public ZeroFunction()
    {
        super(0);
    }
}