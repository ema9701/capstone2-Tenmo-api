import {createApp} from 'vue'
import App from '@/App.vue'
import '@/registerServiceWorker'
import router from '@/router'
import store from '@/store'
import axios from 'axios'

// createApp.config.productionTip = false

axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;

createApp(App).use(store).use(router).mount('#app'); 





