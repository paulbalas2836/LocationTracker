import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import  VueCookies   from 'vue3-cookies'
import VueAxios from 'vue-axios'
import vClosable from "@/directives/close";
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';


const app = createApp(App)
app.use(VueAxios, axios)
app.use(router)
app.use(VueSweetalert2)
app.directive('closable', vClosable)
app.use(VueCookies,{
    expireTimes: "1d",
})
app.mount('#app')