package in.gov.hartrans.etickets.Models;

/**
 * Created by manojK on 25/12/2016.
 */
public class appStatus {
    private boolean appAllowBooking, allowCCDC, allowUPI;
    private String appMessagem, appVersion;

    public appStatus(boolean appAllowBooking, boolean allowCCDC, boolean allowUPI, String appMessagem, String appVersion) {
        this.appAllowBooking = appAllowBooking;
        this.allowCCDC = allowCCDC;
        this.allowUPI = allowUPI;
        this.appMessagem = appMessagem;
        this.appVersion = appVersion;
    }

    public boolean isAppAllowBooking() {
        return appAllowBooking;
    }

    public void setAppAllowBooking(boolean appAllowBooking) {
        this.appAllowBooking = appAllowBooking;
    }

    public boolean isAllowCCDC() {
        return allowCCDC;
    }

    public void setAllowCCDC(boolean allowCCDC) {
        this.allowCCDC = allowCCDC;
    }

    public boolean isAllowUPI() {
        return allowUPI;
    }

    public void setAllowUPI(boolean allowUPI) {
        this.allowUPI = allowUPI;
    }

    public String getAppMessagem() {
        return appMessagem;
    }

    public void setAppMessagem(String appMessagem) {
        this.appMessagem = appMessagem;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
