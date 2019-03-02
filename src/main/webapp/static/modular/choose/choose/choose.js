/**
 * 选课管理管理初始化
 */
var Choose = {
    id: "ChooseTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Choose.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '外键sys_user id', field: 'studentId', visible: true, align: 'center', valign: 'middle'},
            {title: '外键 开课id', field: 'teachId', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Choose.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Choose.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加选课管理
 */
Choose.openAddChoose = function () {
    var index = layer.open({
        type: 2,
        title: '添加选课管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/choose/choose_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看选课管理详情
 */
Choose.openChooseDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '选课管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/choose/choose_update/' + Choose.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除选课管理
 */
Choose.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/choose/delete", function (data) {
            Feng.success("删除成功!");
            Choose.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("chooseId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询选课管理列表
 */
Choose.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Choose.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Choose.initColumn();
    var table = new BSTable(Choose.id, "/choose/list", defaultColunms);
    table.setPaginationType("client");
    Choose.table = table.init();
});
