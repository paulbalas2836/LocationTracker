<template>
  <c-authentication-card>
      <div>
          <c-label for="email" value="Email" />
          <c-input id="email" type="email" class="mt-1 block w-full" v-model="form.email" required autofocus />
      </div>

      <div class="mt-5">
        <c-label for="password" value="Password" />
        <c-input id="password" type="password" class="mt-1 block w-full" v-model="form.password" />
      </div>

    <div class="mt-5">
      <c-error-message :message="errorMessage" v-show="invalid"></c-error-message>
    </div>

      <section class="mt-5" :class="{invalid:'mt-10'}">
        <label class="flex items-center">
          <c-checkbox name="remember" v-model:checked="form.remember" />
          <span class="ml-2 text-sm text-gray-600">Remember me</span>
        </label>
      </section>
    <div class="flex items-center justify-end mt-4">
      <div class="underline text-sm text-gray-600 hover:text-gray-900" >
       <router-link to="/register"> Register </router-link>
      </div>
      <c-button class="ml-4" @click="submit">
        Log in
      </c-button>
      <router-view />
    </div>

  </c-authentication-card>
</template>
<script>
import CAuthenticationCard from '@/components/AuthenticationCard.vue'
import CInput from '@/components/Input.vue'
import CLabel from '@/components/Label.vue'
import CCheckbox from '@/components/Checkbox.vue'
import CButton from '@/components/Button.vue'
import CErrorMessage from '@/components/ErrorMessage'

import {reactive, ref} from "vue";
import {useRouter} from "vue-router";

export default{
  name:'Login',
  components:{
    CAuthenticationCard,
    CInput,
    CLabel,
    CButton,
    CCheckbox,
    CErrorMessage,
  },
  setup() {
    const form = reactive({email: '', password:'', remember: false})
    const url = 'http://localhost:8080/login'
    const router = useRouter();
    const invalid = ref(false);
    const errorMessage = ref('');
    return {
      errorMessage,
      invalid,
      router,
      form,
      url,
    }
  },
  methods:{
    submit: function (){
      this.axios.post(this.url, {
        email: this.form.email, password: this.form.password }
      ).then(res => {
        this.$cookies.set('token',res.data)
        this.router.push('/');
      }).catch(err =>{
        this.invalid = true
        this.errorMessage = err.response.data.errors[0]
      })
    }
  },
  created() {
    if (this.$cookies.get('token') !== null ) {
      this.$router.push('/');
    }
  },
}
</script>