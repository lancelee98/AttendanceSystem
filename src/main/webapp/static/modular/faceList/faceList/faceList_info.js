/**
 * 初始化照片上传详情对话框
 */
var FaceListInfoDlg = {
    faceListInfoData : {}
};

/**
 * 清除数据
 */
FaceListInfoDlg.clearData = function() {
    this.faceListInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FaceListInfoDlg.set = function(key, val) {
    this.faceListInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FaceListInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
FaceListInfoDlg.close = function() {
    parent.layer.close(window.parent.FaceList.layerIndex);
}

/**
 * 收集数据
 */
FaceListInfoDlg.collectData = function() {
    this
    .set('id')
    .set('studentId')
    .set('file')
    .set('link')
    .set('feature');
}

/**
 * 提交添加
 */
FaceListInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/faceList/add", function(data){
        Feng.success("添加成功!");
        window.parent.FaceList.table.refresh();
        FaceListInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.faceListInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
FaceListInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/faceList/update", function(data){
        Feng.success("修改成功!");
        window.parent.FaceList.table.refresh();
        FaceListInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.faceListInfoData);
    ajax.start();
}

$(function() {

});
