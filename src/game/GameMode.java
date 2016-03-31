package game;

public enum GameMode
{
    HUMAN_VS_HUMAN,
    HUMAN_VS_AI,
    AI_VS_AI;
    
    //TODO: rename this method [getStringRepresentations,getValuesLikeStrings, ...] 
    public static String[] getValues()
    {
        String[] result = new String[3];
        GameMode[] values = GameMode.values();

        result[0] = values[0].toString();
        result[1] = values[1].toString();
        result[2] = values[2].toString();

        return result;
    }
}
