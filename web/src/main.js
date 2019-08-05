import Vue from 'vue'
import App from './App.vue'

const isDebug_mode = process.env.NODE_ENV !== 'production';
Vue.config.debug = isDebug_mode;
Vue.config.devtools = isDebug_mode;
Vue.config.productionTip = isDebug_mode;

new Vue({
    render: h => h(App),
}).$mount('#app');
