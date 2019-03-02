/**
 * 初始化选课管理详情对话框
 */
var ChooseInfoDlg = {
    chooseInfoData : {}
};

/**
 * 清除数据
 */
ChooseInfoDlg.clearData = function() {
    this.chooseInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ChooseInfoDlg.set = function(key, val) {
    this.chooseInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ChooseInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ChooseInfoDlg.close = function() {
    parent.layer.close(window.parent.Choose.layerIndex);
}

/**
 * 收集数据
 */
ChooseInfoDlg.collectData = function() {
    this
    .set('id')
    .set('studentId')
    .set('teachId');
}

/**
 * 提交添加
 */
ChooseInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/choose/add", function(data){
        Feng.success("添加成功!");
        window.parent.Choose.table.refresh();
        ChooseInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.chooseInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ChooseInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/choose/update", function(data){
        Feng.success("修改成功!");
        window.parent.Choose.table.refresh();
        ChooseInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.chooseInfoData);
    ajax.start();
}

$(function() {

});
