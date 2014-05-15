package model;

public enum PointState
{
    RED, BLUE, EMPTY;

    public static PointState getFromIntValue(int value)
    {
        PointState result;
        switch(value)
        {
            case 0:
                result = RED;
                break;
            case 1:
                result = BLUE;
                break;
            case 2:
                result = EMPTY;
                break;
            default:
                throw new IllegalArgumentException("Unexpected value:"+value); 
        }
        return result;
    }
}
