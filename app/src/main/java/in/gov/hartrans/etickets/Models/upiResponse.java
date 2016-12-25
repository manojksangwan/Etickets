package in.gov.hartrans.etickets.Models;

/**
 * Created by manojK on 25/12/2016.
 */
public class upiResponse {
    String pgMetrnRefNo, orderNo, TxnAmount, trnAuthDate, approvalCode, payerVA, npciTxnID, refID, status, statusDesc,responseCode;

    public upiResponse(String pgMetrnRefNo, String orderNo, String txnAmount, String trnAuthDate, String approvalCode, String payerVA, String npciTxnID, String refID, String status, String statusDesc, String responseCode) {
        this.pgMetrnRefNo = pgMetrnRefNo;
        this.orderNo = orderNo;
        TxnAmount = txnAmount;
        this.trnAuthDate = trnAuthDate;
        this.approvalCode = approvalCode;
        this.payerVA = payerVA;
        this.npciTxnID = npciTxnID;
        this.refID = refID;
        this.status = status;
        this.statusDesc = statusDesc;
        this.responseCode = responseCode;
    }

    public String getPgMetrnRefNo() {
        return pgMetrnRefNo;
    }

    public void setPgMetrnRefNo(String pgMetrnRefNo) {
        this.pgMetrnRefNo = pgMetrnRefNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTxnAmount() {
        return TxnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        TxnAmount = txnAmount;
    }

    public String getTrnAuthDate() {
        return trnAuthDate;
    }

    public void setTrnAuthDate(String trnAuthDate) {
        this.trnAuthDate = trnAuthDate;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getPayerVA() {
        return payerVA;
    }

    public void setPayerVA(String payerVA) {
        this.payerVA = payerVA;
    }

    public String getNpciTxnID() {
        return npciTxnID;
    }

    public void setNpciTxnID(String npciTxnID) {
        this.npciTxnID = npciTxnID;
    }

    public String getRefID() {
        return refID;
    }

    public void setRefID(String refID) {
        this.refID = refID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
