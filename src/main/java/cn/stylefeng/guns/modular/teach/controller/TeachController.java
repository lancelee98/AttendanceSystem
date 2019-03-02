package cn.stylefeng.guns.modular.teach.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Teach;
import cn.stylefeng.guns.modular.teach.service.ITeachService;

/**
 * 任课管理控制器
 *
 * @author fengshuonan
 * @Date 2019-03-02 16:05:06
 */
@Controller
@RequestMapping("/teach")
public class TeachController extends BaseController {

    private String PREFIX = "/teach/teach/";

    @Autowired
    private ITeachService teachService;

    /**
     * 跳转到任课管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "teach.html";
    }

    /**
     * 跳转到添加任课管理
     */
    @RequestMapping("/teach_add")
    public String teachAdd() {
        return PREFIX + "teach_add.html";
    }

    /**
     * 跳转到修改任课管理
     */
    @RequestMapping("/teach_update/{teachId}")
    public String teachUpdate(@PathVariable Integer teachId, Model model) {
        Teach teach = teachService.selectById(teachId);
        model.addAttribute("item",teach);
        LogObjectHolder.me().set(teach);
        return PREFIX + "teach_edit.html";
    }

    /**
     * 获取任课管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return teachService.selectList(null);
    }

    /**
     * 新增任课管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Teach teach) {
        teachService.insert(teach);
        return SUCCESS_TIP;
    }

    /**
     * 删除任课管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer teachId) {
        teachService.deleteById(teachId);
        return SUCCESS_TIP;
    }

    /**
     * 修改任课管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Teach teach) {
        teachService.updateById(teach);
        return SUCCESS_TIP;
    }

    /**
     * 任课管理详情
     */
    @RequestMapping(value = "/detail/{teachId}")
    @ResponseBody
    public Object detail(@PathVariable("teachId") Integer teachId) {
        return teachService.selectById(teachId);
    }
}
