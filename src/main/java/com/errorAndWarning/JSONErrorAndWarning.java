package com.errorAndWarning;

public class JSONErrorAndWarning {
    public int badArg = 0;
    public int warning = 0;
    public String badArgMsg = "";
    public String warningMsg = "";
    public void setBadArg(String e) {}
    public void setWarning(String w) {}
    public boolean hasBadArg() {
        return this.badArg > 0;
    }
    public boolean hasWarning() {
        return this.warning > 0;
    }
}
