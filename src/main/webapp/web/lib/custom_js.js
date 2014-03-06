/**
 * Created by Damintsev Andrey on 28.01.14.
 */
var sound;

function initSound() {
    sound = new Howl({
        urls: ['web/sound/alarm.mp3', 'web/sound/alarm.wav'],
        loop: true
    });
}
