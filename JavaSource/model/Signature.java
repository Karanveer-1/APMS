package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "Signature")
public class Signature implements Serializable {
    @EmbeddedId
    private TimesheetPK timesheetPk;
    
    private byte[] signature;

    @Lob
    @Column(name = "publicKey", nullable = false, columnDefinition = "blob")
    private byte[] publicKey;
    
    public Signature() {}
    
    public Signature(byte[] sig, byte[] pubKey) {
        signature = sig;
        publicKey = pubKey;
    } 
    
    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public TimesheetPK getTimesheetPk() {
        return timesheetPk;
    }

    public void setTimesheetPk(TimesheetPK timesheetPk) {
        this.timesheetPk = timesheetPk;
    }

}
