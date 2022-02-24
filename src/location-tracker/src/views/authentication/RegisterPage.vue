<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title  class="ion-text-center">Register</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true" class="ion-align-items-center" >
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large"  class="ion-text-center">Register</ion-title>
        </ion-toolbar>
      </ion-header>

      <register-card/>
    </ion-content>
  </ion-page>
</template>

<script>
import {defineComponent} from "vue";
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/vue';
import RegisterCard from '@/components/RegisterCard.vue';
export default defineComponent({
  name: "RegisterPage",
  components: {RegisterCard, IonPage, IonHeader, IonToolbar, IonTitle, IonContent},
  created(){
    if(this.$store.getters.isLoggedIn){
      this.$router.push('/home')
    }
  },
})
</script>
