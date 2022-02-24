<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title class="ion-text-center" >Login</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true" class="ion-align-items-center" >
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large"  class="ion-text-center">Login</ion-title>
        </ion-toolbar>
      </ion-header>

      <login-card/>
    </ion-content>
  </ion-page>
</template>


<script>
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/vue';
import LoginCard from '@/components/LoginCard.vue';
import {defineComponent} from "vue";
export default defineComponent ({
  name: "LoginPage",
  components: {LoginCard, IonPage, IonHeader, IonToolbar, IonTitle, IonContent},
  created(){
    if(this.$store.getters.isLoggedIn){
      this.$router.push('/home')
    }
  }
})
</script>