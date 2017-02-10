package pers.zhentao.refereeresourceclient.bean;

import java.io.Serializable;
import java.util.Date;

import pers.zhentao.refereeresourceclient.globalvariable.Common;

/**
 * Created by ZhangZT on 2016/8/21 10:43.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class ApplyRefereeMessage implements Serializable{
    private FindRefereeMessage findRefereeMessage;
    private Referee referee;
    private User refereeUser;
    private Date applyDate;
    private String note;
    private Boolean isDelete = false;
    private Integer status = Common.APPLY_WAIT;

    public FindRefereeMessage getFindRefereeMessage() {
        return findRefereeMessage;
    }

    public void setFindRefereeMessage(FindRefereeMessage findRefereeMessage) {
        this.findRefereeMessage = findRefereeMessage;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getRefereeUser() {
        return refereeUser;
    }

    public void setRefereeUser(User refereeUser) {
        this.refereeUser = refereeUser;
    }
}
