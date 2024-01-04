package vn.edu.hcmuaf.fit.bean;
import com.google.api.client.util.DateTime;

import java.sql.Timestamp;

public class Key {
    private int idU;
    private String pubkey;
    private Timestamp reportdate;
    private Timestamp addedDate;
    private int isblock;

    public Key(int idU, String pubkey, Timestamp reportdate, Timestamp addedDate,int isblock) {
        this.idU = idU;
        this.pubkey = pubkey;
        this.reportdate = reportdate;
        this.addedDate = addedDate;
        this.isblock = isblock;
    }

    public Key() {
    }

    public int getIsblock() {
        return isblock;
    }

    public void setIsblock(int isblock) {
        this.isblock = isblock;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }



    public void setReportdate(Timestamp reportdate) {
        this.reportdate = reportdate;
    }


    public Timestamp getReportdate() {
        return reportdate;
    }

    public Timestamp getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Timestamp addedDate) {
        this.addedDate = addedDate;
    }
    public boolean isPrivateKeySecure(Timestamp invoiceDate,Timestamp report) {
               return invoiceDate.after(report) && isblock == 0;
    }
}

