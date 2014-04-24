
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
$(document).ready(function() {
    (function () {
//    $('#submitForm').submit(function (e) {
//        alert("asd");
//        e.preventDefault();
//        return false;
//    });
        $('form#submitForm').submit(function(e){
            $(this).children('input[type=submit]').attr('disabled', 'disabled');
            // this is just for demonstration
            e.preventDefault();
            loginAttempt();
            return false;
        });

        var loginAttempt = function () {
            alert("asdasdasdas");
            var login = $('#login').val();
            var pswd = $('#password').val();
            if(checkValues(login, pswd)) return;
//       todo var password = CryptoJS.MD5(pswd);
            var password = pswd;
            var request = $.post('api/login',
                {
                    login: login,
                    password: password
                });

            request.success(function() {
                alert("sucsessss")
            });

            request.error(function(text) {
                alert(text);
            });
        };

        var checkValues = function(login, pswd) {
            return login == null || pswd == null;
        };
        window['loginAttempt'] = loginAttempt;
    })();
});


