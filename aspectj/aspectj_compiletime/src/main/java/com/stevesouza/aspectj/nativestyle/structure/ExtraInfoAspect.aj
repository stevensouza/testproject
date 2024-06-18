package com.stevesouza.aspectj.nativestyle.structure;

/**
 * This aspect introduces a static crosscutting implementation of an interface to
 * CustomerClass. Note the code runs in intellij although it shows a compiler error
 * in the IDE.
 */
public aspect ExtraInfoAspect {
    declare parents : CustomerClass implements ExtraInfo;

    private String ExtraInfo.extraInfo;

    public void ExtraInfo.setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
    public String ExtraInfo.getExtraInfo() {
        return this.extraInfo;
    }
}
