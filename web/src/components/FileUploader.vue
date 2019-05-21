<template>
    <form id="formFile" style="display: none">
        <input id="chooser" name="files" type="file" multiple="multiple" @change="inputChange($event)">
    </form>
</template>

<script>
    export default {
        name: "FileUploader",
        methods: {
            upload() {
                document.getElementById('chooser').click();
            },
            inputChange(event) {
                if (event.target.files.length > 0) {
                    this.$emit('uploadStart');
                    let formData = new FormData(document.getElementById("formFile"));
                    let xhr = new XMLHttpRequest();
                    xhr.open("post", "api/upload");
                    xhr.send(formData);
                    xhr.onload = () => {
                        this.$emit('uploadDone');
                        switch (xhr.status) {
                            case 200:
                                break;
                            case  500:
                                if (xhr.response.indexOf('Maximum upload size exceeded')) {
                                    alert('文件过大');
                                } else {
                                    alert('服务器出错');
                                }
                                break;
                        }
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
