<template>
    <div id="app">
        <file-uploader ref="fileUploader"
                       @uploadStart="$refs.fileList.controlLoading(true)"
                       @uploadDone="$refs.fileList.getData()">
        </file-uploader>
        <div class="header text-center">The Filer</div>
        <div class="content auto-scroll">
            <file-list ref="fileList"></file-list>
        </div>
        <float-action-button
                @clickEvent="$refs.fileUploader.upload()">
        </float-action-button>
        <bottom></bottom>
    </div>
</template>

<script>
    import FloatActionButton from "./components/FloatActionButton";
    import Bottom from "./components/Bottom";
    import FileList from "./components/FileList";
    import FileUploader from "./components/FileUploader";

    export default {
        name: 'app',
        components: {FileUploader, FileList, Bottom, FloatActionButton},
        data() {
            return {
                postText: 'post'
            }
        },
        methods: {}
    }
</script>

<style lang="scss" scoped>
    #app {
        height: 100%;
        display: flex;
        flex-direction: column;

        > div {
            padding: $spacing-normal;
        }

        > .header {
            color: $color-black;
            font-size: 1.1em;
            background: linear-gradient(135deg, transparent 0.5em, $color-yellow 0);
        }

        > .content {
            flex: 1;
            margin: $spacing-normal*2 0 0;
            background: $color-gray;
        }
    }
</style>
