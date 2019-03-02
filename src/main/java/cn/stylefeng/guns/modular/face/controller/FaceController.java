package cn.stylefeng.guns.modular.face.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Face;
import cn.stylefeng.guns.modular.face.service.IFaceService;

/**
 * 识别管理控制器
 *
 * @author fengshuonan
 * @Date 2019-03-02 16:05:40
 */
@Controller
@RequestMapping("/face")
public class FaceController extends BaseController {

    private String PREFIX = "/face/face/";

    @Autowired
    private IFaceService faceService;

    /**
     * 跳转到识别管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "face.html";
    }

    /**
     * 跳转到添加识别管理
     */
    @RequestMapping("/face_add")
    public String faceAdd() {
        return PREFIX + "face_add.html";
    }

    /**
     * 跳转到修改识别管理
     */
    @RequestMapping("/face_update/{faceId}")
    public String faceUpdate(@PathVariable Integer faceId, Model model) {
        Face face = faceService.selectById(faceId);
        model.addAttribute("item",face);
        LogObjectHolder.me().set(face);
        return PREFIX + "face_edit.html";
    }

    /**
     * 获取识别管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return faceService.selectList(null);
    }

    /**
     * 新增识别管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Face face) {
        faceService.insert(face);
        return SUCCESS_TIP;
    }

    /**
     * 删除识别管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer faceId) {
        faceService.deleteById(faceId);
        return SUCCESS_TIP;
    }

    /**
     * 修改识别管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Face face) {
        faceService.updateById(face);
        return SUCCESS_TIP;
    }

    /**
     * 识别管理详情
     */
    @RequestMapping(value = "/detail/{faceId}")
    @ResponseBody
    public Object detail(@PathVariable("faceId") Integer faceId) {
        return faceService.selectById(faceId);
    }
}
