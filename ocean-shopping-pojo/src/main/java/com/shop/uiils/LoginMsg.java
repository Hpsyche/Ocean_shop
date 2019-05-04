package com.shop.uiils;

import java.io.Serializable;

/**
 * @author Hpsyche
 */
public class LoginMsg implements Serializable {
        String msg;
        String password;

        public LoginMsg(String msg, String password) {
            this.msg = msg;
            this.password = password;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
}
