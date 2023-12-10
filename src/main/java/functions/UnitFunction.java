package functions;

@SimpleFunction(name="Единичная функция", order=10)
public class UnitFunction extends ConstantFunction
{
    public UnitFunction()
    {
        super(1);
    }
}