package models;

public class User {
    private final Integer userId;
    private final String userGuid;
    private final String userName;

    public User(Integer userId, String userGuid, String userName) {
        this.userGuid = userGuid;
        this.userId = userId;
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public String getUserName() {
        return userName;
    }
}
