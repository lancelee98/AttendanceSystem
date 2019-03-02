/**
 * 任课管理管理初始化
 */
var Teach = {
    id: "TeachTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Teach.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '外键 课程id', field: 'courseId', visible: true, align: 'center', valign: 'middle'},
            {title: '外键 sys_user id', field: 'teacherId', visible: true, align: 'center', valign: 'middle'},
            {title: '外键 教室id', field: 'classroomId', visible: true, align: 'center', valign: 'middle'},
            {title: '起始节数 填数字1-13', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
            {title: '终止节数 填数字1-13  起始节数分别为1-2节时上课时间为8:20-9-55', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
            {title: '周几的课 填数字1-7 1代表周一 7代表周日', field: 'day', visible: true, align: 'center', valign: 'middle'},
            {title: '起始周数 填数字1-18', field: 'startWeek', visible: true, align: 'center', valign: 'middle'},
            {title: '结束周数 填数字1-18', field: 'endWeek', visible: true, align: 'center', valign: 'middle'},
            {title: '1为单周 0位双周', field: 'isSingle', visible: true, align: 'center', valign: 'middle'},
            {title: '实际上课周数 [1],[3],[5], 代表1,2,5周上课', field: 'actualWeek', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Teach.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Teach.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加任课管理
 */
Teach.openAddTeach = function () {
    var index = layer.open({
        type: 2,
        title: '添加任课管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/teach/teach_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看任课管理详情
 */
Teach.openTeachDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '任课管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/teach/teach_update/' + Teach.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除任课管理
 */
Teach.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/teach/delete", function (data) {
            Feng.success("删除成功!");
            Teach.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("teachId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询任课管理列表
 */
Teach.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Teach.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Teach.initColumn();
    var table = new BSTable(Teach.id, "/teach/list", defaultColunms);
    table.setPaginationType("client");
    Teach.table = table.init();
});
