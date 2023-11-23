import './assets/main.scss'
import Element from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router'
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'

import { createApp } from 'vue'
import App from './App.vue'

const pinia = createPinia()
const app = createApp(App)
const persist = createPersistedState()

pinia.use(persist)
app.use(Element)
app.use(router)
app.use(pinia)


app.mount('#app')
