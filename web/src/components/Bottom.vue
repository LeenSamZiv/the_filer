<template>
    <div class="bottom pointer text-center" onclick="window.open('http://github.com/LeenSamZIv')">©LeenSamZiv</div>
</template>

<script>
    export default {
        name: "Bottom",
    }
</script>

<style scoped>
    .bottom {
        position: absolute;
        bottom: 0;
        font-size: 0.6em;
        color: #999999;
        animation: disappear 2s ease-in-out infinite;
        animation-direction: alternate;
    }

    @keyframes disappear {
        from {
            opacity: 1;
        }
        to {
            opacity: 0;
        }
    }
</style>
