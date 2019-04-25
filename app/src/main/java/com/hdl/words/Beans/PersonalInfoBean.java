package com.hdl.words.Beans;

public class PersonalInfoBean {


    /**
     * data : {"hometown":"四川-南充","sex":1,"name":"何栋梁","sign":"与我为敌,就当这般生不如死!","birth":"1998-03-12","id":1,"username":"15508060795"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * hometown : 四川-南充
         * sex : 1
         * name : 何栋梁
         * sign : 与我为敌,就当这般生不如死!
         * birth : 1998-03-12
         * id : 1
         * username : 15508060795
         */

        private String hometown;
        private int sex;
        private String name;
        private String sign;
        private String birth;
        private int id;
        private String username;

        public String getHometown() {
            return hometown;
        }

        public void setHometown(String hometown) {
            this.hometown = hometown;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
