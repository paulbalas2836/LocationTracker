<template>
<ion-card>
  <ion-card-content>
    <ion-label>{{loggedUser.email}}</ion-label>
    <ion-button class="ion-margin-top" color="tertiary" shape="round" expand="full" @click="submit">
      Submit Current Location
    </ion-button>
    <ion-button class="ion-margin-top" color="tertiary" shape="round" expand="full" @click="logout">
      Logout
    </ion-button>
  </ion-card-content>
</ion-card>
</template>

<script>
import {computed, defineComponent} from "vue";
import {IonCard, IonCardContent, IonButton, IonLabel, toastController} from "@ionic/vue";
import {useStore} from "vuex";
import axios from "axios";
const LOCATION_URL ="http://localhost:8080/position/createPosition"
export default defineComponent({
  name: "HomeCard",
  components:{IonCard, IonCardContent, IonButton, IonLabel },
  setup(){
    const store = useStore()
    const loggedUser = computed(()=>{
      return store.getters.loggedUser
    })
    const location = (position) => {
      const latitude  = position.coords.latitude;
      const longitude = position.coords.longitude;
      axios.post(LOCATION_URL, {latitude: latitude, longitude: longitude, terminalId: loggedUser.value.email},{
        headers:{
          Authorization: "Bearer " + store.getters.token
        }
      }).then(async ()=>{
        await toastController.create({
          color: 'dark',
          duration: 2000,
          message: "Position saved",
          showCloseButton: true,
        });
      }).catch(err => console.log(err))
    }
    const error = (err) => {
      console.log(err)
    };
    const submit = () => (navigator.geolocation.getCurrentPosition(location, error))
    return{
      loggedUser,
      submit
    }
  },
  methods:{
    logout: function(){
      this.$store.dispatch('logout')
          .then(() => {
            this.$router.push('/')
          })
      }
  }
})
</script>