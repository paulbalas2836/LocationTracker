<template>
  <c-authentication-card>
    <div>
      <c-label value="Email" for="email" />
      <c-input type="email" id="email" class="mt-1 block w-full" v-model="form.email" />
      <c-error-message v-show="checkErrorEmail" :message="errorEmailMessage" />
    </div>

    <div class="mt-5">
      <c-label value="Password" for="password" />
      <c-input type="password" id="password" class="mt-1 block w-full" v-model="form.password" />
      <c-error-message v-show="checkErrorPassword" :message="errorPasswordMessage" />
    </div>

    <div class="mt-5">
      <c-label value="Confirm Password" for="confirmPassword" />
      <c-input type="password" id="password" class="mt-1 block w-full" v-model="form.matchingPassword" />
      <c-error-message v-show="checkErrorConfirmPassword" :message="errorConfirmPasswordMessage"/>
    </div>

    <div class="mt-5 flex items-center justify-end">
      <div class="text-sm mr-5 underline text-gray-600 hover:text-gray-900">
        <router-link to="/login">i already have an account</router-link>
      </div>
      <c-button @click="submitUser">Register</c-button>
    </div>
  </c-authentication-card>
</template>

<script>
import CAuthenticationCard from '@/components/AuthenticationCard'
import CLabel from '@/components/Label'
import CInput from '@/components/Input'
import CButton from '@/components/Button'
import CErrorMessage from '@/components/ErrorMessage'
import {reactive, ref} from "vue";
import {useRouter} from "vue-router";

const url = 'http://localhost:8080/register'
export default {
  name: "Register",
  components:{
    CAuthenticationCard,
    CLabel,
    CButton,
    CInput,
    CErrorMessage,
  },
  setup(){
    const form = reactive({email:'', password:'', matchingPassword:''})
    const checkErrorEmail = ref(false);
    const checkErrorPassword = ref(false);
    const checkErrorConfirmPassword = ref(false);
    const errorEmailMessage = ref('');
    const errorPasswordMessage = ref('');
    const errorConfirmPasswordMessage = ref('');
    const router = useRouter();
    return{
      form,
      url,
      checkErrorEmail,
      checkErrorPassword,
      checkErrorConfirmPassword,
      errorEmailMessage,
      errorPasswordMessage,
      errorConfirmPasswordMessage,
      router,
    }
  },
  methods:{
    submitUser(){
      this.axios.post(this.url, {email: this.form.email, password: this.form.password, matchingPassword:this.form.matchingPassword}).then(() => {
        this.router.push('/confirm');
      }).catch(error => {
        if(error.response){
          error.response.data.errors.forEach(err => {
            if(err.includes('email')){
              this.checkErrorEmail = true
              this.errorEmailMessage = err
            }
            if(err.includes('password')){
              this.checkErrorPassword = true
              this.errorPasswordMessage = err
            }
            if(err.includes('match')){
              this.checkErrorConfirmPassword = true
              this.errorConfirmPasswordMessage = err
            }
          })
        }
      })
    },
  },
  created() {
    if (this.$cookies.get('token') !== null) {
      this.$router.push('/confirm');
    }
  },
}
</script>
