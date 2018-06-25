<div class="row">
    <div class="col-sm-12">
        <div id="brandTableToolbar">
            <#button id="importBtn" label="导入" icon="fa-cloud-upload" clickFun="openImport()"/>
            <#button id="exportBtn" label="导出" icon="fa-cloud-download" clickFun="exportp()"/>
        </div>
    </div>
</div>
<script  type="text/javascript">
    function openImport() {
        var index = layer.open({
            type: 2,
            title: '某某导入',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: '${ctxPath}${doImport}'
        });
        this.layerIndex = index;
    }

    function exportp() {
        var queryData = {};
        queryData.brandName = $('#brandName').val();
        queryData.brandNumber = $('#brandNumber').val();
        queryData.brandType = $('#brandType').val();
        queryData.brandApply = $('#brandApply').val();
        queryData.brandStatus = $('#brandStatus').val();

        $('#exportForm').remove();
        var exportForm = $('<form id="exportForm"/>');
        exportForm.attr('action', '${ctxPath}${doExport}');
        exportForm.attr('method', 'post');
        exportForm.appendTo($('body'))
        exportForm.submit();
        $('#exportForm').remove();
    };

</script>



