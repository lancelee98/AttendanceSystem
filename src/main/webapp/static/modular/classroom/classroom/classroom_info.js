/**
 * 初始化教室管理详情对话框
 */
var ClassroomInfoDlg = {
    classroomInfoData : {}
};

/**
 * 清除数据
 */
ClassroomInfoDlg.clearData = function() {
    this.classroomInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClassroomInfoDlg.set = function(key, val) {
    this.classroomInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClassroomInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ClassroomInfoDlg.close = function() {
    parent.layer.close(window.parent.Classroom.layerIndex);
}

/**
 * 收集数据
 */
ClassroomInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name');
}

/**
 * 提交添加
 */
ClassroomInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/classroom/add", function(data){
        Feng.success("添加成功!");
        window.parent.Classroom.table.refresh();
        ClassroomInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.classroomInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ClassroomInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/classroom/update", function(data){
        Feng.success("修改成功!");
        window.parent.Classroom.table.refresh();
        ClassroomInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.classroomInfoData);
    ajax.start();
}

$(function() {

});
