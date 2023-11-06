package exceptions; // пакет исключений

public class ArrayIsNotSortedException extends RuntimeException
{
    public ArrayIsNotSortedException()
    {
        super();
    }

    public ArrayIsNotSortedException(String message)
    {
        super(message);
    }
}