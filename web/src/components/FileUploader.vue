<template>
    <form id="formFile" style="display: none">
        <input id="chooser" name="files" type="file" multiple="multiple" @change="inputChange($event)">
    </form>
</template>

<script>
    import DataInterfaceUtil from "../utils/DataInterfaceUtil";

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

                    DataInterfaceUtil.PostData(formData).then(
                        () => this.$emit('uploadDone')
                    ).catch(
                        error => this.$emit('uploadDone') && alert(error)
                    );
                }
            }
        }
    }
</script>
