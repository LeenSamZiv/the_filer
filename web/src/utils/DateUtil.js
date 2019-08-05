/**
 * 时间日期日期工具
 */
export default class DateUtil {

    static getDateObject(date) {
        if (!(date instanceof Date)) {
            date = new Date(date);
        }
        return date;
    }

    /**
     * 获取格式化字符串
     * @param dateObject Date对象
     * @param format 目标字符串格式,默认：yyyy-MM-dd hh:mm:ss
     * @returns {*|string} 格式化后的日期字符串
     *
     * 支持参数
     * yyyy：年
     * q: 季度
     * MM：月
     * dd：日
     * hh: 时
     * mm：分
     * ss：秒
     * S：毫秒
     */
    static format(dateObject, format) {
        dateObject = this.getDateObject(dateObject);

        let f = format || "yyyy-MM-dd hh:mm:ss";
        let o = {
            "q+": Math.floor((dateObject.getMonth() + 3) / 3),    // 季度
            "M+": dateObject.getMonth() + 1,    // 月份
            "d+": dateObject.getDate(),   // 日
            "h+": dateObject.getHours(),    // 时
            "m+": dateObject.getMinutes(),    // 分
            "s+": dateObject.getSeconds(),    // 秒
            "S": dateObject.getMilliseconds(), //毫秒
        };
        if (/(y+)/.test(f)) {
            f = f.replace(RegExp.$1, (dateObject.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (let k in o) {
            if (new RegExp("(" + k + ")").test(f)) {
                f = f.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return f;
    }

    /**
     * 获取前一天
     * @param date
     * @returns {Date}
     */
    static getPreDate(date) {
        date = this.getDateObject(date);
        return new Date(date.getTime() - 24 * 60 * 60 * 1000);
    }

    /**
     * 获取后一天
     * @param date
     * @returns {Date}
     */
    static getNextDate(date) {
        date = this.getDateObject(date);
        return new Date(date.getTime() + 24 * 60 * 60 * 1000);
    }
}
