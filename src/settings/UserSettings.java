package settings;

public class UserSettings {

    private static int gameVolume = 100;
    private static boolean isFullScreenEnabled = false;
    private static boolean isVSyncEnabled = false;


    public static int getGameVolume() {
        return gameVolume;
    }

    public static void setGameVolume(int gameVolume) {
        UserSettings.gameVolume = gameVolume;
    }

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
