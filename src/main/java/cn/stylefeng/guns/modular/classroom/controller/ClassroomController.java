package cn.stylefeng.guns.modular.classroom.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Classroom;
import cn.stylefeng.guns.modular.classroom.service.IClassroomService;

/**
 * 教室管理控制器
 *
 * @author fengshuonan
 * @Date 2019-03-02 14:44:37
 */
@Controller
@RequestMapping("/classroom")
public class ClassroomController extends BaseController {

    private String PREFIX = "/classroom/classroom/";

    @Autowired
    private IClassroomService classroomService;

    /**
     * 跳转到教室管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "classroom.html";
    }

    /**
     * 跳转到添加教室管理
     */
    @RequestMapping("/classroom_add")
    public String classroomAdd() {
        return PREFIX + "classroom_add.html";
    }

    /**
     * 跳转到修改教室管理
     */
    @RequestMapping("/classroom_update/{classroomId}")
    public String classroomUpdate(@PathVariable Integer classroomId, Model model) {
        Classroom classroom = classroomService.selectById(classroomId);
        model.addAttribute("item",classroom);
        LogObjectHolder.me().set(classroom);
        return PREFIX + "classroom_edit.html";
    }

    /**
     * 获取教室管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return classroomService.selectList(null);
    }

    /**
     * 新增教室管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Classroom classroom) {
        classroomService.insert(classroom);
        return SUCCESS_TIP;
    }

    /**
     * 删除教室管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer classroomId) {
        classroomService.deleteById(classroomId);
        return SUCCESS_TIP;
    }

    /**
     * 修改教室管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Classroom classroom) {
        classroomService.updateById(classroom);
        return SUCCESS_TIP;
    }

    /**
     * 教室管理详情
     */
    @RequestMapping(value = "/detail/{classroomId}")
    @ResponseBody
    public Object detail(@PathVariable("classroomId") Integer classroomId) {
        return classroomService.selectById(classroomId);
    }
}
