<template>
  <ion-page>
    <ion-tabs>
      <ion-router-outlet></ion-router-outlet>
      <ion-tab-bar slot="bottom">
        <ion-tab-button tab="login" href="/login">
           <ion-icon :icon="ellipse" />
          <ion-label class="itemCustomization">Login</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="register" href="/register">
          <ion-icon :icon="ellipse" />
          <ion-label class="itemCustomization">Register</ion-label>
        </ion-tab-button>

      </ion-tab-bar>
    </ion-tabs>
  </ion-page>
</template>

<script>
import { IonTabBar, IonTabButton, IonTabs, IonLabel, IonIcon, IonPage, IonRouterOutlet } from '@ionic/vue';
import { ellipse } from 'ionicons/icons';

export default {
  name: "TabsAuthentication",
  components: { IonLabel, IonTabs, IonTabBar, IonTabButton, IonIcon, IonPage, IonRouterOutlet },
  setup() {
    return {
      ellipse,
    }
  }
}
</script>

<style scoped>
.itemCustomization{
  color: white;
}
</style>