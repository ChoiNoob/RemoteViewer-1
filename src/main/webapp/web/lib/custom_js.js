/**
 * Created by Damintsev Andrey on 28.01.14.
 */
var sound = new Howl({
    urls: ['web/sound/alarm.mp3', 'web/sound/alarm.wav'],
    loop: true
});

//function bindSubmit() {
//// variable to hold request
//    var request;
//// bind to the submit event of our form
//    $("#ImageUpload").submit(function (event) {
//        // abort any pending request
//        if (request) {
//            request.abort();
//        }
//        // setup some local variables
//        var $form = $(this);
//        // let's select and cache all the fields
//        var $inputs = $form.find("input, select, button, textarea");
//        // serialize the data in the form
//        var serializedData = $form.serialize();
//
//        // let's disable the inputs for the duration of the ajax request
//        $inputs.prop("disabled", true);
//
//        // fire off the request to /form.php
//        request = $.ajax({
//            url: "upload",
//            type: "post",
//            data: serializedData
//        });
//
//        // callback handler that will be called on success
//        request.done(function (response, textStatus, jqXHR) {
//            // log a message to the console
//            console.log("Hooray, it worked!");
//            $wnd.alert("resince =" + response)
//        });
//
//        // callback handler that will be called on failure
//        request.fail(function (jqXHR, textStatus, errorThrown) {
//            alert("fail =" + textStatus)
//            // log the error to the console
//            console.error(
//                "The following error occured: " +
//                    textStatus, errorThrown
//            );
//        });
//
//        // callback handler that will be called regardless
//        // if the request failed or succeeded
//        request.always(function () {
//            alert("asdasdasd");
//            // reenable the inputs
//            $inputs.prop("disabled", false);
//        });
//
//        // prevent default posting of form
//        event.preventDefault();
//    });
//}
//function loadFile() {
//    $('#fileupload').fileupload({
//        dataType: 'json',
//        done: function (e, data) {
//            $.each(data.result.files, function (index, file) {
//                $('<p/>').text(file.name).appendTo(document.body);
//            });
//        },
//        url: "upload"
//    });
//};
function loadFile() {
    alert("try to load");
    $('#ImageUpload').submit(function (event) {
        event.preventDefault();

        $.postJSON('/upload/image', {
//                owner: $('#owner').val(),
//                description: $('#description').val(),
                filename: 'test!'
            },
            function (result) {
                alert("sucesrrd")
//                if (result.success == true) {
//                    dialog('Success', 'Files have been uploaded!');
//                } else {
//                    dialog('Failure', 'Unable to upload files!');
//                }
            });
    });

    $('#upload').fileupload({
        dataType: 'json',
        done: function (e, data) {
            $.each(data.result, function (index, file) {
                alert("suces");
//                $('body').data('filelist').push(file);
//                $('#filename').append(formatFileDisplay(file));
//                $('#attach').empty().append('Add another file');
            });
        }
    });
}