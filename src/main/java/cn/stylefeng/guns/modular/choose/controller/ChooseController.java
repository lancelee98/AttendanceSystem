package cn.stylefeng.guns.modular.choose.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Choose;
import cn.stylefeng.guns.modular.choose.service.IChooseService;

/**
 * 选课管理控制器
 *
 * @author fengshuonan
 * @Date 2019-03-02 16:02:41
 */
@Controller
@RequestMapping("/choose")
public class ChooseController extends BaseController {

    private String PREFIX = "/choose/choose/";

    @Autowired
    private IChooseService chooseService;

    /**
     * 跳转到选课管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "choose.html";
    }

    /**
     * 跳转到添加选课管理
     */
    @RequestMapping("/choose_add")
    public String chooseAdd() {
        return PREFIX + "choose_add.html";
    }

    /**
     * 跳转到修改选课管理
     */
    @RequestMapping("/choose_update/{chooseId}")
    public String chooseUpdate(@PathVariable Integer chooseId, Model model) {
        Choose choose = chooseService.selectById(chooseId);
        model.addAttribute("item",choose);
        LogObjectHolder.me().set(choose);
        return PREFIX + "choose_edit.html";
    }

    /**
     * 获取选课管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return chooseService.selectList(null);
    }

    /**
     * 新增选课管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Choose choose) {
        chooseService.insert(choose);
        return SUCCESS_TIP;
    }

    /**
     * 删除选课管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer chooseId) {
        chooseService.deleteById(chooseId);
        return SUCCESS_TIP;
    }

    /**
     * 修改选课管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Choose choose) {
        chooseService.updateById(choose);
        return SUCCESS_TIP;
    }

    /**
     * 选课管理详情
     */
    @RequestMapping(value = "/detail/{chooseId}")
    @ResponseBody
    public Object detail(@PathVariable("chooseId") Integer chooseId) {
        return chooseService.selectById(chooseId);
    }
}
