import {createRouter, createWebHistory} from 'vue-router'
import Login from '@/pages/authentication/Login.vue'
import Register from "@/pages/authentication/Register";
import ConfirmEmail from "@/pages/authentication/ConfirmEmail";
import Map from "@/pages/Map";
import Profile from "@/pages/profie/Profile";
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

const routes = [
    {
        path: '/profile',
        name: 'Profile',
        component: Profile,
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/register',
        name:'Register',
        component: Register
    },
    {
        path: '/confirm',
        name: 'Confirm',
        component: ConfirmEmail
    },
    {
        path: '/',
        name: 'Home',
        component: Map
    }

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register'];
    const authRequired = !publicPages.includes(to.path)
    const loggedIn = cookies.get('token')
    if (authRequired && !loggedIn) {
        next('/login');
    } else {
        next();
    }
});

export default router



