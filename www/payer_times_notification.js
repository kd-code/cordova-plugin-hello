/*global cordova, module*/

module.exports = {
    init: function (input_json, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "PrayertTimesNotification", "init", [input_json]);
    }
};
