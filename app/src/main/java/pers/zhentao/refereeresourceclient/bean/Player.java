package pers.zhentao.refereeresourceclient.bean;


import java.util.Date;

/**
 * Created by ZhangZT on 2016/3/16 15:01.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class Player{
    private Integer playerId;
    private String team;
    private String experience;
    private String honor;
    private Date createTime;
    private Boolean isDelete;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
