/**
 * 初始化任课管理详情对话框
 */
var TeachInfoDlg = {
    teachInfoData : {}
};

/**
 * 清除数据
 */
TeachInfoDlg.clearData = function() {
    this.teachInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeachInfoDlg.set = function(key, val) {
    this.teachInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeachInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TeachInfoDlg.close = function() {
    parent.layer.close(window.parent.Teach.layerIndex);
}

/**
 * 收集数据
 */
TeachInfoDlg.collectData = function() {
    this
    .set('id')
    .set('courseId')
    .set('teacherId')
    .set('classroomId')
    .set('startTime')
    .set('endTime')
    .set('day')
    .set('startWeek')
    .set('endWeek')
    .set('isSingle')
    .set('actualWeek');
}

/**
 * 提交添加
 */
TeachInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teach/add", function(data){
        Feng.success("添加成功!");
        window.parent.Teach.table.refresh();
        TeachInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teachInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TeachInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teach/update", function(data){
        Feng.success("修改成功!");
        window.parent.Teach.table.refresh();
        TeachInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teachInfoData);
    ajax.start();
}

$(function() {

});
