package springBatchDemo;

import java.util.Date;

public class CustomerPayment {

    private String emailAddress;
    private Date ccExp;
    private Boolean notifiedOfUpcomingExpiration;

    public CustomerPayment(String emailAddress, Date ccExp) {
        this.emailAddress = emailAddress;
        this.ccExp = ccExp;
        this.notifiedOfUpcomingExpiration = false;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setCcExp(Date ccExp) {
        this.ccExp = ccExp;
    }

    public Date getCcExp() {
        return this.ccExp;
    }

    public void setNotifiedOfUpcomingExpiration(Boolean notifiedOfUpcomingExpiration) {
        this.notifiedOfUpcomingExpiration = notifiedOfUpcomingExpiration;
    }

    public Boolean getNotifiedOfUpcomingExpiration() {
        return this.notifiedOfUpcomingExpiration;
    }
}
