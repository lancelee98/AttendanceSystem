package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Classroom;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-03-02
 */
public interface ClassroomMapper extends BaseMapper<Classroom> {

    List<Map<String,Object>> test();

}
