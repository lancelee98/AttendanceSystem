/**
 * 照片上传管理初始化
 */
var FaceList = {
    id: "FaceListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
FaceList.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '对应sys_user id', field: 'studentId', visible: true, align: 'center', valign: 'middle'},
            {title: '图片存放物理位置', field: 'file', visible: true, align: 'center', valign: 'middle'},
            {title: '显示链接', field: 'link', visible: true, align: 'center', valign: 'middle'},
            {title: '特征向量', field: 'feature', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
FaceList.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        FaceList.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加照片上传
 */
FaceList.openAddFaceList = function () {
    var index = layer.open({
        type: 2,
        title: '添加照片上传',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/faceList/faceList_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看照片上传详情
 */
FaceList.openFaceListDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '照片上传详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/faceList/faceList_update/' + FaceList.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除照片上传
 */
FaceList.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/faceList/delete", function (data) {
            Feng.success("删除成功!");
            FaceList.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("faceListId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询照片上传列表
 */
FaceList.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    FaceList.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = FaceList.initColumn();
    var table = new BSTable(FaceList.id, "/faceList/list", defaultColunms);
    table.setPaginationType("client");
    FaceList.table = table.init();
});
