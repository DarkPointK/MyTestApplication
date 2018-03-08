package com.example.alphadog.mytestapplication.http.bean;

/**
 * Created by Xinxin on 2017/10/30.
 */

public class ResultLogin {
        private String rootDisctrctID;
        private int tenantID;
        private int state;
        private String accessToken;
        private String userName;
        private String userId;
        private String mobile;
        private String email;
        private String loginName;

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        private String errorMsg;

        public String getRootDisctrctID() {
            return rootDisctrctID;
        }

        public void setRootDisctrctID(String rootDisctrctID) {
            this.rootDisctrctID = rootDisctrctID;
        }

        public int getTenantID() {
            return tenantID;
        }

        public void setTenantID(int tenantID) {
            this.tenantID = tenantID;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }
}
