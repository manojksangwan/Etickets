package in.gov.hartrans.etickets.Models;

/**
 * Created by manojK on 05/12/2016.
 */
public class wibmoResponse {
    String resCode, resDesc, wPayTxnId, merAppData, merTxnId, msgHash, dataPickUpCode;

    public wibmoResponse(String resCode, String resDesc, String wPayTxnId, String merAppData, String merTxnId, String msgHash, String dataPickUpCode) {
        this.resCode = resCode;
        this.resDesc = resDesc;
        this.wPayTxnId = wPayTxnId;
        this.merAppData = merAppData;
        this.merTxnId = merTxnId;
        this.msgHash = msgHash;
        this.dataPickUpCode = dataPickUpCode;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public String getwPayTxnId() {
        return wPayTxnId;
    }

    public void setwPayTxnId(String wPayTxnId) {
        this.wPayTxnId = wPayTxnId;
    }

    public String getMerAppData() {
        return merAppData;
    }

    public void setMerAppData(String merAppData) {
        this.merAppData = merAppData;
    }

    public String getMerTxnId() {
        return merTxnId;
    }

    public void setMerTxnId(String merTxnId) {
        this.merTxnId = merTxnId;
    }

    public String getMsgHash() {
        return msgHash;
    }

    public void setMsgHash(String msgHash) {
        this.msgHash = msgHash;
    }

    public String getDataPickUpCode() {
        return dataPickUpCode;
    }

    public void setDataPickUpCode(String dataPickUpCode) {
        this.dataPickUpCode = dataPickUpCode;
    }
}
