export default class {
    static CreateRequest(method, url, async) {
        let xhr = new XMLHttpRequest();
        xhr.open(method, url, async);
        return xhr;
    }

    /**
     * 获取列表
     */
    static GetList() {
        let xhr = this.CreateRequest('get', 'api/list', true);
        xhr.send();
        return new Promise(
            (resolve, reject) => {
                xhr.onload = () => {
                    switch (xhr.status) {
                        case 200:
                            resolve(JSON.parse(xhr.response));
                            break;
                        case  500:
                            if (xhr.response.indexOf('Maximum upload size exceeded')) {
                                reject('文件过大');
                            } else {
                                reject('服务器出错');
                            }
                            break;
                    }
                };
            }
        );
    }

    /**
     * 上传文件
     * @param formData
     */
    static PostData(formData) {
        let xhr = this.CreateRequest("post", "api/upload", true);
        xhr.send(formData);
        return new Promise(
            (resolve, reject) => {
                xhr.onload = () => {
                    switch (xhr.status) {
                        case 200:
                            resolve();
                            break;
                        case  500:
                            if (xhr.response.indexOf('Maximum upload size exceeded')) {
                                reject('文件过大');
                            } else {
                                reject('服务器出错');
                            }
                            break;
                        default:
                            resolve();
                            break;
                    }
                }
            }
        );
    }

    /**
     * 下载文件
     * @param data
     */
    static GetData(data) {
        let xhr = this.CreateRequest("post", "api/download", true);
        xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        xhr.responseType = "blob";
        xhr.send(data);
        return new Promise(
            (resolve, reject) => {
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        resolve({
                            disposition: xhr.getResponseHeader("Content-disposition"),
                            data: xhr.response
                        });
                    }
                }
            }
        );
    }

    static DeleteData(data) {
        let xhr = this.CreateRequest("delete", "api/remove", true);
        xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        xhr.send(data);
        return new Promise(
            (resolve, reject) => {
                xhr.onload = () => {
                    if (xhr.status === 200 && xhr.response) {
                        resolve(xhr.response);
                    } else {
                        reject("删除失败");
                    }
                };
            }
        );
    }
}
