/**
 * Created by Damintsev Andrey on 29.04.2014.
 */
'use strict';
var APPLICATION_ROOT;
$(document).ready(function () {
    (function () {
        var timeoutFunction;
        var checkAuthentication = function() {
            $.get('api/authenticated').success(function(contextPath) {
                APPLICATION_ROOT = contextPath.responseText;
                goToRootContext();
            }).error(function(contextPath) {
                APPLICATION_ROOT = contextPath.responseText;
                timeoutFunction = setTimeout(checkAuthentication, 30000);
            });
        };
        checkAuthentication();

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
            var request = $.post('api/login?username=' + login + '&password=' + pswd + "&remember_me=" + rememberMe);

            request.success(function () {
                goToRootContext();
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

        var goToRootContext = function() {
            if(timeoutFunction != null) clearTimeout(timeoutFunction);
            window.location.replace(APPLICATION_ROOT);
        }
    })();
});