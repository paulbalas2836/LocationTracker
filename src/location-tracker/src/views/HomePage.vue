<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title class="ion-text-center" >Track your location</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true" class="ion-align-items-center" >
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large"  class="ion-text-center">Track your location</ion-title>
        </ion-toolbar>
      </ion-header>

      <home-card/>
    </ion-content>
  </ion-page>
</template>

<script>
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/vue';
import { defineComponent} from "vue";
import HomeCard from "@/components/HomeCard";
export default defineComponent({
  name: "HomePages",
  components: {HomeCard, IonPage, IonHeader, IonToolbar, IonTitle, IonContent},
  created() {
    if(!this.$store.getters.isLoggedIn){
      this.$router.push('/')
    }
  }
})
</script>
