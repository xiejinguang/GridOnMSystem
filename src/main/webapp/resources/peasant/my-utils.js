/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var loadJS = function (url, callback, charset) {
    var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');
    if (charset) {
        script.charset = charset;
    }
    script.src = url;
    if (!callback) {
        return;
    }
    script.onload = script.onreadystatechange = function () {
        if (!this.readyState || this.readyState === 'loaded' || this.readyState === 'complete') {
            callback();
        }
    };
    head.appendChild(script);
};
