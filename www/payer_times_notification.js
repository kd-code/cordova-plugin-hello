/*global cordova, module*/

module.exports = {
    init: function (input_json, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "PrayerTimesNotification", "init", [input_json]);
    }
};
