package cn.ucai.fulicenter.model.bean;

/**
 * Created by yu chen on 2017/1/9.
 */

public class User {

    /**
     * retCode : 0
     * retMsg : true
     * retData : {"muserName":"a123456","muserNick":"1234563","mavatarId":245,"mavatarPath":"user_avatar","mavatarSuffix":".jpg","mavatarType":0,"mavatarLastUpdateTime":"1477446355442"}
     */

    private int retCode;
    private boolean retMsg;
    private RetDataBean retData;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public boolean isRetMsg() {
        return retMsg;
    }

    public void setRetMsg(boolean retMsg) {
        this.retMsg = retMsg;
    }

    public RetDataBean getRetData() {
        return retData;
    }

    public void setRetData(RetDataBean retData) {
        this.retData = retData;
    }

    public static class RetDataBean {
        /**
         * muserName : a123456
         * muserNick : 1234563
         * mavatarId : 245
         * mavatarPath : user_avatar
         * mavatarSuffix : .jpg
         * mavatarType : 0
         * mavatarLastUpdateTime : 1477446355442
         */

        private String muserName;
        private String muserNick;
        private int mavatarId;
        private String mavatarPath;
        private String mavatarSuffix;
        private int mavatarType;
        private long mavatarLastUpdateTime;

        public String getMuserName() {
            return muserName;
        }

        public void setMuserName(String muserName) {
            this.muserName = muserName;
        }

        public String getMuserNick() {
            return muserNick;
        }

        public void setMuserNick(String muserNick) {
            this.muserNick = muserNick;
        }

        public int getMavatarId() {
            return mavatarId;
        }

        public void setMavatarId(int mavatarId) {
            this.mavatarId = mavatarId;
        }

        public String getMavatarPath() {
            return mavatarPath;
        }

        public void setMavatarPath(String mavatarPath) {
            this.mavatarPath = mavatarPath;
        }

        public String getMavatarSuffix() {
            return mavatarSuffix;
        }

        public void setMavatarSuffix(String mavatarSuffix) {
            this.mavatarSuffix = mavatarSuffix;
        }

        public int getMavatarType() {
            return mavatarType;
        }

        public void setMavatarType(int mavatarType) {
            this.mavatarType = mavatarType;
        }

        public long getMavatarLastUpdateTime() {
            return mavatarLastUpdateTime;
        }

        public void setMavatarLastUpdateTime(long mavatarLastUpdateTime) {
            this.mavatarLastUpdateTime = mavatarLastUpdateTime;
        }

        @Override
        public String toString() {
            return "RetDataBean{" +
                    "muserName='" + muserName + '\'' +
                    ", muserNick='" + muserNick + '\'' +
                    ", mavatarId=" + mavatarId +
                    ", mavatarPath='" + mavatarPath + '\'' +
                    ", mavatarSuffix='" + mavatarSuffix + '\'' +
                    ", mavatarType=" + mavatarType +
                    ", mavatarLastUpdateTime=" + mavatarLastUpdateTime +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "retCode=" + retCode +
                ", retMsg=" + retMsg +
                ", retData=" + retData +
                '}';
    }
}
