<template>
    <div class="list">
        <loading-bar :loading="dataLoading"></loading-bar>
        <div class="empty" v-if="dataArray.length<1 && !dataLoading">EMPTY</div>
        <div class="item" v-for="item of dataArray" :key="item.name">
            <div class="name">{{item.name}}</div>
            <div class="sizeString">{{item.sizeString}}</div>
        </div>
    </div>
</template>

<script>
    import StringUtil from "../utils/StringUtil";
    import LoadingBar from "./LoadingBar";

    export default {
        name: "FileList",
        components: {LoadingBar},
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
                let xhr = new XMLHttpRequest();
                xhr.open('get', 'api/list', true);
                xhr.send();
                xhr.onload = () => {
                    switch (xhr.status) {
                        case 200:
                            this.renderData(JSON.parse(xhr.response));
                            break;
                        case  500:
                            if (xhr.response.indexOf('Maximum upload size exceeded')) {
                                alert('文件过大');
                            } else {
                                alert('服务器出错');
                            }
                            break;
                    }
                };
            },

            renderData(array) {
                array.forEach(item => item.sizeString = this.stringUtil.formatSize(item.size));
                this.dataArray = array;
                this.controlLoading(false);
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
            }
        }
    }
</style>
