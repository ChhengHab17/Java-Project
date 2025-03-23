package Main;

public class Session {
    private static int loggedInUserId = -1;  // Default: No user logged in

    public static void setUserId(int userId) {
        loggedInUserId = userId;
    }

    public static int getUserId() {
        return loggedInUserId;
    }

    public static boolean isUserLoggedIn() {
        return loggedInUserId != -1;
    }
}
