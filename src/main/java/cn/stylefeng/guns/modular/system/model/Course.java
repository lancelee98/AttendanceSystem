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
@TableName("attendance_course")
public class Course extends Model<Course> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 课程号
     */
    private String courseNum;
    /**
     * 课程名
     */
    private String courseName;
    /**
     * 课时
     */
    private Integer courseTimespan;
    /**
     * 学分
     */
    private Integer coursePoint;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseTimespan() {
        return courseTimespan;
    }

    public void setCourseTimespan(Integer courseTimespan) {
        this.courseTimespan = courseTimespan;
    }

    public Integer getCoursePoint() {
        return coursePoint;
    }

    public void setCoursePoint(Integer coursePoint) {
        this.coursePoint = coursePoint;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Course{" +
        ", id=" + id +
        ", courseNum=" + courseNum +
        ", courseName=" + courseName +
        ", courseTimespan=" + courseTimespan +
        ", coursePoint=" + coursePoint +
        "}";
    }
}
