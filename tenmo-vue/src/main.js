import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import 'vuetify/styles' // Global CSS has to be imported

loadFonts()

axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;

createApp(App)
  .use(router)
  .use(store)
  .use(vuetify)
  .mount('#app')
