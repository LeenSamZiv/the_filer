export default class {
    //Powered By https://www.jianshu.com/p/1e189c14aa98
    static getBrowser() {
        let ua = window.navigator.userAgent;
        let isIE = !!window.ActiveXObject || "ActiveXObject" in window;
        let isFirefox = ua.indexOf("Firefox") !== -1;
        let isOpera = window.opr !== undefined;
        let isChrome = ua.indexOf("Chrome") && window.chrome;
        let isSafari = ua.indexOf("Safari") !== -1 && ua.indexOf("Version") !== -1;
        if (isIE) {
            return "IE";
        } else if (isFirefox) {
            return "Firefox";
        } else if (isOpera) {
            return "Opera";
        } else if (isChrome) {
            return "Chrome";
        } else if (isSafari) {
            return "Safari";
        } else {
            return "Unkown";
        }
    }
}
