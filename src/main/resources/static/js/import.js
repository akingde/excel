layui.use('upload', function () {
    var $ = layui.jquery, upload = layui.upload;
    var uploadInst = upload.render({
        elem: '#selectFileBtn'
        , bindAction: '#doImportBtn'
        , url: 'doImport'
        , auto: false
        , multiple: false
        , accept: 'file'
        , exts: 'xls|xlsx'
        , before: before
        , done: done
        , error: error
    });

    function before() {
    }

    function done(res, index, upload) {
        $('#resultCard').show();
        $('#resultBody').html('');
        var result = res.msg + '<br/>';
        if(res.totalMsg){
            result += res.totalMsg + '<br/>';
        }

        var data = res.data;
        if(data){
            if(CommonUtil.isArray(data)){
                var infos = '';
                for(var size=data.length,i=0; i<size; i++){
                    var d = data[i];
                    var info ='表{0}，行{1}，列{2}，信息：{3}' + '<br/>';
                    infos += info.format(d.sheetName, d.rowIndex, d.colIndex, d.message);
                }
                if(infos && infos != ''){
                    result += infos;
                }
            }else {
                result += data;
            }
        }
        $('#resultBody').html(result);
    }

    function error(index, upload) {
    }

    $('#getTemplateBtn').click(function () {
        $("#getTemplateForm").submit();
    });

    $('#cancel').click(function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });
});