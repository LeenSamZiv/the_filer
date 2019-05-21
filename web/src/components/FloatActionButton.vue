<template>
    <div class="float copy-unable" @click="handleClick">
        <div class="button"></div>
    </div>
</template>

<script>
    export default {
        name: "FloatActionButton",
        methods: {
            handleClick() {
                this.$emit('clickEvent');
            }
        }
    }
</script>

<style scoped lang="scss">
    .float {
        position: absolute;
        right: $spacing-normal*3;
        bottom: $spacing-normal*3;
        padding: 0 !important;

        perspective: 120px;

        > .button {
            width: 1.5em;
            height: 1.5em;
            border-radius: 50%;
            border: 0.3em solid $color-yellow;
            animation: spin 1.5s ease-in-out;
            animation-delay: 1.5s;
            transition: all 0.2s ease-in-out;

            &:hover {
                width: 1.2em;
                height: 1.2em;
            }

            &:active {
                width: 1em;
                height: 1em;
            }
        }

        @keyframes spin {
            to {
                transform: rotateY(540deg);
            }
        }
    }
</style>
