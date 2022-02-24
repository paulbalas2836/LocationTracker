import {createStore} from 'vuex';
import { Storage } from '@capacitor/storage';
import axios from 'axios'
const store = createStore({
    state:{
        status: 'out',
        token: Storage.get({key:'token'}) || '',
        user:''
    },
    getters:{
    isLoggedIn: state => state.status === 'success',
    authStatus: state => state.status,
    loggedUser: state => state.user,
        token: state => state.token,
    },
    mutations: {
        auth_request(state) {
            state.status = 'loading'
        },
        auth_success(state, payload) {
            state.status = 'success'
            state.token = payload.jwt
            state.user = payload.appUserDto
        },
        auth_error(state) {
            state.status = 'error'
        },
        logout(state) {
            state.status = 'out'
            state.token = ''
            state.user = ''
        },
    },
    actions: {
        login({commit} ,user) {
            return new Promise((resolve, reject) => {
                commit('auth_request')
                axios.post("http://localhost:8080/login", user)
                    .then(res=> {
                        Storage.set({key: 'token', value: res.data.jwt})
                        commit('auth_success', res.data)
                        resolve(res)
                    }).catch(err => {
                    commit('auth_error', err)
                    Storage.remove({key:'token'})
                    reject(err)
                })
            })

        },
        logout({commit}) {
            return new Promise((resolve) => {
                commit('logout')
                Storage.remove({key:'token'})
                resolve()
            })
        },
    }
});

export default store;