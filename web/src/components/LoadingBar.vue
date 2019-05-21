<template>
    <transition name="transition">
        <div class="bar" v-if="loading">
            <div class="shape">
                <span></span>
                <span></span>
                <span></span>
                <span></span>
            </div>
        </div>
    </transition>
</template>

<script>
    export default {
        name: "LoadingBar",
        props: {
            loading: {default: true}
        }
    }
</script>

<style scoped lang="scss">
    .bar {
        display: flex;
        align-items: center;
        justify-content: center;
        padding: $spacing-normal;

        > .shape {
            display: flex;
            flex-flow: row nowrap;
            align-items: center;
            justify-content: space-between;
            width: 2em;

            span {
                width: 0.3em;
                height: 0.5em;
                background-color: $color-yellow;
            }

            span:nth-of-type(1) {
                animation: grow 1s -0.45s ease-in-out infinite;
            }

            span:nth-of-type(2) {
                animation: grow 1s -0.3s ease-in-out infinite;
            }

            span:nth-of-type(3) {
                animation: grow 1s -0.15s ease-in-out infinite;
            }

            span:nth-of-type(4) {
                animation: grow 1s ease-in-out infinite;
            }

            @keyframes grow {
                0%,
                100% {
                    transform: scaleY(0.5);
                }

                50% {
                    transform: scaleY(2);
                }
            }
        }
    }

    .transition-enter-active {
        animation: height .5s;
    }

    .transition-leave-active {
        animation: height .5s reverse;
    }

    @keyframes height {
        0% {
            height: 0;
        }
        100% {
            height: auto;
        }
    }
</style>
