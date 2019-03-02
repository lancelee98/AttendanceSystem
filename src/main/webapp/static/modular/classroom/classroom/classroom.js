/**
 * 教室管理管理初始化
 */
var Classroom = {
    id: "ClassroomTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Classroom.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '教室名称', field: 'name', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Classroom.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Classroom.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加教室管理
 */
Classroom.openAddClassroom = function () {
    var index = layer.open({
        type: 2,
        title: '添加教室管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/classroom/classroom_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看教室管理详情
 */
Classroom.openClassroomDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '教室管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/classroom/classroom_update/' + Classroom.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除教室管理
 */
Classroom.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/classroom/delete", function (data) {
            Feng.success("删除成功!");
            Classroom.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("classroomId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询教室管理列表
 */
Classroom.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Classroom.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Classroom.initColumn();
    var table = new BSTable(Classroom.id, "/classroom/list", defaultColunms);
    table.setPaginationType("client");
    Classroom.table = table.init();
});
