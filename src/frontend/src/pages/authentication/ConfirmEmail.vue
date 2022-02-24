<template>
  <div class="min-h-screen flex flex-col sm: justify-center items-center">
     <h1>Please confirm your email address</h1>
     <c-button class="ml-4" @click="logIn">Go to log in</c-button>
  </div>
</template>

<script>
import CButton from '@/components/Button.vue'
import {useRouter} from "vue-router";
export default {
  name: "ConfirmEmail",
  components:{
    CButton,
  },
  setup(){
    const router = useRouter();
    return{
      router,
    }
  },
  methods:{
    logIn: function(){
      this.router.push('/login');
    },
  },
  created() {
    if (this.$cookies.get('user') !== null ) {
      this.$router.push('/');
    }
  },
}
</script>

<style scoped>

</style>