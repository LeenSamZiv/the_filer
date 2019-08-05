<template>
    <div class="list">
        <loading-bar :loading="dataLoading"></loading-bar>
        <div class="empty" v-if="dataArray.length<1 && !dataLoading">EMPTY</div>
        <div class="item" v-for="item of dataArray" :key="item.name">
            <div class="name">{{item.name}}</div>
            <div class="info">
                <div class="lastModified">
                    {{item.lastModified | dateFormat}}
                </div>
                <div class="function copy-unable">
                    <div class="sizeString"> {{item.sizeString}}</div>
                    <div class="download" title="download" @click="handleDownload(item)">⏬</div>
                    <div class="delete" title="delete" @click="handleDelete(item)">🚮</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import StringUtil from "../utils/StringUtil";
    import LoadingBar from "./LoadingBar";
    import DateUtil from "../utils/DateUtil";
    import DataInterfaceUtil from "../utils/DataInterfaceUtil";
    import CommonUtil from "../utils/CommonUtil";

    export default {
        name: "FileList",
        components: {LoadingBar},
        filters: {
            dateFormat(value) {
                return DateUtil.format(value)
            }
        },
        data() {
            return {
                stringUtil: new StringUtil(),
                dataLoading: true,
                dataArray: [],
            }
        },
        mounted() {
            this.getData();
        },
        methods: {
            controlLoading(show) {
                this.dataLoading = !!show;
            },

            getData() {
                this.controlLoading(true);
                let renderData = array => {
                    array.forEach(item => item.sizeString = this.stringUtil.formatSize(item.size));
                    this.dataArray = array;
                    this.controlLoading(false);
                };

                DataInterfaceUtil.GetList().then(
                    value => renderData(value)
                ).catch(
                    error => alert(error)
                );
            },

            handleDownload(target) {
                let requestData = {
                    name: target.name, size: target.size, lastModified: target.lastModified,
                };

                DataInterfaceUtil.GetData(JSON.stringify(requestData)).then(
                    response => this.saveFile(response)
                ).catch(
                    val => console.log(val)
                );
            },

            handleDelete(target) {
                let requestData = {
                    name: target.name, size: target.size, lastModified: target.lastModified,
                };
                if (!window.confirm(`是否删除${requestData.name}？`)) {
                    return null;
                }

                DataInterfaceUtil.DeleteData(JSON.stringify(requestData)).then(
                    () => this.getData()
                ).catch(
                    val => console.log(val)
                );
            },

            saveFile(response) {
                let fileName = decodeURIComponent(response.disposition).substring(20);

                let handleChromium = () => {
                    let aTag = document.createElement('a');
                    let blob = new Blob([response.data]);
                    aTag.href = URL.createObjectURL(blob);
                    aTag.download = fileName;
                    aTag.click();
                    URL.revokeObjectURL(blob);
                };

                let handleFireFox = () => {
                    let file = new File([response.data], fileName);
                    parent.location.href = URL.createObjectURL(file);
                };

                let handleIE = () => {
                    let file = new Blob([response.data]);
                    window.navigator.msSaveBlob(file, fileName);
                };

                switch (CommonUtil.getBrowser()) {
                    case 'Firefox':
                        handleFireFox();
                        break;
                    case 'IE':
                        handleIE();
                        break;
                    case 'Chrome':
                    default:
                        handleChromium();
                        break;
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
    .list {
        > .empty {
            color: $color-white;
            text-align: center;
        }

        > .item {
            margin: 0.8em 0.25em;
            padding: 0.2em 0.5em;
            display: flex;
            color: $color-white;
            cursor: pointer;
            transition: all 0.2s;

            border-left: 0.3em $color-yellow solid;
            position: relative;
            z-index: 1;

            &::before {
                content: '';
                position: absolute;
                z-index: -1;
                top: 0;
                bottom: 0;
                left: -0.25em;
                right: -0.25em;
                background-color: $color-yellow;
                transform-origin: right;
                transform: scaleX(0);
                transition: transform 0.2s ease-in-out;
            }

            &:hover::before {
                transform: scaleX(1);
                transform-origin: left;
            }

            &:hover {
                color: $color-black;
                font-weight: bold;
            }

            > .name {
                flex: 1;
                word-break: break-all;
                margin-right: 0.2em;
            }

            > .info {
                > .function {
                    display: flex;
                    align-items: center;
                    justify-content: flex-end;

                    > div {
                        margin-left: 0.5em;
                    }

                    > .download, > .delete {
                        transition: all 0.2s;

                        &:hover {
                            transform: scale(1.1);
                        }

                        &:active {
                            transform: scale(1.05);
                        }
                    }
                }
            }
        }
    }
</style>
