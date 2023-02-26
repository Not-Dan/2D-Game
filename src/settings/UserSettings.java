package settings;

public class UserSettings {

    private static boolean isFullScreenEnabled = false;
    private static boolean isVSyncEnabled = false;


    public static void setIsFullScreenEnabled(boolean enabled){
        isFullScreenEnabled = enabled;
    }
    public static boolean getIsFullScreenEnabled(){
        return isFullScreenEnabled;
    }

    public static void setIsVSyncEnabled(boolean enabled){
        isVSyncEnabled = enabled;
    }

    public static boolean getIsVSyncEnabled(){
        return isVSyncEnabled;
    }



}
