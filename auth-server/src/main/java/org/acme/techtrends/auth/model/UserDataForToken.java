package org.acme.techtrends.auth.model;

public class UserDataForToken {
    private Long userId;
    private String userType;

    public UserDataForToken(Long userId, String userType) {
        this.userId = userId;
        this.userType = userType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}