export default class {
    static GetList() {
        let xhr = new XMLHttpRequest();
        xhr.open('get', 'api/list', true);
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

    static PostData(formData) {
        let xhr = new XMLHttpRequest();
        xhr.open("post", "api/upload");
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
}
