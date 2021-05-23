package com.errorAndWarning;

public class JSONEW_1 extends JSONErrorAndWarning{
    @Override
    public void setBadArg(String e) {
        this.badArg += 1;
        this.badArgMsg += e;
    }
    @Override
    public void setWarning(String w) {
        this.warning += 1;
        this.warningMsg += w;
    }
}
