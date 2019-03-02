package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
@TableName("attendance_face")
public class Face extends Model<Face> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 若识别出是谁 则填学生id 若未识别出 则不填
     */
    private Integer studentId;
    /**
     * 照片物理地址
     */
    private String file;
    /**
     * 照片显示地址
     */
    private String link;
    /**
     * 特征向量
     */
    private String feature;
    /**
     * 教室编号
     */
    private Integer classroomId;
    /**
     * 识别时间
     */
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Face{" +
        ", id=" + id +
        ", studentId=" + studentId +
        ", file=" + file +
        ", link=" + link +
        ", feature=" + feature +
        ", classroomId=" + classroomId +
        ", createTime=" + createTime +
        "}";
    }
}
