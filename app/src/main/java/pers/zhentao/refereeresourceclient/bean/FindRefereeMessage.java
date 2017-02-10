package pers.zhentao.refereeresourceclient.bean;



import java.io.Serializable;
import java.util.Date;

/**
 * Created by ZhangZT on 2016/8/20 16:33.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class FindRefereeMessage implements Serializable{

    private Integer id;
    private User user;     //发起人
    private String gameState;   //比赛描述
    private String address;     //比赛地点
    private Date time;          //比赛时间
    private Integer refereeCount;//裁判人数
    private String refereeClaim;//裁判要求
    private String pay;         //报酬
    private String note;        //备注
    private Date publishTime;  //发布时间
    private Boolean isAccept = false;  //是否已被接手
    private Integer applyCount = 0;//报名人数
    private Integer readCount = 0; //查看人数
    private Integer acceptCount = 0;//已招募人数
    private Boolean isDelete = false;  //是否删除

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Boolean getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(Boolean isAccept) {
        this.isAccept = isAccept;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRefereeCount() {
        return refereeCount;
    }

    public void setRefereeCount(Integer refereeCount) {
        this.refereeCount = refereeCount;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public String getRefereeClaim() {
        return refereeClaim;
    }

    public void setRefereeClaim(String refereeClaim) {
        this.refereeClaim = refereeClaim;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public Integer getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(Integer acceptCount) {
        this.acceptCount = acceptCount;
    }
}
