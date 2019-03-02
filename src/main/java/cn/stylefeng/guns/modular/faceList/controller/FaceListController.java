package cn.stylefeng.guns.modular.faceList.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.FaceList;
import cn.stylefeng.guns.modular.faceList.service.IFaceListService;

/**
 * 照片上传控制器
 *
 * @author fengshuonan
 * @Date 2019-03-02 16:03:43
 */
@Controller
@RequestMapping("/faceList")
public class FaceListController extends BaseController {

    private String PREFIX = "/faceList/faceList/";

    @Autowired
    private IFaceListService faceListService;

    /**
     * 跳转到照片上传首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "faceList.html";
    }

    /**
     * 跳转到添加照片上传
     */
    @RequestMapping("/faceList_add")
    public String faceListAdd() {
        return PREFIX + "faceList_add.html";
    }

    /**
     * 跳转到修改照片上传
     */
    @RequestMapping("/faceList_update/{faceListId}")
    public String faceListUpdate(@PathVariable Integer faceListId, Model model) {
        FaceList faceList = faceListService.selectById(faceListId);
        model.addAttribute("item",faceList);
        LogObjectHolder.me().set(faceList);
        return PREFIX + "faceList_edit.html";
    }

    /**
     * 获取照片上传列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return faceListService.selectList(null);
    }

    /**
     * 新增照片上传
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(FaceList faceList) {
        faceListService.insert(faceList);
        return SUCCESS_TIP;
    }

    /**
     * 删除照片上传
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer faceListId) {
        faceListService.deleteById(faceListId);
        return SUCCESS_TIP;
    }

    /**
     * 修改照片上传
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(FaceList faceList) {
        faceListService.updateById(faceList);
        return SUCCESS_TIP;
    }

    /**
     * 照片上传详情
     */
    @RequestMapping(value = "/detail/{faceListId}")
    @ResponseBody
    public Object detail(@PathVariable("faceListId") Integer faceListId) {
        return faceListService.selectById(faceListId);
    }
}
