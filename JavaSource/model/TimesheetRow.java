package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TimesheetRow")
public class TimesheetRow implements Serializable {

    @EmbeddedId
    private TimesheetRowPK timesheetRowPk;

    @Column(name = "Sat")
    private float sat;

    @Column(name = "Sun")
    private float sun;

    @Column(name = "Mon")
    private float mon;

    @Column(name = "Tue")
    private float tue;

    @Column(name = "Wed")
    private float wed;

    @Column(name = "Thu")
    private float thu;

    @Column(name = "Fri")
    private float fri;

    @Column(name = "Note")
    private String note;

    @Column(name = "State", nullable = false)
    private String state;

    @Column(name = "Comment")
    private String comment;

    public TimesheetRow() {
    }
    
    public TimesheetRow(TimesheetRowPK timesheetRowPk, float sat, float sun,
            float mon, float tue, float wed, float thu, float fri, String note,
            String state, String comment) {
        super();
        this.timesheetRowPk = timesheetRowPk;
        this.sat = sat;
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.note = note;
        this.state = state;
        this.comment = comment;
    }

    public TimesheetRowPK getTimesheetRowPk() {
        return timesheetRowPk;
    }

    public void setTimesheetRowPk(TimesheetRowPK timesheetRowPk) {
        this.timesheetRowPk = timesheetRowPk;
    }

    public float getSat() {
        return sat;
    }

    public void setSat(float sat) {
        this.sat = sat;
    }

    public float getSun() {
        return sun;
    }

    public void setSun(float sun) {
        this.sun = sun;
    }

    public float getMon() {
        return mon;
    }

    public void setMon(float mon) {
        this.mon = mon;
    }

    public float getTue() {
        return tue;
    }

    public void setTue(float tue) {
        this.tue = tue;
    }

    public float getWed() {
        return wed;
    }

    public void setWed(float wed) {
        this.wed = wed;
    }

    public float getThu() {
        return thu;
    }

    public void setThu(float thu) {
        this.thu = thu;
    }

    public float getFri() {
        return fri;
    }

    public void setFri(float fri) {
        this.fri = fri;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
        result = prime * result + Float.floatToIntBits(fri);
        result = prime * result + Float.floatToIntBits(mon);
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + Float.floatToIntBits(sat);
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + Float.floatToIntBits(sun);
        result = prime * result + Float.floatToIntBits(thu);
        result = prime * result
                + ((timesheetRowPk == null) ? 0 : timesheetRowPk.hashCode());
        result = prime * result + Float.floatToIntBits(tue);
        result = prime * result + Float.floatToIntBits(wed);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TimesheetRow other = (TimesheetRow) obj;
        if (comment == null) {
            if (other.comment != null)
                return false;
        } else if (!comment.equals(other.comment))
            return false;
        if (Float.floatToIntBits(fri) != Float.floatToIntBits(other.fri))
            return false;
        if (Float.floatToIntBits(mon) != Float.floatToIntBits(other.mon))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        if (Float.floatToIntBits(sat) != Float.floatToIntBits(other.sat))
            return false;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        if (Float.floatToIntBits(sun) != Float.floatToIntBits(other.sun))
            return false;
        if (Float.floatToIntBits(thu) != Float.floatToIntBits(other.thu))
            return false;
        if (timesheetRowPk == null) {
            if (other.timesheetRowPk != null)
                return false;
        } else if (!timesheetRowPk.equals(other.timesheetRowPk))
            return false;
        if (Float.floatToIntBits(tue) != Float.floatToIntBits(other.tue))
            return false;
        if (Float.floatToIntBits(wed) != Float.floatToIntBits(other.wed))
            return false;
        return true;
    }

    
}
