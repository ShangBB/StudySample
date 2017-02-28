package com.shangbb.studysample.http.entity;

/**
 * @Fuction: 血压测量数据
 * @Author: Shang
 * @Date: 2016/9/8  15:45
 */
public class BloodData{
    String bloodpreinfoid;//血压id
    String memberid;//会员id
    String equipmentid;//设备id
    String bloodpreinfohigh;//高压
    String bloodpreinfolow;//低压
    String pulse;//心率
    String bloodpreinfo_TIME;//测量时间
    String fill_member_id;//填表人id
    String update_time;//插入时间
    String delete_status;//删除状态

    public String getBloodpreinfoid() {
        return bloodpreinfoid;
    }

    public void setBloodpreinfoid(String bloodpreinfoid) {
        this.bloodpreinfoid = bloodpreinfoid;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(String equipmentid) {
        this.equipmentid = equipmentid;
    }

    public String getBloodpreinfohigh() {
        return bloodpreinfohigh;
    }

    public void setBloodpreinfohigh(String bloodpreinfohigh) {
        this.bloodpreinfohigh = bloodpreinfohigh;
    }

    public String getBloodpreinfolow() {
        return bloodpreinfolow;
    }

    public void setBloodpreinfolow(String bloodpreinfolow) {
        this.bloodpreinfolow = bloodpreinfolow;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getBloodpreinfo_TIME() {
        return bloodpreinfo_TIME;
    }

    public void setBloodpreinfo_TIME(String bloodpreinfo_TIME) {
        this.bloodpreinfo_TIME = bloodpreinfo_TIME;
    }

    public String getFill_member_id() {
        return fill_member_id;
    }

    public void setFill_member_id(String fill_member_id) {
        this.fill_member_id = fill_member_id;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(String delete_status) {
        this.delete_status = delete_status;
    }
}
