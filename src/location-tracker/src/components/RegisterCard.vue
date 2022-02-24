<template>
  <ion-card>
    <ion-card-content>
      <ion-item>
        <ion-label position="floating"> Email </ion-label>
        <ion-input  v-model="form.email"></ion-input>
      </ion-item>
      <ion-item>
        <ion-label position="floating"> Password </ion-label>
        <ion-input  placeholder="password" type="password" v-model="form.password"></ion-input>
      </ion-item>
      <ion-item>
        <ion-label position="floating"> Password </ion-label>
        <ion-input placeholder="password match" type="password"  v-model="form.matchingPassword"></ion-input>
      </ion-item>
      <ion-button class="ion-margin-top" color="tertiary" shape="round" expand="full" @click="submit">
        Register
      </ion-button>
      <ion-item>
        <router-link to="/"> Already have an account? </router-link>
      </ion-item>
    </ion-card-content>
  </ion-card>
</template>

<script>
import {IonCard, IonLabel, IonInput, IonCardContent, IonItem, IonButton, toastController} from '@ionic/vue';
import {defineComponent, reactive} from "vue";
const REGISTER_URL = 'http://localhost:8080/register'
export default defineComponent({
  name: "RegisterCard",
  components: {IonCard, IonLabel, IonInput, IonCardContent, IonItem, IonButton},
  setup(){
    const form = reactive({email: '', password: '',matchingPassword: ''})
    const errors = reactive({})
    return{
      form,
      errors
    }
  },
  methods: {
    submit: function () {
      fetch(REGISTER_URL, {
        method: "post",
        headers: {
          Accept: 'application/json',
          "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify({email: this.form.email, password: this.form.password, matchingPassword: this.form.matchingPassword}),
      }).then(res => res.json())
      .then(async (json) => {
        this.errors = json
        let toast = await toastController.create({
          color: 'dark',
          duration: 2000,
          message: "Account has been created",
          showCloseButton: true,
        });
        if(Object.prototype.hasOwnProperty.call(this.errors, "statusCodeValue")) {
          if(this.errors.statusCodeValue === 200) {
            toast = await toastController.create({
              color: 'dark',
              duration: 2000,
              message: "Account has been created",
              showCloseButton: true,
            });
            this.$router.push('/login')
          }
          else{
            toast = await toastController.create({
              color: 'dark',
              duration: 2000,
              message: "Email address is already taken",
              showCloseButton: true,
            });
          }
        }else if(Object.prototype.hasOwnProperty.call(this.errors, "errors"))
        {
           toast = await toastController.create({
            color: 'dark',
            duration: 2000,
            message: this.errors.errors,
            showCloseButton: true,
          });
        }

        await toast.present();

      })
    }
  },
})
</script>