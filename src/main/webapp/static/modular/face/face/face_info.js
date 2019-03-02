/**
 * 初始化识别管理详情对话框
 */
var FaceInfoDlg = {
    faceInfoData : {}
};

/**
 * 清除数据
 */
FaceInfoDlg.clearData = function() {
    this.faceInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FaceInfoDlg.set = function(key, val) {
    this.faceInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FaceInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
FaceInfoDlg.close = function() {
    parent.layer.close(window.parent.Face.layerIndex);
}

/**
 * 收集数据
 */
FaceInfoDlg.collectData = function() {
    this
    .set('id')
    .set('studentId')
    .set('file')
    .set('link')
    .set('feature')
    .set('classroomId')
    .set('createTime');
}

/**
 * 提交添加
 */
FaceInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/face/add", function(data){
        Feng.success("添加成功!");
        window.parent.Face.table.refresh();
        FaceInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.faceInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
FaceInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/face/update", function(data){
        Feng.success("修改成功!");
        window.parent.Face.table.refresh();
        FaceInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.faceInfoData);
    ajax.start();
}

$(function() {

});
