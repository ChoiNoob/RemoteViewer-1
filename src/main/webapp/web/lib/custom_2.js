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
$(document).ready(function () {
    (function () {
        $("input#rememberMe").prop("checked", true);
        $('form#submitForm').submit(function (e) {
            $(this).children('input[type=submit]').attr('disabled', 'disabled');
            e.preventDefault();
            loginAttempt();
            return false;
        });

        var loginAttempt = function () {
            var login = $('#login').val();
            var pswd = $('#password').val();
            var rememberMe = $('input#rememberMe').prop('checked');
            if (checkValues(login, pswd)) return;
//       todo var password = CryptoJS.MD5(pswd);
            var password = pswd;
            var request = $.post('/api/login?username=' + login + '&password=' + pswd + "&remember_me=" + rememberMe,
                {
                    login: login,
                    password: password,
                    rememberMe: rememberMe
                });

            request.success(function () {
                window.location.replace("/");
            });

            request.error(function (text) {
                console.log(text);
                alert(text);
            });
        };

        var checkValues = function (login, pswd) {
            return login == null || pswd == null;
        };
        window['loginAttempt'] = loginAttempt;
    })();
});


