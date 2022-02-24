<template>
  <ion-card>
    <ion-card-content>
      <ion-item>
        <ion-label position="floating"> Email </ion-label>
        <ion-input  v-model="form.email"></ion-input>
      </ion-item>
      <ion-item>
        <ion-label position="floating"> Password </ion-label>
        <ion-input placeholder="password" type="password"  v-model="form.password"></ion-input>
      </ion-item>
        <ion-button class="ion-margin-top" color="tertiary" shape="round" expand="full" @click="submit">
          Login
        </ion-button>
      <ion-item>
        <router-link to="/register"> Register </router-link>
      </ion-item>
    </ion-card-content>
  </ion-card>
</template>

<script>
import {IonCard, IonLabel, IonInput, IonCardContent, IonItem, IonButton} from '@ionic/vue';
import {defineComponent, reactive} from "vue";
export default defineComponent ({
  name: "LoginCard",
  components: {IonCard, IonLabel, IonInput, IonCardContent, IonItem, IonButton},
  setup(){
    const form = reactive({email: '', password: ''})
    const data = reactive({})

    return{
      form,
      data
    }
  },
  methods:{
    submit: function (){
      let email = this.form.email
      let password = this.form.password

      this.$store.dispatch('login',{email, password})
      .then( ()=>{
        this.$router.push('/home')}
      ).catch(err=> console.log(err))
    },
  },
})
</script>
