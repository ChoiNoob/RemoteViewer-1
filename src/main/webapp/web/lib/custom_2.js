
function bindd() {
    $('#upload').fileupload({
        replaceFileInput: false,
        dataType: 'json',
        done: function (e, data) {
//            console.dir(data);
            var tmpImage = data.result;
            $('#upload').value = tmpImage.name;
            jsniCallback(tmpImage.id);

        },
        fail: function (e, data) {
            console.log(data);
            console.dir(data);
            alert('При загрузке произошла ошибка:"' + data.errorThrown + '"');
        }
    });
}
