package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lichuang
 * @since 2019-03-02
 */
@TableName("attendance_teach")
public class Teach extends Model<Teach> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 外键 课程id
     */
    private Integer courseId;
    /**
     * 外键 sys_user id
     */
    private Integer teacherId;
    /**
     * 外键 教室id
     */
    private Integer classroomId;
    /**
     * 起始节数 填数字1-13
     */
    private Integer startTime;
    /**
     * 终止节数 填数字1-13  起始节数分别为1-2节时上课时间为8:20-9-55
     */
    private Integer endTime;
    /**
     * 周几的课 填数字1-7 1代表周一 7代表周日
     */
    private Integer day;
    /**
     * 起始周数 填数字1-18
     */
    private Integer startWeek;
    /**
     * 结束周数 填数字1-18
     */
    private Integer endWeek;
    /**
     * 1为单周 0位双周
     */
    private Integer idSingleWeek;
    /**
     * 实际上课周数 [1],[2],[5], 代表1,2,5周上课
     */
    private String actualWeek;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public Integer getIdSingleWeek() {
        return idSingleWeek;
    }

    public void setIdSingleWeek(Integer idSingleWeek) {
        this.idSingleWeek = idSingleWeek;
    }

    public String getActualWeek() {
        return actualWeek;
    }

    public void setActualWeek(String actualWeek) {
        this.actualWeek = actualWeek;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Teach{" +
        ", id=" + id +
        ", courseId=" + courseId +
        ", teacherId=" + teacherId +
        ", classroomId=" + classroomId +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", day=" + day +
        ", startWeek=" + startWeek +
        ", endWeek=" + endWeek +
        ", idSingleWeek=" + idSingleWeek +
        ", actualWeek=" + actualWeek +
        "}";
    }
}
