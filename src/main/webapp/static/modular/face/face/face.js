/**
 * 识别管理管理初始化
 */
var Face = {
    id: "FaceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Face.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '若识别出是谁 则填学生id 若未识别出 则不填', field: 'studentId', visible: true, align: 'center', valign: 'middle'},
            {title: '照片物理地址', field: 'file', visible: true, align: 'center', valign: 'middle'},
            {title: '照片显示地址', field: 'link', visible: true, align: 'center', valign: 'middle'},
            {title: '特征向量', field: 'feature', visible: true, align: 'center', valign: 'middle'},
            {title: '教室编号', field: 'classroomId', visible: true, align: 'center', valign: 'middle'},
            {title: '识别时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Face.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Face.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加识别管理
 */
Face.openAddFace = function () {
    var index = layer.open({
        type: 2,
        title: '添加识别管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/face/face_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看识别管理详情
 */
Face.openFaceDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '识别管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/face/face_update/' + Face.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除识别管理
 */
Face.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/face/delete", function (data) {
            Feng.success("删除成功!");
            Face.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("faceId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询识别管理列表
 */
Face.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Face.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Face.initColumn();
    var table = new BSTable(Face.id, "/face/list", defaultColunms);
    table.setPaginationType("client");
    Face.table = table.init();
});
