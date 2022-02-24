import {createRouter, createWebHistory} from '@ionic/vue-router';
import LoginPage from '../views/authentication/LoginPage.vue'
import RegisterPage from '../views/authentication/RegisterPage.vue'
import HomePage from '@/views/HomePage.vue';
import {Storage} from "@capacitor/storage";

const routes = [
    {
        path: '/',
        name: 'Login',
        component: LoginPage
    },
    {
        path: '/register',
        name: 'Register',
        component: RegisterPage
    },
    {
        path: '/home',
        name: 'HomePage',
        component: HomePage
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})


router.beforeEach((to, from, next) => {
    const publicPages = ['/', '/register'];
    const authRequired = !publicPages.includes(to.path)
    const loggedIn = Storage.get({key: 'token'})
    if (authRequired && !loggedIn) {
        next('/');
    } else {
        next();
    }
});

export default router
