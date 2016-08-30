package com.stevesouza.aspectj.javastyle.nativestyle.structure;

/**
 * Created by stevesouza on 6/23/15.
 */
public interface ExtraInfo {
    public void setExtraInfo(String extraInfo);
    public String getExtraInfo();

//    static aspect ExtraInfoAspect {
//        private String ExtraInfo.extraInfo;
//
//        public void ExtraInfo.setExtraInfo(String extraInfo) {
//            this.extraInfo = extraInfo;
//        }
//
//        public String ExtraInfo.getExtraInfo() {
//            return this.extraInfo;
//        }
//    }
}
