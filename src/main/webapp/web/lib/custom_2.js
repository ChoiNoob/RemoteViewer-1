
function bindd() {
    $('#upload').fileupload({
        replaceFileInput: false,
        dataType: 'json',
        done: function (e, data) {
//            console.dir(data);
            var tmpImage = data.result;
            $('#upload').value = tmpImage.name;
            jsniCallback(tmpImage.id, tmpImage.width, tmpImage.height);

        },
        fail: function (e, data) {
            console.log(data);
            alert('fuck');
        }
    });
}
